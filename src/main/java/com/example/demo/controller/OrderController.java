package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import com.example.demo.service.OrderService;
import com.example.demo.dto.OrderDTO;
import com.example.demo.model.OrderStatus;
import com.example.demo.dto.OrderMapper;
import com.example.demo.model.Order;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.AddressRepository;
import com.example.demo.controller.AuthController;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order API", description = "Endpoints for order processing")
public class OrderController {
    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AuthController authController;

    @Autowired
    public OrderController(OrderService orderService, ProductRepository productRepository, UserRepository userRepository, AddressRepository addressRepository, AuthController authController) {
        this.orderService = orderService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.authController = authController;
    }

    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderDTO> createOrder(@RequestHeader("Authorization") String authHeader, @RequestBody OrderDTO orderDTO) {
        Long userId = authController.getUserIdFromToken(authHeader);
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        Order order = OrderMapper.toEntity(orderDTO);
        Order saved = orderService.createOrder(order, userId, orderDTO.getAddressId());
        return ResponseEntity.ok(OrderMapper.toDTO(saved));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(value -> ResponseEntity.ok(OrderMapper.toDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/{id}")
    @Operation(summary = "Search for user's own order by ID")
    public ResponseEntity<OrderDTO> searchOrderById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        Long userId = authController.getUserIdFromToken(authHeader);
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Order> order = orderService.getOrderByIdAndUser(id, userId);
        return order.map(value -> ResponseEntity.ok(OrderMapper.toDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update order status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        Order updated = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(OrderMapper.toDTO(updated));
    }

    @GetMapping
    @Operation(summary = "List orders for the current user, optionally filter by status")
    public ResponseEntity<List<OrderDTO>> listOrders(@RequestHeader("Authorization") String authHeader, @RequestParam(required = false) OrderStatus status) {
        Long userId = authController.getUserIdFromToken(authHeader);
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Order> orders = orderService.listOrdersByUser(userId, Optional.ofNullable(status));
        return ResponseEntity.ok(OrderMapper.toDTOList(orders));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel an order (only if PENDING)")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable Long id) {
        try {
            Order cancelled = orderService.cancelOrder(id);
            return ResponseEntity.ok(OrderMapper.toDTO(cancelled));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 