# Starz Records E-Commerce API
<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/9fdb6633-a0ef-459d-8677-6f310a128941" />


## Overview

EasyShop is a RESTful e-commerce backend API built with **Java Spring Boot** and **MySQL**. It supports product and category management, shopping cart functionality, and order checkout with role-based security.

---

## Tech Stack

* IntelliJ
* insomnia
  
## Features

* Browse products and categories (public)
* Persistent shopping cart for logged-in users
* Checkout process that converts cart into orders
* Role-based access control (USER / ADMIN)

---

## API Endpoints

### Categories

* `GET /categories`
* `GET /categories/{id}`
* `POST /categories` (ADMIN)
* `PUT /categories/{id}` (ADMIN)
* `DELETE /categories/{id}` (ADMIN)

### Products

* `GET /products`
* `GET /products/{id}`
* `POST /products` (ADMIN)
* `PUT /products/{id}` (ADMIN)
* `DELETE /products/{id}` (ADMIN)

### Shopping Cart

* `GET /cart`
* `POST /cart/products/{productId}`
* `PUT /cart/products/{productId}`
* `DELETE /cart`

### Orders

* `POST /orders` (Checkout)

---

## Database Setup

```sql
CREATE DATABASE easyshop;
```

Update `application.properties` with your MySQL credentials.

---

## Testing

* Manual API testing with Insomnia
* Verified CRUD operations and security rules

  <img width="1247" height="919" alt="image" src="https://github.com/user-attachments/assets/10b5a7cf-5ec1-4bc5-81c6-d1c9fd1a9307" />

  <img width="1330" height="921" alt="image" src="https://github.com/user-attachments/assets/338b0adb-e2c2-488d-afd1-6044f65d6977" />


