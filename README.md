# Order Processing System

A comprehensive e-commerce order processing system built with Spring Boot, featuring user authentication, shopping cart management, order processing, and real-time status tracking.

## 🚀 Features

- **User Management**: Registration, authentication, and profile management with JWT tokens
- **Product Catalog**: Browse and search products with dynamic loading
- **Shopping Cart**: Add, remove, and manage cart items with server-side validation
- **Order Management**: Create, view, and manage orders with status tracking
- **Address Management**: Store and manage delivery addresses
- **Order Status Tracking**: Real-time order status updates with background processing
- **API Documentation**: Complete Swagger/OpenAPI documentation
- **Security**: JWT-based authentication with Spring Security

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.5.4, Spring Security, Spring Data JPA
- **Database**: MySQL 8.0
- **Frontend**: HTML, CSS, JavaScript (Vanilla)
- **API Documentation**: Swagger/OpenAPI 3.0
- **Build Tool**: Gradle
- **Java Version**: 17
- **Authentication**: JWT (JSON Web Tokens)

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Gradle (or use the included wrapper)

### . Access the Application
- **Home Page**: http://localhost:9090
- **Swagger UI**: http://localhost:9090/swagger-ui.html
- **API Documentation**: http://localhost:9090/api-docs

## 🔐 Authentication

The system uses JWT (JSON Web Token) authentication.

## 📚 API Documentation

### Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/auth/register` | Register a new user | ❌ |
| POST | `/auth/login` | Login user | ❌ |
| GET | `/auth/profile` | Get current user profile | ✅ |

### Product Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/products` | List all products | ❌ |
| GET | `/products/{id}` | Get product by ID | ❌ |

### Cart Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/cart` | Get user's shopping cart | ✅ |
| POST | `/cart/add` | Add item to cart | ✅ |
| PUT | `/cart/update` | Update cart item quantity | ✅ |
| DELETE | `/cart/remove` | Remove item from cart | ✅ |
| DELETE | `/cart/clear` | Clear entire cart | ✅ |

### Order Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/orders` | Create new order | ✅ |
| GET | `/orders` | List user's orders | ✅ |
| GET | `/orders/{id}` | Get order by ID | ❌ |
| GET | `/orders/search/{id}` | Search user's own order by ID | ✅ |
| PUT | `/orders/{id}/status` | Update order status | ❌ |
| POST | `/orders/{id}/cancel` | Cancel order (PENDING only) | ❌ |

### Address Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/addresses` | List user's addresses | ✅ |
| POST | `/addresses` | Add new address | ✅ |

## 🏗️ Project Structure

```
Order processing system/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── config/          # Configuration classes
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── JwtConfig.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── DataInitializer.java
│   │   │   ├── controller/      # REST controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── CartController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   └── AddressController.java
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   │   ├── OrderDTO.java
│   │   │   │   ├── CartDTO.java
│   │   │   │   ├── CartItemDTO.java
│   │   │   │   └── OrderMapper.java
│   │   │   ├── model/          # Entity classes
│   │   │   │   ├── Order.java
│   │   │   │   ├── User.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Cart.java
│   │   │   │   ├── Address.java
│   │   │   │   └── OrderStatus.java
│   │   │   ├── repository/     # Data access layer
│   │   │   │   ├── OrderRepository.java
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── CartRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   └── AddressRepository.java
│   │   │   ├── service/        # Business logic
│   │   │   │   ├── OrderService.java
│   │   │   │   ├── CartService.java
│   │   │   │   └── OrderStatusScheduler.java
│   │   │   └── util/           # Utility classes
│   │   │       └── JwtUtil.java
│   │   └── resources/
│   │       ├── static/         # Frontend files
│   │       │   ├── index.html
│   │       │   ├── login.html
│   │       │   └── my-orders.html
│   │       └── application.properties
│   └── test/                   # Test files
│       └── java/com/example/demo/
│           ├── controller/
│           ├── service/
│           └── OrderProcessingSystemApplicationTests.java
├── data/                       # Application data files
├── build.gradle               # Gradle build configuration
├── settings.gradle            # Gradle settings
└── .vscode/                   # VS Code configuration
    ├── settings.json
    ├── launch.json
    └── tasks.json
```


```properties
# Server configuration
server.port=9090








