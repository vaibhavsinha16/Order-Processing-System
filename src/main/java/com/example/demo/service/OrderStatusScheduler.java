package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusScheduler {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusScheduler.class);
    private final OrderService orderService;

    public OrderStatusScheduler(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(fixedRate = 300000) // every 5 minutes
    public void updatePendingOrders() {
        logger.info("Scheduled job running: updating PENDING orders to PROCESSING");
        int updated = orderService.processPendingOrders();
        logger.info("Scheduled job completed: {} orders updated to PROCESSING", updated);
    }
} 