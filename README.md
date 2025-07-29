# Order Processing System

A comprehensive e-commerce order processing system built with Spring Boot, featuring user authentication, shopping cart management, order processing, and real-time status tracking.

## 🚀 Features

- **User Management**: Registration, authentication, and profile management
- **Product Catalog**: Browse and search products with dynamic loading
- **Shopping Cart**: Add, remove, and manage cart items with server-side validation
- **Order Management**: Create, view, and manage orders with status tracking
- **Address Management**: Store and manage delivery addresses
- **Order Status Tracking**: Real-time order status updates with background processing
- **API Documentation**: Complete Swagger/OpenAPI documentation

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.5.4, Spring Security, Spring Data JPA
- **Database**: MySQL 8.0
- **Frontend**: HTML, CSS, JavaScript (Vanilla)
- **API Documentation**: Swagger/OpenAPI 3.0
- **Build Tool**: Gradle
- **Java Version**: 17

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Gradle (or use the included wrapper)

## 🗄️ Database Setup

### 1. Install MySQL

Make sure MySQL is installed and running on your system.


-- Run the setup script
source mysql_setup.sql
```

### 3. Create Database

Run the database setup script:


-- Run the setup script
source mysql_setup.sql
```

Or manually create the database:

```sql
CREATE DATABASE IF NOT EXISTS order_processing_system
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

### 4. Configure Application Properties

Choose the appropriate configuration based on your MySQL setup:


## 🏃‍♂️ Running the Application

### 1. Build the Application

```bash
./gradlew build
```

### 2. Run the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:9090`

### 3. Access the Application

- **Home Page**: http://localhost:9090
- **Swagger UI**: http://localhost:9090/swagger-ui.html
- **API Documentation**: http://localhost:9090/api-docs

## 📚 API Documentation

### Authentication

Most endpoints require authentication using the `X-Auth-Token` header. Obtain a token by registering or logging in through the `/auth` endpoints.

### Complete API Reference

#### Authentication Endpoints

| Method | Endpoint | Description | Auth Required | Request Body | Response |
|--------|----------|-------------|---------------|--------------|----------|
| POST | `/auth/register` | Register a new user | ❌ | `{"username": "john", "password": "password123", "email": "john@example.com"}` | `{"message": "User registered successfully"}` |
| POST | `/auth/login` | Login user | ❌ | `{"username": "john", "password": "password123"}` | `{"token": "uuid-token"}` |
| GET | `/auth/profile` | Get current user profile | ✅ | - | `{"id": 1, "username": "john", "email": "john@example.com"}` |

#### Product Endpoints

| Method | Endpoint | Description | Auth Required | Request Body | Response |
|--------|----------|-------------|---------------|--------------|----------|
| GET | `/products` | List all products | ❌ | - | `[{"id": 1, "name": "Laptop", "price": 999.99, "description": "High-performance laptop"}]` |

#### Cart Management Endpoints

| Method | Endpoint | Description | Auth Required | Request Body | Response |
|--------|----------|-------------|---------------|--------------|----------|
| GET | `/cart` | Get user's shopping cart | ✅ | - | `{"id": 1, "items": [...], "total": 1999.98}` |
| POST | `/cart/add` | Add item to cart | ✅ | `productId=1&quantity=2` | `{"id": 1, "items": [...], "total": 1999.98}` |
| PUT | `/cart/update` | Update cart item quantity | ✅ | `productId=1&quantity=3` | `{"id": 1, "items": [...], "total": 2999.97}` |
| DELETE | `/cart/remove` | Remove item from cart | ✅ | `productId=1` | `{"id": 1, "items": [...], "total": 999.99}` |
| DELETE | `/cart/clear` | Clear entire cart | ✅ | - | `204 No Content` |

#### Order Endpoints

