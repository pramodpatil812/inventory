package com.sayurbox.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantity {

    @JsonProperty("product_id")
    int productId;

    @Min(value = 1, message = "Minimum 1 is required for quantity")
    int quantity;
}
