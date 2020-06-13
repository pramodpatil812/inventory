package com.sayurbox.repositories;

import com.sayurbox.entities.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {

    List<CartProduct> findByUserId(int userId);
}