| Method | Endpoint | Description | Auth Required | Request Body | Response |
|--------|----------|-------------|---------------|--------------|----------|
| POST | `/orders` | Create new order | ✅ | `{"customerName": "John", "addressId": 1, "items": [{"productId": 1, "quantity": 2}]}` | `{"id": 1, "status": "PENDING", "total": 1999.98}` |
| GET | `/orders` | List user's orders | ✅ | - | `[{"id": 1, "status": "PENDING", "total": 1999.98}]` |
| GET | `/orders/{id}` | Get order by ID | ❌ | - | `{"id": 1, "status": "PENDING", "total": 1999.98}` |
| GET | `/orders/search/{id}` | Search user's own order by ID | ✅ | - | `{"id": 1, "status": "PENDING", "total": 1999.98}` |
| PUT | `/orders/{id}/status` | Update order status | ❌ | `status=SHIPPED` | `{"id": 1, "status": "SHIPPED"}` |
| POST | `/orders/{id}/cancel` | Cancel order (PENDING only) | ❌ | - | `{"id": 1, "status": "CANCELED"}` |

#### Address Endpoints

| Method | Endpoint | Description | Auth Required | Request Body | Response |
|--------|----------|-------------|---------------|--------------|----------|
| GET | `/addresses` | List user's addresses | ✅ | - | `[{"id": 1, "street": "123 Main St", "city": "New York"}]` |
| POST | `/addresses` | Add new address | ✅ | `{"street": "123 Main St", "city": "New York", "state": "NY", "zipCode": "10001"}` | `{"id": 1, "street": "123 Main St", "city": "New York"}` |

### HTTP Status Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 201 | Created |
| 204 | No Content |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 500 | Internal Server Error |

### Order Status Values

| Status | Description |
|--------|-------------|
| PENDING | Order is created and waiting for processing |
| PROCESSING | Order is being processed |
| SHIPPED | Order has been shipped |
| DELIVERED | Order has been delivered |
| CANCELED | Order has been canceled |

## 🔒 Business Rules

- **Data Isolation**: Users can only access their own data (orders, cart, addresses)
- **Order Cancellation**: Orders can only be cancelled if they are in `PENDING` status
- **Price Locking**: Cart prices are locked at the time of addition to prevent manipulation
- **Background Processing**: Orders automatically update from `PENDING` to `PROCESSING` every 5 minutes
- **Authentication Required**: Most endpoints require valid `X-Auth-Token` header

## 🏗️ Development

### Project Structure

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── config/          # Configuration classes
│   │   │   ├── SecurityConfig.java
│   │   │   └── SwaggerConfig.java
│   │   ├── controller/      # REST controllers
│   │   │   ├── AuthController.java
│   │   │   ├── CartController.java
│   │   │   ├── OrderController.java
│   │   │   └── AddressController.java
│   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── OrderDTO.java
│   │   │   ├── CartDTO.java
│   │   │   └── CartItemDTO.java
│   │   ├── model/          # Entity classes
│   │   │   ├── Order.java
│   │   │   ├── User.java
│   │   │   ├── Product.java
│   │   │   └── Cart.java
│   │   ├── repository/     # Data access layer
│   │   │   ├── OrderRepository.java
│   │   │   ├── UserRepository.java
│   │   │   └── CartRepository.java
│   │   └── service/        # Business logic
│   │       ├── OrderService.java
│   │       ├── CartService.java
│   │       └── OrderStatusScheduler.java
│   └── resources/
│       ├── static/         # Frontend files
│       │   ├── index.html
│       │   ├── login.html
│       │   └── my-orders.html
│       └── application.properties
└── test/                   # Test files
    └── java/com/example/demo/
        ├── controller/
        ├── service/
        └── OrderProcessingSystemApplicationTests.java
```

### Key Components

- **CartService**: Manages shopping cart operations with price locking
- **OrderService**: Handles order creation and management
- **AuthController**: User authentication and session management
- **OrderStatusScheduler**: Background job for status updates
- **SwaggerConfig**: API documentation configuration

## 🧪 Testing

### Run All Tests
```bash
./gradlew test
```

### Run Specific Test Class
```bash
./gradlew test --tests OrderControllerTest
```

### Test Coverage
- **Unit Tests**: Service layer business logic
- **Integration Tests**: Controller layer API endpoints
- **Component Tests**: Full application context



