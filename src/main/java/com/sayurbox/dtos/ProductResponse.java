package com.sayurbox.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {
    private int id;
    private String name;
    private int quantity;
    private int unitPrice;
    private String unit;
    private String abbreviation;
}
