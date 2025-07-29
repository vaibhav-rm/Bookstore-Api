# 📚 Bookstore RESTful API

A comprehensive RESTful API for managing books and authors in a bookstore, built with Spring Boot, JPA, and H2 Database made as the final project for Elevate Labs internship.

## 🚀 Features

- ✅ Complete CRUD Operations for Books and Authors
- 🔍 Advanced Search and Filtering capabilities
- 📄 Pagination and Sorting for all list endpoints
- ✅ Data Validation with comprehensive error handling
- 📘 Swagger/OpenAPI Documentation for easy API exploration
- 🗃️ H2 In-Memory Database for development and testing
- 🧪 Sample Data Initialization for immediate testing

## 🛠 Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **Swagger/OpenAPI 3**
- **Maven**

## ⚙️ Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:

```bash
git clone https://github.com/vaibhav-rm/Bookstore-Api
cd bookstore-api
```

2. Build the project:

```bash
mvn clean install
```

3. Run the application:

```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`.

## 📘 API Documentation

### Swagger UI

- [Swagger UI](http://localhost:8080/swagger-ui.html)
- [OpenAPI JSON](http://localhost:8080/api-docs)

### H2 Database Console

- **URL**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL**: `jdbc:h2:mem:bookstore`
- **Username**: `sa`
- **Password**: `password`

## 📡 API Endpoints

### Authors

| Method | Endpoint                  | Description                    |
|--------|---------------------------|--------------------------------|
| GET    | `/api/authors`           | Get all authors (paginated)    |
| GET    | `/api/authors/{id}`      | Get author by ID               |
| POST   | `/api/authors`           | Create new author              |
| PUT    | `/api/authors/{id}`      | Update author                  |
| DELETE | `/api/authors/{id}`      | Delete author                  |
| GET    | `/api/authors/search`    | Search authors by name         |
| GET    | `/api/authors/prolific`  | Get authors with multiple books|

### Books

| Method | Endpoint                      | Description               |
|--------|-------------------------------|---------------------------|
| GET    | `/api/books`                 | Get all books (paginated) |
| GET    | `/api/books/{id}`            | Get book by ID            |
| GET    | `/api/books/isbn/{isbn}`     | Get book by ISBN          |
| POST   | `/api/books`                 | Create new book           |
| PUT    | `/api/books/{id}`            | Update book               |
| DELETE | `/api/books/{id}`            | Delete book               |
| GET    | `/api/books/search`          | Advanced book search      |
| GET    | `/api/books/genre/{genre}`   | Get books by genre        |
| GET    | `/api/books/author/{authorId}` | Get books by author     |
| GET    | `/api/books/low-stock`       | Get low stock books       |

## 🔍 Query Parameters

### Pagination

- `page`: Page number (0-based, default: 0)
- `size`: Page size (default: 10)
- `sortBy`: Field to sort by (default varies)
- `sortDir`: Sort direction (`asc` or `desc`, default: `asc`)

### Book Search Filters

- `title`: Filter by book title (partial match)
- `genre`: Filter by genre
- `authorId`: Filter by author ID
- `minPrice`: Minimum price filter
- `maxPrice`: Maximum price filter

## 🧪 Sample Data

### Authors

- J.K. Rowling
- George Orwell  
- Jane Austen

### Books

- Harry Potter and the Philosopher's Stone
- 1984
- Pride and Prejudice
- Animal Farm

## 📚 Book Genres

- FICTION
- NON_FICTION
- MYSTERY
- ROMANCE
- SCIENCE_FICTION
- FANTASY
- BIOGRAPHY
- HISTORY
- SELF_HELP
- CHILDREN
- YOUNG_ADULT
- HORROR
- THRILLER
- POETRY
- DRAMA

## 📬 Example API Calls

### Create an Author

```bash
curl -X POST http://localhost:8080/api/authors   -H "Content-Type: application/json"   -d '{
    "firstName": "Stephen",
    "lastName": "King",
    "birthDate": "1947-09-21",
    "biography": "American author of horror and supernatural fiction."
  }'
```

### Create a Book

```bash
curl -X POST http://localhost:8080/api/books   -H "Content-Type: application/json"   -d '{
    "title": "The Shining",
    "isbn": "978-0307743657",
    "publicationDate": "1977-01-28",
    "genre": "HORROR",
    "price": 14.99,
    "description": "A horror novel about a family isolated in a haunted hotel.",
    "stockQuantity": 20,
    "author": {"id": 1}
  }'
```

### Search Books

```bash
curl "http://localhost:8080/api/books/search?title=Harry&genre=FANTASY&minPrice=10&maxPrice=20&page=0&size=10"
```

## ⚠️ Error Handling

- **400 Bad Request**: Validation errors
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: General server errors

## 📬 Testing with Postman

Use the provided collection `postman/Bookstore_API.postman_collection.json` to test all endpoints.

## 🧱 Project Structure

```
src/main/java/com/bookstore/
├── entity/          # JPA entities
├── repository/      # Data access layer
├── service/         # Business logic layer
├── controller/      # REST controllers
├── config/          # Configuration classes
└── exception/       # Exception handling
```

## 🔧 Key Features

1. One-to-Many relationship between Author and Book
2. Data validation using Bean Validation
3. Advanced search with JPQL
4. Global exception handler for consistent error responses
5. Full Swagger/OpenAPI docs
6. Sample data for demo

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to your branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.