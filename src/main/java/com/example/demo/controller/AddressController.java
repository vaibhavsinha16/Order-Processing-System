package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.controller.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AuthController authController;

    @Autowired
    public AddressController(AddressRepository addressRepository, UserRepository userRepository, AuthController authController) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.authController = authController;
    }

    @GetMapping
    public List<Address> list(@RequestHeader("X-Auth-Token") String token) {
        Long userId = authController.getUserIdFromToken(token);
        if (userId == null) return List.of();
        User user = userRepository.findById(userId).orElse(null);
        return user == null ? List.of() : addressRepository.findByUser(user);
    }

    @PostMapping
    public Address add(@RequestHeader("X-Auth-Token") String token, @RequestBody Address address) {
        Long userId = authController.getUserIdFromToken(token);
        if (userId == null) throw new RuntimeException("Invalid token");
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new RuntimeException("User not found");
        address.setUser(user);
        return addressRepository.save(address);
    }
} 