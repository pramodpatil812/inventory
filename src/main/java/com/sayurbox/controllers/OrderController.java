package com.sayurbox.controllers;

import com.sayurbox.dtos.OrderResponse;
import com.sayurbox.entities.CartProduct;
import com.sayurbox.exceptions.CartEmptyException;
import com.sayurbox.exceptions.InvalidProductException;
import com.sayurbox.exceptions.NotEnoughQuantityException;
import com.sayurbox.services.CartService;
import com.sayurbox.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("user_id") final int userId) {
        int orderId = 0;

        try {
            List<CartProduct> cartProducts = cartService.getCartProducts(userId);
            if (cartProducts.size() == 0) {
                throw new CartEmptyException("Your cart is empty. Please add few items.");
            }

            orderId = orderService.createOrder(userId, cartProducts);

            cartService.deleteCart(userId);
        }
        catch (InvalidProductException | NotEnoughQuantityException | CartEmptyException e) {
            return ResponseEntity.status(HttpStatus.OK).body(OrderResponse.failure(e.getMessage()));
        }
        catch (Exception e ) {
            return ResponseEntity.status(HttpStatus.OK).body(OrderResponse.failure("Some internal error"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.success(orderId));
    }
}
