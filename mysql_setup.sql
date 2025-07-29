-- MySQL Setup Script for Order Processing System
-- Run this script as MySQL root user to configure the database

-- Option 1: If you want to set a password for root user (recommended for production)
-- ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';

-- Option 2: If you want to allow root user without password (for development only)
-- ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '';

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS order_processing_system
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Grant all privileges to root user on the database
GRANT ALL PRIVILEGES ON order_processing_system.* TO 'root'@'localhost';

-- Flush privileges to apply changes
FLUSH PRIVILEGES;

-- Show the created database
SHOW DATABASES;

-- Use the database
USE order_processing_system;

-- Show database information
SELECT 'Order Processing System Database Setup Complete' AS status; 