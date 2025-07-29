package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private String itemName;
    private int quantity;
    private double price;
} 