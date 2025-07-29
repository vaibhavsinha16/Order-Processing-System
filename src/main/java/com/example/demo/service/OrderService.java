package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.model.Product;
import com.example.demo.model.OrderItem;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.AddressRepository;
import com.example.demo.model.User;
import com.example.demo.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, UserRepository userRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Order createOrder(Order order, Long userId, Long addressId) {
        // Set initial order status and timestamps
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(java.time.LocalDateTime.now());
        order.setUpdatedAt(java.time.LocalDateTime.now());
        
        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
            order.setUser(user);
            // Set customer name from user if not provided
            if (order.getCustomerName() == null || order.getCustomerName().trim().isEmpty()) {
                order.setCustomerName(user.getUsername());
            }
        }
        if (addressId != null) {
            Address address = addressRepository.findById(addressId).orElseThrow(() -> new IllegalArgumentException("Invalid addressId"));
            order.setAddress(address);
        }
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                // The frontend sends productId and quantity
                // We need to get the product details from the productId
                if (item.getItemName() == null || item.getPrice() == 0) {
                    // For new orders, we need to get product details from productId
                    // The productId should be available in the item data
                    final Long productId;
                    
                    // Try to get productId from different possible sources
                    if (item.getId() != null) {
                        // If item.id is set, it might be the productId (temporary storage)
                        productId = item.getId();
                    } else {
                        throw new IllegalArgumentException("Order item must have productId");
                    }
                    
                    Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid productId: " + productId));
                    item.setItemName(product.getName());
                    item.setPrice(product.getPrice());
                    // Reset the id since it was temporarily used for productId
                    item.setId(null);
                }
                item.setOrder(order);
            }
        }
        Order saved = orderRepository.save(order);
        logger.info("Order created: id={}, customer={}", saved.getId(), saved.getCustomerName());
        return saved;
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> getOrderByIdAndUser(Long orderId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
        return orderRepository.findByIdAndUser(orderId, user);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        order.setUpdatedAt(java.time.LocalDateTime.now());
        Order saved = orderRepository.save(order);
        logger.info("Order status updated: id={}, newStatus={}", orderId, status);
        return saved;
    }

    public List<Order> listOrders(Optional<OrderStatus> status) {
        if (status.isPresent()) {
            return orderRepository.findByStatus(status.get());
        } else {
            return orderRepository.findAll();
        }
    }

    public List<Order> listOrdersByUser(Long userId, Optional<OrderStatus> status) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
        if (status.isPresent()) {
            return orderRepository.findByUserAndStatus(user, status.get());
        } else {
            return orderRepository.findByUser(user);
        }
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (order.getStatus() != OrderStatus.PENDING) {
            logger.warn("Attempt to cancel non-PENDING order: id={}, status={}", orderId, order.getStatus());
            throw new IllegalStateException("Only PENDING orders can be cancelled");
        }
        order.setStatus(OrderStatus.CANCELED);
        order.setUpdatedAt(java.time.LocalDateTime.now());
        Order saved = orderRepository.save(order);
        logger.info("Order cancelled: id={}", orderId);
        return saved;
    }

    @Transactional
    public int processPendingOrders() {
        List<Order> pendingOrders = orderRepository.findByStatus(OrderStatus.PENDING);
        int count = 0;
        for (Order order : pendingOrders) {
            order.setStatus(OrderStatus.PROCESSING);
            order.setUpdatedAt(java.time.LocalDateTime.now());
            orderRepository.save(order);
            count++;
            logger.info("Order status auto-updated to PROCESSING: id={}", order.getId());
        }
        return count;
    }
} 