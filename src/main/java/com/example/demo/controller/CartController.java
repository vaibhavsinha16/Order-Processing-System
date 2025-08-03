package com.example.demo.controller;

import com.example.demo.dto.CartDTO;
import com.example.demo.dto.CartItemDTO;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.service.CartService;
import com.example.demo.controller.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final AuthController authController;

    @Autowired
    public CartController(CartService cartService, AuthController authController) {
        this.cartService = cartService;
        this.authController = authController;
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@RequestHeader("Authorization") String authHeader) {
        Long userId = authController.getUserIdFromToken(authHeader);
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Cart cart = cartService.getOrCreateCart(userId);
        CartDTO cartDTO = convertToDTO(cart);
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        
        Long userId = authController.getUserIdFromToken(authHeader);
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        if (quantity <= 0) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Cart cart = cartService.addToCart(userId, productId, quantity);
            CartDTO cartDTO = convertToDTO(cart);
            return ResponseEntity.ok(cartDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CartDTO> updateCartItem(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        
        Long userId = authController.getUserIdFromToken(authHeader);
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Cart cart = cartService.updateCartItemQuantity(userId, productId, quantity);
            CartDTO cartDTO = convertToDTO(cart);
            return ResponseEntity.ok(cartDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartDTO> removeFromCart(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long productId) {
        
        Long userId = authController.getUserIdFromToken(authHeader);
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Cart cart = cartService.removeFromCart(userId, productId);
            CartDTO cartDTO = convertToDTO(cart);
            return ResponseEntity.ok(cartDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestHeader("Authorization") String authHeader) {
        Long userId = authController.getUserIdFromToken(authHeader);
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    private CartDTO convertToDTO(Cart cart) {
        List<CartItemDTO> itemDTOs = cart.getItems().stream()
            .map(this::convertItemToDTO)
            .collect(Collectors.toList());
        
        double total = cartService.calculateCartTotal(cart);
        
        return new CartDTO(cart.getId(), itemDTOs, total);
    }

    private CartItemDTO convertItemToDTO(CartItem item) {
        double subtotal = item.getPrice() * item.getQuantity();
        
        return new CartItemDTO(
            item.getId(),
            item.getProduct().getId(),
            item.getProduct().getName(),
            item.getProduct().getImageUrl(),
            item.getQuantity(),
            item.getPrice(),
            subtotal
        );
    }
} 