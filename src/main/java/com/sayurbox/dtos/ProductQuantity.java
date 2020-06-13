package com.sayurbox.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductQuantity {

    @JsonProperty("product_id")
    int productId;

    int quantity;

    public ProductQuantity(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
