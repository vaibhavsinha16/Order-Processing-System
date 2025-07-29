package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import com.example.demo.dto.OrderMapper;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(OrderController.class)
@TestPropertySource(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"
})
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private AuthController authController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerName("John");
        orderDTO.setAddressId(1L);
        Order order = new Order();
        order.setCustomerName("John");
        
        // Mock authentication
        when(authController.getUserIdFromToken("valid-token")).thenReturn(1L);
        when(orderService.createOrder(any(Order.class), eq(1L), eq(1L))).thenReturn(order);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                .header("X-Auth-Token", "valid-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetOrderById() throws Exception {
        Order order = new Order();
        order.setId(1L);
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateOrderStatus() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.SHIPPED);
        when(orderService.updateOrderStatus(eq(1L), eq(OrderStatus.SHIPPED))).thenReturn(order);
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/1/status")
                .param("status", "SHIPPED"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testListOrders() throws Exception {
        Order order = new Order();
        order.setId(1L);
        
        // Mock authentication
        when(authController.getUserIdFromToken("valid-token")).thenReturn(1L);
        when(orderService.listOrdersByUser(eq(1L), any())).thenReturn(Collections.singletonList(order));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .header("X-Auth-Token", "valid-token"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCancelOrder() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.CANCELED);
        when(orderService.cancelOrder(1L)).thenReturn(order);
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/1/cancel"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testSearchOrderById() throws Exception {
        Order order = new Order();
        order.setId(1L);
        
        // Mock authentication
        when(authController.getUserIdFromToken("valid-token")).thenReturn(1L);
        when(orderService.getOrderByIdAndUser(eq(1L), eq(1L))).thenReturn(Optional.of(order));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/search/1")
                .header("X-Auth-Token", "valid-token"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
} 