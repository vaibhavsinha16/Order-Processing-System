package com.example.demo.config;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // Clear existing products and reinitialize to ensure proper stock
        productRepository.deleteAll();
        initializeProducts();
    }

    private void initializeProducts() {
        List<Product> products = Arrays.asList(
            // Electronics
            new Product(null, "MacBook Pro 16\"", 2499.99, 
                       "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=300&h=300&fit=crop", 
                       "Latest MacBook Pro with M3 chip, 16GB RAM, 512GB SSD", "Electronics", 25),
            new Product(null, "iPhone 15 Pro", 999.99, 
                       "https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=300&h=300&fit=crop", 
                       "Apple iPhone 15 Pro with A17 Pro chip, 128GB storage", "Electronics", 50),
            new Product(null, "Samsung Galaxy S24", 1199.99, 
                       "https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=300&h=300&fit=crop", 
                       "Samsung Galaxy S24 Ultra with S Pen, 256GB", "Electronics", 40),
            new Product(null, "Sony WH-1000XM5", 349.99, 
                       "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300&h=300&fit=crop", 
                       "Wireless noise-cancelling headphones with 30-hour battery", "Electronics", 75),
            new Product(null, "iPad Air", 599.99, 
                       "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=300&h=300&fit=crop", 
                       "10.9-inch iPad Air with M2 chip, 64GB storage", "Electronics", 60),
            new Product(null, "Wireless Mouse", 12.99, 
                       "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=300&h=300&fit=crop", 
                       "A smooth and responsive wireless mouse", "Electronics", 150),
            new Product(null, "Bluetooth Headphones", 29.99, 
                       "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300&h=300&fit=crop", 
                       "High-quality wireless headphones with deep bass", "Electronics", 100),

            // Home & Kitchen
            new Product(null, "Instant Pot Duo", 89.99, 
                       "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop", 
                       "7-in-1 electric pressure cooker, 6-quart capacity", "Home & Kitchen", 120),
            new Product(null, "Ninja Foodi", 199.99, 
                       "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop", 
                       "9-in-1 Deluxe XL Cooker with TenderCrisp Technology", "Home & Kitchen", 85),
            new Product(null, "Dyson V15", 699.99, 
                       "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=300&h=300&fit=crop", 
                       "Cord-free vacuum cleaner with laser technology", "Home & Kitchen", 45),
            new Product(null, "KitchenAid Mixer", 379.99, 
                       "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop", 
                       "Professional 5-quart stand mixer in red", "Home & Kitchen", 30),
            new Product(null, "Breville Coffee Maker", 699.99, 
                       "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=300&h=300&fit=crop", 
                       "Barista Express espresso machine with grinder", "Home & Kitchen", 25),

            // Books
            new Product(null, "The Midnight Library", 24.99, 
                       "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=300&fit=crop", 
                       "Matt Haig novel about infinite possibilities", "Books", 200),
            new Product(null, "Atomic Habits", 19.99, 
                       "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=300&fit=crop", 
                       "James Clear guide to building good habits", "Books", 150),
            new Product(null, "The Seven Husbands of Evelyn Hugo", 16.99, 
                       "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=300&fit=crop", 
                       "Taylor Jenkins Reid historical fiction", "Books", 180),
            new Product(null, "Project Hail Mary", 27.99, 
                       "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=300&fit=crop", 
                       "Andy Weir science fiction novel", "Books", 120),
            new Product(null, "Lessons in Chemistry", 22.99, 
                       "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=300&fit=crop", 
                       "Bonnie Garmus debut novel", "Books", 160),

            // Fashion
            new Product(null, "Nike Air Max 270", 129.99, 
                       "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=300&h=300&fit=crop", 
                       "Comfortable running shoes with Air Max technology", "Fashion", 100),
            new Product(null, "Levi's 501 Jeans", 69.99, 
                       "https://images.unsplash.com/photo-1542272604-787c3835535d?w=300&h=300&fit=crop", 
                       "Classic straight-fit denim jeans", "Fashion", 200),
            new Product(null, "Ray-Ban Aviator", 159.99, 
                       "https://images.unsplash.com/photo-1572635196237-14b3f281503f?w=300&h=300&fit=crop", 
                       "Classic aviator sunglasses with UV protection", "Fashion", 80),
            new Product(null, "Casio G-Shock", 89.99, 
                       "https://images.unsplash.com/photo-1524592094714-0f0654e20314?w=300&h=300&fit=crop", 
                       "Durable digital watch with shock resistance", "Fashion", 150),
            new Product(null, "Adidas Ultraboost", 179.99, 
                       "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=300&h=300&fit=crop", 
                       "Premium running shoes with Boost technology", "Fashion", 75),

            // Sports & Outdoors
            new Product(null, "Yeti Tundra 45", 299.99, 
                       "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop", 
                       "Premium hard cooler with 45-quart capacity", "Sports & Outdoors", 40),
            new Product(null, "Patagonia Down Jacket", 199.99, 
                       "https://images.unsplash.com/photo-1551028719-00167b16eac5?w=300&h=300&fit=crop", 
                       "Lightweight insulated jacket for cold weather", "Sports & Outdoors", 60),
            new Product(null, "Coleman Tent", 149.99, 
                       "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop", 
                       "4-person camping tent with weather protection", "Sports & Outdoors", 35),
            new Product(null, "Garmin Fenix 7", 699.99, 
                       "https://images.unsplash.com/photo-1524592094714-0f0654e20314?w=300&h=300&fit=crop", 
                       "Premium GPS smartwatch for outdoor activities", "Sports & Outdoors", 25),
            new Product(null, "Peloton Bike", 1495.00, 
                       "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop", 
                       "Smart exercise bike with live classes", "Sports & Outdoors", 20)
        );

        productRepository.saveAll(products);
        System.out.println("Sample products initialized successfully with stock quantities!");
    }
} 