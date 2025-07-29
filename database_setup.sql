-- MySQL Database Setup for Order Processing System
-- Run this script in your MySQL client to set up the database

-- Create the database
CREATE DATABASE IF NOT EXISTS order_processing_system
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE order_processing_system;

-- Note: Tables will be automatically created by Hibernate/JPA when the application starts
-- with spring.jpa.hibernate.ddl-auto=update

-- Optional: Insert sample products after tables are created
-- Run this after the application has started and created the tables

/*
INSERT INTO products (name, description, price, image_url, stock_quantity) VALUES
('Laptop', 'High-performance laptop with latest specifications', 999.99, 'laptop.jpg', 50),
('Smartphone', 'Latest smartphone with advanced features', 699.99, 'smartphone.jpg', 100),
('Headphones', 'Wireless noise-cancelling headphones', 199.99, 'headphones.jpg', 75),
('Tablet', '10-inch tablet with high-resolution display', 399.99, 'tablet.jpg', 30),
('Smartwatch', 'Fitness tracking smartwatch', 299.99, 'smartwatch.jpg', 60),
('Camera', 'Digital camera with 4K video recording', 799.99, 'camera.jpg', 25),
('Gaming Console', 'Next-generation gaming console', 499.99, 'gaming-console.jpg', 40),
('Wireless Speaker', 'Portable Bluetooth speaker', 149.99, 'speaker.jpg', 80);
*/

-- Show database information
SELECT 'Order Processing System Database Setup Complete' AS status; 