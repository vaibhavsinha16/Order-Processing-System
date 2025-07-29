package com.example.demo.dto;

import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        if (order == null) return null;
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setItems(toItemDTOList(order.getItems()));
        dto.setUserId(order.getUser() != null ? order.getUser().getId() : null);
        dto.setAddressId(order.getAddress() != null ? order.getAddress().getId() : null);
        return dto;
    }

    public static List<OrderDTO> toDTOList(List<Order> orders) {
        return orders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    public static Order toEntity(OrderDTO dto) {
        if (dto == null) return null;
        Order order = new Order();
        order.setId(dto.getId());
        order.setCustomerName(dto.getCustomerName());
        order.setStatus(dto.getStatus());
        order.setCreatedAt(dto.getCreatedAt());
        order.setUpdatedAt(dto.getUpdatedAt());
        order.setItems(toItemEntityList(dto.getItems(), order));
        // user and address will be set in service
        return order;
    }

    public static OrderItemDTO toItemDTO(OrderItem item) {
        if (item == null) return null;
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setProductId(null); // Will set in creation logic
        dto.setItemName(item.getItemName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }

    public static List<OrderItemDTO> toItemDTOList(List<OrderItem> items) {
        if (items == null) return null;
        return items.stream().map(OrderMapper::toItemDTO).collect(Collectors.toList());
    }

    public static OrderItem toItemEntity(OrderItemDTO dto, Order order) {
        if (dto == null) return null;
        OrderItem item = new OrderItem();
        
        // For new orders, the frontend sends productId in the productId field
        // We need to preserve this for the service to use
        if (dto.getProductId() != null) {
            item.setId(dto.getProductId()); // Temporarily store productId in id field for service
        } else if (dto.getId() != null) {
            item.setId(dto.getId()); // Fallback to id field if productId is not set
        }
        
        // productId will be handled in service during creation
        item.setItemName(dto.getItemName());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());
        item.setOrder(order);
        return item;
    }

    public static List<OrderItem> toItemEntityList(List<OrderItemDTO> dtos, Order order) {
        if (dtos == null) return null;
        return dtos.stream().map(dto -> toItemEntity(dto, order)).collect(Collectors.toList());
    }
} 