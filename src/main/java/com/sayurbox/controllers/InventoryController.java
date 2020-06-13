package com.sayurbox.controllers;

import com.sayurbox.dtos.ProductResponse;
import com.sayurbox.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(value = "/inventories", method = RequestMethod.GET)
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> productResponses = inventoryService.getAllProducts();

        return ResponseEntity.ok(productResponses);
    }

}
