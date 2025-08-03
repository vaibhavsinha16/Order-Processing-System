# JWT Authentication Implementation

This document describes the JWT (JSON Web Token) authentication implementation for the Order Processing System.

## Overview

The system has been updated to use JWT tokens for authentication instead of the previous in-memory session-based approach. This provides better security, scalability, and stateless authentication.

## Key Changes

### 1. Dependencies Added
- `io.jsonwebtoken:jjwt-api:0.12.3`
- `io.jsonwebtoken:jjwt-impl:0.12.3`
- `io.jsonwebtoken:jjwt-jackson:0.12.3`

### 2. New Components

#### JwtConfig
- Configuration class for JWT settings
- Configurable secret key and expiration time
- Properties can be set in `application.properties`

#### JwtUtil
- Utility class for JWT operations
- Token generation, validation, and parsing
- Extracts user information from tokens

#### JwtAuthenticationFilter
- Spring Security filter for JWT authentication
- Intercepts requests and validates JWT tokens
- Sets up authentication context for protected endpoints

### 3. Updated Components

#### SecurityConfig
- Updated to use JWT authentication
- Stateless session management
- JWT filter integration
- Protected endpoints configuration

#### AuthController
- Updated to generate JWT tokens on login
- Returns token, username, and userId on successful login
- Updated profile endpoint to use Authorization header

#### Controllers
- All controllers updated to use `Authorization: Bearer <token>` header
- Replaced `X-Auth-Token` with standard `Authorization` header

#### Frontend
- Updated all JavaScript code to use `Authorization: Bearer <token>` header
- Enhanced login response handling
- Improved error handling

## Configuration

### JWT Settings in application.properties
```properties
jwt.secret=your-secret-key-should-be-very-long-and-secure-for-production-use-change-this-in-production
jwt.expiration=86400000
```

### Security Configuration
- Public endpoints: `/auth/**`, `/products`, `/cart/**`, `/addresses`, Swagger UI
- Protected endpoints: `/orders/**` and other authenticated operations
- Stateless session management

## API Changes

### Authentication Header
- **Old**: `X-Auth-Token: <token>`
- **New**: `Authorization: Bearer <token>`

### Login Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "john_doe",
  "userId": 1
}
```

### Profile Endpoint
- **Old**: `GET /auth/profile` with `X-Auth-Token` header
- **New**: `GET /auth/profile` with `Authorization: Bearer <token>` header

## Security Features

1. **Token Expiration**: Tokens expire after 24 hours (configurable)
2. **Secure Signing**: HMAC-SHA256 signing algorithm
3. **Stateless**: No server-side session storage
4. **Standard Headers**: Uses standard Authorization header format
5. **Automatic Validation**: Spring Security automatically validates tokens

## Testing

The test files have been updated to:
- Use the new Authorization header format
- Include JWT configuration in test context
- Mock JWT token validation

## Migration Notes

1. **Existing Users**: No migration needed - users can re-login to get JWT tokens
2. **Frontend**: All frontend code has been updated to use new authentication format
3. **API Clients**: External API clients need to update to use `Authorization: Bearer <token>` header

## Production Considerations

1. **Secret Key**: Change the default secret key in production
2. **HTTPS**: Always use HTTPS in production
3. **Token Expiration**: Consider shorter expiration times for sensitive applications
4. **Refresh Tokens**: Consider implementing refresh tokens for better user experience
5. **Token Storage**: Store tokens securely on the client side

## Example Usage

### Login
```bash
curl -X POST http://localhost:9090/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"password"}'
```

### Using the Token
```bash
curl -X GET http://localhost:9090/orders \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

## Benefits

1. **Scalability**: No server-side session storage
2. **Security**: Standard JWT security practices
3. **Interoperability**: Standard Authorization header format
4. **Performance**: Reduced server memory usage
5. **Microservices**: Better suited for microservices architecture 