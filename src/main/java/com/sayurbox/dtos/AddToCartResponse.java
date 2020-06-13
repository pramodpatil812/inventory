package com.sayurbox.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartResponse {
    private boolean success;
    private String msg;

    public static AddToCartResponse failure(String error) {
        return new AddToCartResponse(false, error);
    }

    public static AddToCartResponse success() {
        return new AddToCartResponse(true, "");
    }
}
