# Invoice Spring Boot Application

This is a Spring Boot application for managing invoices and products, generating PDF invoices, and emailing them to customers. It uses PostgreSQL for data storage and integrates with Gmail SMTP for sending emails.

👉 You can test the API using **Postman**, or check out the **frontend dashboard** built with Next.js:
- 🔗 GitHub (Frontend): [https://github.com/badrinarayanms/invoiceer-frontend](https://github.com/badrinarayanms/invoiceer-frontend)
- 🌐 Live Demo: [https://invoiceer-frontend.vercel.app](https://invoiceer-frontend.vercel.app)
- 🚀 Deployed Backend: [https://invoiceer-o31i.onrender.com](https://invoiceer-o31i.onrender.com)
  
## 🚀 Deployment

The backend was:
- ✅ **Dockerized**: The Spring Boot JAR was packaged inside a Docker image
- ☁️ **Hosted on Render** using a containerized deployment (Docker image pushed to Docker Hub)



---

## Features

- Product CRUD operations
- Invoice creation with multiple items
- PDF invoice generation using iText 7
- Emailing invoices as PDF attachments
- RESTful API with CORS support for frontend integration

---

## API Endpoints

### 🔹 Products

| Method | Endpoint             | Description                         |
|--------|----------------------|-------------------------------------|
| GET    | `/products`          | Get all products                    |
| GET    | `/products/{id}`     | Get a specific product by ID        |
| POST   | `/products`          | Add a new product                   |
| PUT    | `/products/{id}`     | Update a product by ID              |
| DELETE | `/products/{id}`     | Delete a product by ID              |

> ⚠️ Deleting a product that's used in invoices will return a `409 Conflict` error to prevent data loss.

---
## 📥 JSON Format for `POST` or `PUT /products`


<pre> <code>json
{
  "name": "Wireless Mouse",
  "price": 1299.99
}
</code> </pre>

### 🔹 Invoices

| Method | Endpoint             | Description                            |
|--------|----------------------|----------------------------------------|
| GET    | `/invoices`          | Get all invoices                       |
| GET    | `/invoices/{id}`     | Get a specific invoice by ID           |
| POST   | `/invoice`           | Create an invoice with line items. PDF is generated and emailed to customer |

> ✅ While creating an invoice, you must pass an array of items containing the product ID and quantity.

---
## 📥 JSON Format for JSON Format for `POST /invoice`


<pre> <code>json
{
  "customerName": "John Doe",
  "customerEmail": "john@example.com",
  "items": [
    {
      "product": { "id": 1 },
      "quantity": 2
    },
    {
      "product": { "id": 3 },
      "quantity": 1
    }
  ]
}

</code> </pre>

## Technologies Used

- Spring Boot
- Spring Data JPA
- Spring Mail
- PostgreSQL
- iText 7 (PDF generation)
- Lombok

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL database

### Configuration

Set environment variables or update `src/main/resources/application.properties`:

```properties
DB_URL=jdbc:postgresql://localhost:5432/invoice_db
DB_USER=your_db_user
DB_PASSWORD=your_db_password
FRONTEND_URL=http://localhost:3000
MAIL_GMAIL=your_gmail_address@gmail.com
MAIL_APPPASS=your_gmail_app_password
