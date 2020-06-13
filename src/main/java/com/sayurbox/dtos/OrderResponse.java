package com.sayurbox.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
    private boolean success;
    private String msg;

    @JsonProperty(value = "order_id", defaultValue = "")
    private int orderId;

    public static OrderResponse success(int orderId) {
        return new OrderResponse(true,"", orderId);
    }

    public static OrderResponse failure(String error) {
        return new OrderResponse(false, error, 0);
    }
}
