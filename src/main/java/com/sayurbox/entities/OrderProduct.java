package com.sayurbox.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_products")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    //private int productId;
    private int unitPrice;
    private int quantity;

}