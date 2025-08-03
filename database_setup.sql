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
INSERT INTO products (name, description, price, image_url, category, stock_quantity) VALUES
-- Electronics
('MacBook Pro 16"', 'Latest MacBook Pro with M3 chip, 16GB RAM, 512GB SSD', 2499.99, 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=300&h=300&fit=crop', 'Electronics', 25),
('iPhone 15 Pro', 'Apple iPhone 15 Pro with A17 Pro chip, 128GB storage', 999.99, 'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=300&h=300&fit=crop', 'Electronics', 50),
('Samsung Galaxy S24', 'Samsung Galaxy S24 Ultra with S Pen, 256GB', 1199.99, 'https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=300&h=300&fit=crop', 'Electronics', 40),
('Sony WH-1000XM5', 'Wireless noise-cancelling headphones with 30-hour battery', 349.99, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300&h=300&fit=crop', 'Electronics', 75),
('iPad Air', '10.9-inch iPad Air with M2 chip, 64GB storage', 599.99, 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=300&h=300&fit=crop', 'Electronics', 60),

-- Home & Kitchen
('Instant Pot Duo', '7-in-1 electric pressure cooker, 6-quart capacity', 89.99, 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop', 'Home & Kitchen', 120),
('Ninja Foodi', '9-in-1 Deluxe XL Cooker with TenderCrisp Technology', 199.99, 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop', 'Home & Kitchen', 85),
('Dyson V15', 'Cord-free vacuum cleaner with laser technology', 699.99, 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=300&h=300&fit=crop', 'Home & Kitchen', 45),
('KitchenAid Mixer', 'Professional 5-quart stand mixer in red', 379.99, 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop', 'Home & Kitchen', 30),
('Breville Coffee Maker', 'Barista Express espresso machine with grinder', 699.99, 'https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=300&h=300&fit=crop', 'Home & Kitchen', 25),

-- Books
('The Midnight Library', 'Matt Haig novel about infinite possibilities', 24.99, 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=300&fit=crop', 'Books', 200),
('Atomic Habits', 'James Clear guide to building good habits', 19.99, 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=300&fit=crop', 'Books', 150),
('The Seven Husbands of Evelyn Hugo', 'Taylor Jenkins Reid historical fiction', 16.99, 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=300&fit=crop', 'Books', 180),
('Project Hail Mary', 'Andy Weir science fiction novel', 27.99, 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=300&fit=crop', 'Books', 120),
('Lessons in Chemistry', 'Bonnie Garmus debut novel', 22.99, 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=300&fit=crop', 'Books', 160),

-- Fashion
('Nike Air Max 270', 'Comfortable running shoes with Air Max technology', 129.99, 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=300&h=300&fit=crop', 'Fashion', 100),
('Levi\'s 501 Jeans', 'Classic straight-fit denim jeans', 69.99, 'https://images.unsplash.com/photo-1542272604-787c3835535d?w=300&h=300&fit=crop', 'Fashion', 200),
('Ray-Ban Aviator', 'Classic aviator sunglasses with UV protection', 159.99, 'https://images.unsplash.com/photo-1572635196237-14b3f281503f?w=300&h=300&fit=crop', 'Fashion', 80),
('Casio G-Shock', 'Durable digital watch with shock resistance', 89.99, 'https://images.unsplash.com/photo-1524592094714-0f0654e20314?w=300&h=300&fit=crop', 'Fashion', 150),
('Adidas Ultraboost', 'Premium running shoes with Boost technology', 179.99, 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=300&h=300&fit=crop', 'Fashion', 75),

-- Sports & Outdoors
('Yeti Tundra 45', 'Premium hard cooler with 45-quart capacity', 299.99, 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop', 'Sports & Outdoors', 40),
('Patagonia Down Jacket', 'Lightweight insulated jacket for cold weather', 199.99, 'https://images.unsplash.com/photo-1551028719-00167b16eac5?w=300&h=300&fit=crop', 'Sports & Outdoors', 60),
('Coleman Tent', '4-person camping tent with weather protection', 149.99, 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop', 'Sports & Outdoors', 35),
('Garmin Fenix 7', 'Premium GPS smartwatch for outdoor activities', 699.99, 'https://images.unsplash.com/photo-1524592094714-0f0654e20314?w=300&h=300&fit=crop', 'Sports & Outdoors', 25),
('Peloton Bike', 'Smart exercise bike with live classes', 1495.00, 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=300&h=300&fit=crop', 'Sports & Outdoors', 20);
*/

-- Show database information
SELECT 'Order Processing System Database Setup Complete' AS status; 