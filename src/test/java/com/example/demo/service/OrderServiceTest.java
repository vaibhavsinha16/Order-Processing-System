package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        Order order = new Order();
        order.setCustomerName("John");
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order saved = orderService.createOrder(order, null, null);
        assertEquals("John", saved.getCustomerName());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetOrderById() {
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Optional<Order> found = orderService.getOrderById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    void testUpdateOrderStatus() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PENDING);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order updated = orderService.updateOrderStatus(1L, OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, updated.getStatus());
    }

    @Test
    void testListOrders() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));
        List<Order> orders = orderService.listOrders(Optional.empty());
        assertEquals(1, orders.size());
    }

    @Test
    void testCancelOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PENDING);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order cancelled = orderService.cancelOrder(1L);
        assertEquals(OrderStatus.CANCELED, cancelled.getStatus());
    }

    @Test
    void testCancelOrderNotPending() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.SHIPPED);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        assertThrows(IllegalStateException.class, () -> orderService.cancelOrder(1L));
    }

    @Test
    void testProcessPendingOrders() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        when(orderRepository.findByStatus(OrderStatus.PENDING)).thenReturn(Arrays.asList(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        orderService.processPendingOrders();
        assertEquals(OrderStatus.PROCESSING, order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }
} 