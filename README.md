# 📦 Thrift Shop E-commerce — Back-end (Spring Boot)

REST API built with **Spring Boot,** featuring **PostgreSQL integration, DTO architecture, data validation, global error handling, advanced filtering, pagination,** and **Swagger documentation.**

This back-end provides the foundation for a simple e-commerce system focused on product management.

## 🚀 Technologies Used

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Hibernate
- Swagger / Springdoc OpenAPI
- Maven

## 📁 Project Structure

```css
src/main/java/com.thomazsilva.ecommerce
├── controller
├── dto
│   ├── request
│   └── response
├── exceptions
│   ├── handlers
│   └── custom
├── model
├── repository
├── service
└── EcommerceApplication.java
```

## 📌 Features Implemented

#### ✅ 1. Project Setup

Generated using Spring Initializr with all required dependencies.

---

#### ✅ 2. Layered Architecture

The project follows a clean architecture:

- Model → Product entity
- Repository → JPA interface
- Service → Business logic + CRUD operations
- Controller → REST endpoints

---

#### ✅ 3. PostgreSQL Database

Basic development database configured and connected through JPA with `application.properties`.

---

#### ✅ 4. Exception Handling (ProductNotFoundException)

Custom exception + handler to return proper HTTP codes:

- `404 NOT FOUND`

---

#### ✅ 5. Full CRUD Operations

- Create product
- Read product (list and by ID)
- Update product
- Delete product

---

#### ✅ 6. ResponseEntity Usage

Refactored controllers to use:

- ResponseEntity.ok()
- ResponseEntity.status().body()
- ResponseEntity.noContent()

---

#### ✅ 7. Advanced Filtering

Filtering supported by:

- product name
- minimum price
- maximum price
- combined filters
  Filter logic organized using a `FilterType` enum.

---

#### ✅ 8. Pagination & Sorting

Implemented via:

```java
Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
```

---

#### ✅ 9. DTOs & Validation

Created:

- `ProductRequestDTO` (input)
- `ProductResponseDTO` (output)

Validations include:

- `@NotBlank`
- `@NotNull`
- `@Positive`
- `@Size`
- `@Valid`
  Data conversion handled through:
- `fromDTO()`
- `toDTO()`
- `updateEntity()`

---

#### ✅ 10. Global Error Handling

Centralized with `@ControllerAdvice`.
Handles:

- Validation errors → **400 BAD REQUEST**
- Invalid parameters → **400**
- Missing products → **404**
  Returns consistent JSON error format.

---

#### ✅ 11. Swagger Documentation

Generated automatically with Springdoc:

- `/swagger-ui.html`
- `/v3/api-docs`
  Annotated with:
- `@Operation`
- `@ApiResponses`
- `@Schema`

## 🛠️ How to Run the Project

1. Clone the repository

```bash
git clone https://github.com/thomaz1593/brecho-ecommerce.git
```

2. Create the PostgreSQL database

```sql
CREATE DATABASE brecho_ecommerce;
```

3. Configure `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/brecho_ecommerce
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

4. Start the application

```bash
mvn spring-boot:run
```

## 📚 Main Endpoints

### 🔹 List Products (with filters, pagination, sorting)

| Method | Endpoint    | Description                                                                                                   |
| :----- | :---------- | :------------------------------------------------------------------------------------------------------------ |
| `GET`  | `/products` | Returns a paginated list of products. Supports filtering by name, minPrice, maxPrice, sorting, and pagination |

#### Query Parameters

| Name       | Type         | Required | Description                                          |
| :--------- | :----------- | :------- | :--------------------------------------------------- |
| `name`     | `String`     | No       | Filters products whose name contains the given value |
| `minPrice` | `BigDecimal` | No       | Minimum price filter                                 |
| `maxPrice` | `BigDecimal` | No       | Maximum price filter                                 |
| `page`     | `Integer`    | No       | Page index (default: 0)                              |
| `size`     | `Integer`    | No       | Page size (default: 10)                              |
| `sort`     | `String`     | No       | Field to sort by (e.g. `price`, `name`)              |

**Response (200 OK)**

Returns a `Page<ProductResponseDTO>`.

### 🔹 Get Product by ID

| Method | Endpoint         | Description                          |
| :----- | :--------------- | :----------------------------------- |
| `GET`  | `/products/{id}` | Retrieves a single product by its ID |

**Response Codes**
| Code | Description|
| :--------- | :---------- |
| 200 | Product found |
| 404 | Product not found |

### 🔹 Create a Product

| Method | Endpoint    | Description                                    |
| :----- | :---------- | :--------------------------------------------- |
| `POST` | `/products` | Creates a new product based on a validated DTO |

**Request Body (ProductRequestDTO)**

```json
{
  "name": "Example",
  "price": 49.99,
  "description": "Sample description",
  "imageUrl": "http://example.com/image.jpg"
}
```

**Response Codes**
| Code | Description|
| :--------- | :---------- |
| 201 | Product created |
| 400 | Validation error |

### 🔹 Update a Product

| Method | Endpoint         | Description                 |
| :----- | :--------------- | :-------------------------- |
| `PUT`  | `/products/{id}` | Updates an existing product |

**Request Body**

Same as `ProductRequestDTO`.

**Response Codes**
| Code | Description |
| :--------- | :---------- |
| 200 | Product updated successfully |
| 400 | Validation error |
| 404 | Product not found |

### 🔹 Delete a Product

| Method   | Endpoint         | Description            |
| :------- | :--------------- | :--------------------- |
| `DELETE` | `/products/{id}` | Remove a Product by ID |

**Response Codes**
| Code | Description|
| :--------- | :---------- |
| 204 | Product deleted |
| 404 | Product not found |

## ❗ Error Response Example

All errors follow this JSON format:

```json
{
  "status": 400,
  "message": "Validation failed",
  "fieldErrors": [
    {
      "field": "price",
      "message": "must be positive"
    }
  ],
  "timestamp": "2025-01-30T14:22:10"
}
```

## 📖 Swagger Documentation

After starting the application:

[Swagger link](http://localhost:8080/swagger-ui.html)

## ✔️ Project Status

🟢 Back-end: basic implementation finished

🟡 Front-end development will begin next

## 👩‍💻 Author

- [@thomaz1593](https://github.com/thomaz1593)

## 📄 License

Educational project — free to use and modify.
