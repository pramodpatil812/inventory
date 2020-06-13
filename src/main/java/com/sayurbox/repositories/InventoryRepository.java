package com.sayurbox.repositories;

import com.sayurbox.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Product, Integer> {

    @Query("Select p from Product p where id in :productIds")
    List<Product> findByIds(List<Integer> productIds);

    @Modifying
    @Query(value = "Update products set quantity = quantity - ?2 where id = ?1 and quantity >= ?2", nativeQuery = true)
    int decrementQuantity(int productId, int quantity);
}
