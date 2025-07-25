
# 📚 Bookstore REST API

A comprehensive Spring Boot REST API for managing a bookstore with features for books, authors, customers, orders, reviews, and shopping cart functionality.

**🎓 Final Internship Project - Elevate Labs**

## 🚀 Features

### 📖 Book Management
- Add, update, delete, and retrieve books.
- Filter books by title, genre, price, and more.
- Associate books with one or more authors.
- Upload cover images for books.

### ✍️ Author Management
- Create and manage authors.
- View books associated with specific authors.
- Search for authors by name.

### 👥 Customer Management
- Customer registration and login.
- Update customer profile details.
- Secure password storage with encryption.

### 🛒 Shopping Cart & Orders
- Add books to shopping cart.
- Create orders from the cart.
- View order history.
- Order status tracking.

### ⭐ Book Reviews & Ratings
- Add reviews for purchased books.
- Star ratings for books.
- Retrieve average rating and reviews.

### 📦 API Functionality
- RESTful API design with proper HTTP status codes.
- Error handling with custom exceptions.
- Pagination and sorting.
- Swagger UI for interactive API documentation.

### 🛡️ Security
- JWT-based authentication and authorization.
- Role-based access for admin and customers.
- Secure endpoints with Spring Security.

### 🧪 Testing
- Unit tests for services and controllers.
- Integration tests for API endpoints.
- Postman collection included.

### 🗃️ Database & Storage
- MySQL or PostgreSQL database support.
- Entity relationships using JPA & Hibernate.
- File storage for book covers.

### 🧰 Tools & Tech Stack
- Spring Boot 3.x
- Spring Data JPA
- Spring Security + JWT
- Swagger/OpenAPI (Springdoc)
- Lombok
- Maven
- Docker (optional)

---

## 🧑‍💻 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL or PostgreSQL
- (Optional) Docker

### Clone the repository
```bash
git clone https://github.com/vaibhav-rm/Bookstore-Api.git
cd Bookstore-Api
```

### Configure the application
Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore
spring.datasource.username=root
spring.datasource.password=yourpassword
jwt.secret=your_jwt_secret
file.upload-dir=uploads/
```

### Run the application
```bash
mvn spring-boot:run
```

### Access Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

---

## 📁 Project Structure

```
src
├── controller        # REST API controllers
├── service           # Business logic
├── model             # JPA entities
├── repository        # Spring Data JPA interfaces
├── config            # Security & app config
├── dto               # Data Transfer Objects
├── exception         # Custom exceptions & handlers
└── util              # Utility classes
```

---

## 📦 Sample API Endpoints

| Method | Endpoint                     | Description                   |
|--------|------------------------------|-------------------------------|
| GET    | /api/books                   | Get all books                 |
| GET    | /api/books/{id}              | Get book by ID                |
| POST   | /api/books                   | Create new book               |
| PUT    | /api/books/{id}              | Update book                   |
| DELETE | /api/books/{id}              | Delete book                   |
| GET    | /api/authors                 | List all authors              |
| POST   | /api/authors                 | Create author                 |
| POST   | /api/auth/signup             | Register new customer         |
| POST   | /api/auth/login              | Authenticate and get token    |
| POST   | /api/cart/add                | Add book to shopping cart     |
| POST   | /api/orders/place            | Place new order               |
| POST   | /api/reviews/{bookId}        | Add review for book           |

---

## 🔐 Authentication

JWT tokens are required for accessing protected endpoints.

### Example: Include token in request header
```
Authorization: Bearer <your-token>
```

---

## 🧪 Running Tests

```bash
mvn test
```

Or run specific test classes in your IDE.

---

## 📚 API Documentation

Access Swagger UI at:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 🐳 Docker Support (Optional)

Create a Dockerfile and docker-compose.yml for containerization.

```bash
docker build -t bookstore-api .
docker run -p 8080:8080 bookstore-api
```

---

## 🙌 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss.

---

## 📃 License

This project is licensed under the MIT License.

---

**Happy Coding! 🚀**
