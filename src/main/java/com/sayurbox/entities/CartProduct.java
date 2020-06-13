package com.sayurbox.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart_products")
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private int userId;
    private int quantity;

    public CartProduct(int userId, Product product, int quantity) {
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
    }
}