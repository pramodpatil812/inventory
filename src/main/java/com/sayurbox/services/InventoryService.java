package com.sayurbox.services;

import com.sayurbox.dtos.ProductResponse;
import com.sayurbox.entities.Product;
import com.sayurbox.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;


    public List<ProductResponse> getAllProducts() {
        List<Product> products = inventoryRepository.findAll();
        System.out.println(products.get(0));
        return products.stream()
                .map(p->new ProductResponse(p.getId(), p.getName(), p.getQuantity(),p.getUnitPrice(),
                        p.getUnit().getName(), p.getUnit().getAbbreviation()))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByIds(List<Integer> productIds) {
        return inventoryRepository.findByIds(productIds);
    }

    public int reduceInventory(int productId, int quantity) {
        return inventoryRepository.decrementQuantity(productId, quantity);
    }
}
