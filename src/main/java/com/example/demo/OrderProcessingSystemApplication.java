package com.example.demo;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrderProcessingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner seedProducts(ProductRepository productRepository) {
		return args -> {
			if (productRepository.count() == 0) {
				productRepository.save(new Product(null, "Wireless Mouse", 12.99, "https://m.media-amazon.com/images/I/61LtuGzXeaL._AC_UL320_.jpg", "A smooth and responsive wireless mouse."));
				productRepository.save(new Product(null, "Bluetooth Headphones", 29.99, "https://m.media-amazon.com/images/I/71D9ImsvEtL._AC_UL320_.jpg", "High-quality wireless headphones with deep bass."));
				productRepository.save(new Product(null, "USB-C Charger", 18.49, "https://m.media-amazon.com/images/I/61b6U+3l5TL._AC_UL320_.jpg", "Fast charging USB-C charger for all devices."));
				productRepository.save(new Product(null, "Smart Watch", 49.99, "https://m.media-amazon.com/images/I/71hIfcIPyxS._AC_UL320_.jpg", "Feature-rich smart watch with fitness tracking."));
				productRepository.save(new Product(null, "Backpack", 24.99, "https://m.media-amazon.com/images/I/81vpsIs58WL._AC_UL320_.jpg", "Durable and spacious backpack for daily use."));
				productRepository.save(new Product(null, "Water Bottle", 9.99, "https://m.media-amazon.com/images/I/61rL1hG6ZGL._AC_UL320_.jpg", "Insulated water bottle keeps drinks cold."));
				productRepository.save(new Product(null, "Fitness Band", 19.99, "https://m.media-amazon.com/images/I/61ZRU9gnbxL._AC_UL320_.jpg", "Track your activity and health metrics."));
				productRepository.save(new Product(null, "Desk Lamp", 15.99, "https://m.media-amazon.com/images/I/61pJc6U5F0L._AC_UL320_.jpg", "LED desk lamp with adjustable brightness."));
			}
		};
	}
}
