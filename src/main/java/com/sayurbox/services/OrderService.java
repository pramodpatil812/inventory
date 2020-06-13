package com.sayurbox.services;

import com.sayurbox.entities.CartProduct;
import com.sayurbox.entities.Order;
import com.sayurbox.entities.OrderProduct;
import com.sayurbox.exceptions.NotEnoughQuantityException;
import com.sayurbox.repositories.OrderProductRepository;
import com.sayurbox.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private InventoryService inventoryService;


    @Transactional
    public int createOrder(int userId, List<CartProduct> cartProducts) {

        for (CartProduct cp:cartProducts) {
            if(cp.getProduct() == null || cp.getProduct().getQuantity() < cp.getQuantity()) {
                throw new NotEnoughQuantityException("Product quantity of "+cp.getProduct().getId()+"=>" +
                        cp.getProduct().getName()+" is not available");
            }
        }

        //create order
        Order o = createActiveOrder(userId);

        //sort the cart products by product id to avoid deadlock
        Collections.sort(cartProducts, (cp1,cp2)-> cp1.getProduct().getId() - cp2.getProduct().getId());

        //random transaction id for debugging concurrent transactions
        int transactionId = cartProducts.hashCode();//(int) (Math.random()*100000);

        //System.out.println("============Transaction start============"+transactionId);

        for (CartProduct cp : cartProducts) {
            //System.out.println("Cartproduct: "+cp.getId()+"    "+transactionId);
            int affectedRows = inventoryService.reduceInventory(cp.getProduct().getId(), cp.getQuantity());

            if (affectedRows == 0) {
                //System.out.println("=========Transaction will be rolledback========="+transactionId);
                throw new NotEnoughQuantityException("Product quantity of id " + cp.getProduct().getId() + " is not available");
            }

            createOrderProduct(o, cp);

//            System.out.println("Reduced quantity for product id "+cp.getProduct().getId()+"     "+transactionId);
//
//            try {
//                //Increasing lock acquisition time for transaction
//                Thread.sleep(7000);
//            } catch (InterruptedException e) {  }
        }

        //System.out.println("============Transaction end============"+transactionId);

        return o.getId();
    }

    private Order createActiveOrder(int userId) {
        Order o = new Order();
        o.setStatus("ACTIVE");
        o.setUserId(userId);
        o.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(o);
    }

    private void createOrderProduct(Order o, CartProduct cp) {
        OrderProduct op = new OrderProduct();
        op.setOrder(o);
        op.setQuantity(cp.getQuantity());
        op.setProduct(cp.getProduct());
        op.setUnitPrice(cp.getProduct().getUnitPrice());

        orderProductRepository.save(op);
    }
}
