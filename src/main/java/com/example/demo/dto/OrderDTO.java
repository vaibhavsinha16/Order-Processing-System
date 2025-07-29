package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import com.example.demo.model.OrderStatus;
import com.example.demo.dto.OrderItemDTO;

@Data
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private String customerName;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemDTO> items;
    private Long userId;
    private Long addressId;
} 