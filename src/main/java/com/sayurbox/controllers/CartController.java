package com.sayurbox.controllers;

import com.sayurbox.dtos.AddToCartResponse;
import com.sayurbox.dtos.ProductQuantity;
import com.sayurbox.exceptions.CartAlreadyExistsException;
import com.sayurbox.exceptions.InvalidProductException;
import com.sayurbox.exceptions.NotEnoughQuantityException;
import com.sayurbox.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/user/cart", method = RequestMethod.POST)
    public ResponseEntity<AddToCartResponse> selectProducts(@RequestBody final List<ProductQuantity> cartRequest,
                                                       @RequestHeader("user_id") final int userId) {

        try {
            cartService.addToCart(userId, cartRequest);
        }
        catch (InvalidProductException | NotEnoughQuantityException | CartAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.OK).body(AddToCartResponse.failure(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(AddToCartResponse.success());
    }

//    @RequestMapping(value = "/user/cart", method = RequestMethod.GET)
//    public ResponseEntity<AddToCartResponse> getCartProducts(@RequestHeader("user_id") final int userId) {
//        return ResponseEntity.status(HttpStatus.OK).body(AddToCartResponse.success());
//    }

}
