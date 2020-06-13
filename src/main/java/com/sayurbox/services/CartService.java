package com.sayurbox.services;

import com.sayurbox.dtos.ProductQuantity;
import com.sayurbox.entities.CartProduct;
import com.sayurbox.entities.Product;
import com.sayurbox.exceptions.CartAlreadyExistsException;
import com.sayurbox.exceptions.CartEmptyException;
import com.sayurbox.exceptions.InvalidProductException;
import com.sayurbox.exceptions.NotEnoughQuantityException;
import com.sayurbox.repositories.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private InventoryService inventoryService;


    public void addToCart(int userId, List<ProductQuantity> requiredQuantities) {

        List<CartProduct> existingCartProducts = cartProductRepository.findByUserId(userId);

        if (existingCartProducts.size() > 0) {
            throw new CartAlreadyExistsException("Cart already exists for userId " + userId + ". Use update cart api");
        }

        List<Integer> productIds = requiredQuantities.stream()
                .map(p->p.getProductId())
                .collect(Collectors.toList());

        List<Product> products = inventoryService.getProductsByIds(productIds);

        validateQuantities(requiredQuantities, products);

        Map<Integer, Product> idToObjectMap = new HashMap<>();
        for (Product p:products) {
            idToObjectMap.put(p.getId(), p);
        }

        List<CartProduct> cartProducts = requiredQuantities.stream()
                .map(cp -> createCartProduct(userId, idToObjectMap.get(cp.getProductId()), cp.getQuantity()))
                .collect(Collectors.toList());

        cartProductRepository.saveAll(cartProducts);
    }

    private CartProduct createCartProduct(int userId, Product product, int quantity) {
        CartProduct cp = new CartProduct();
        cp.setQuantity(quantity);
        cp.setUserId(userId);
        cp.setProduct(product);
        return cp;
    }

    public List<CartProduct> getCartProducts(int userId) {
        return cartProductRepository.findByUserId(userId);
    }

    public void deleteCart(int userId) {
        List<CartProduct> cartProducts = cartProductRepository.findByUserId(userId);
        if(cartProducts.size() == 0) {
            throw new CartEmptyException("Cart is empty");
        }

        cartProductRepository.deleteInBatch(cartProducts);
    }

    private void validateQuantities(List<ProductQuantity> requiredQuantities, List<Product> products) {
        //validate product quantities
        if(requiredQuantities.size() != products.size()) {
            throw new InvalidProductException("Invalid products in cart");
        }

        for (ProductQuantity rq: requiredQuantities) {
            for (Product p:products) {
                if(rq.getProductId() == p.getId() && rq.getQuantity() > p.getQuantity()) {
                    throw new NotEnoughQuantityException("Product quantity of "+p.getId()+"=>"+p.getName()+" is not available");
                }
            }
        }
    }

}
