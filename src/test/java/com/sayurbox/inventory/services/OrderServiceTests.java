package com.sayurbox.inventory.services;

import com.sayurbox.entities.CartProduct;
import com.sayurbox.entities.Order;
import com.sayurbox.entities.Product;
import com.sayurbox.entities.Unit;
import com.sayurbox.exceptions.NotEnoughQuantityException;
import com.sayurbox.repositories.OrderProductRepository;
import com.sayurbox.repositories.OrderRepository;
import com.sayurbox.services.InventoryService;
import com.sayurbox.services.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private InventoryService inventoryService;

    private List<Product> products;
    private Unit unit;


    @Before
    public void init() {
        products = new ArrayList<>();
        products.add(new Product(1, unit, "Mango", 50, 100, 1 ));
        products.add(new Product(2, unit, "Papaya", 100, 150, 1 ));

        unit = new Unit(1,"kilogram","kg");
    }

    @Test
    public void testCreateOrderSuccess() {
        List<CartProduct> cartProducts = new ArrayList<>();
        cartProducts.add(new CartProduct(123, products.get(0), 5));
        cartProducts.add(new CartProduct(123, products.get(1) , 10 ));

        Mockito.when(inventoryService.reduceInventory(1, 5)).thenReturn(1);
        Mockito.when(inventoryService.reduceInventory(2, 10)).thenReturn(1);

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(new Order(1, 123, "ACTIVE", LocalDateTime.now()));

        int actual = orderService.createOrder(123, cartProducts);

        Assert.assertEquals(1, actual);
    }

    @Test(expected = NotEnoughQuantityException.class)
    public void testCreateOrderWithNotEnoughQuantity() {
        List<CartProduct> cartProducts = new ArrayList<>();
        cartProducts.add(new CartProduct(123, products.get(0), 5));
        cartProducts.add(new CartProduct(123, products.get(1) , 101 ));

        orderService.createOrder(123, cartProducts);
    }

    @Test(expected = NotEnoughQuantityException.class)
    public void testCreateOrderWithNotEnoughQuantityInInventoryReduction() {
        List<CartProduct> cartProducts = new ArrayList<>();
        cartProducts.add(new CartProduct(123, products.get(0), 5));
        cartProducts.add(new CartProduct(123, products.get(1) , 10 ));

        Mockito.when(inventoryService.reduceInventory(1, 5)).thenReturn(0);

        orderService.createOrder(123, cartProducts);
    }

}
