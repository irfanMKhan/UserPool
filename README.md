# Project: UserPool
## Feature:
- "User Pool Management System" — A secure web-based system that allows user registration, login, role-based access, and JWT-authenticated access to protected endpoints.

## User Stories:
- As a user, I want to register with an email and password so that I can access the platform.
- As a user, I want to securely log in so that I can receive a JWT token for protected actions.
- As an admin, I want to view all users so that I can manage user roles.
- As an admin, I want to deactivate a user so that access can be revoked securely.
- As a system, I should protect API endpoints using JWT so only authenticated users can access sensitive information.

## Project Scope:
- User Registration & Login with JWT-based authentication.
- Role-based access control (e.g., USER, ADMIN).
- Secured RESTful API using Spring Security.
- User profile management.
- Admin dashboard for managing users.
- CI/CD-ready with Docker support.
- Basic unit tests for JWT functionality.

## Justification:
- Spring boot: Quick setup, RESTful services, security integration, and scalability.
- Spring Security: Provides secure JWT integration and role-based access out-of-the-box.
- JWT: Stateless authentication can be used for refreshed token.
- PostgreSQL: Good for structured user data with constraints.
- gradle: flexibility, faster build times, and more intuitive configuration.

## ER Diagram:
![ER Diagram.png](images/ER%20Diagram.png)

## Docker image build: 
- docker buildx build -t irfan/userpool:1.0.0 .
- docker images
- docker run -p 8080:8080 {image-id}