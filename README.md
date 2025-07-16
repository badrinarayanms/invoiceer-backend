# Invoice Spring Boot Application

This is a Spring Boot application for managing invoices and products, generating PDF invoices, and emailing them to customers. It uses PostgreSQL for data storage and integrates with Gmail SMTP for sending emails.

## Features

- Product CRUD operations
- Invoice creation with multiple items
- PDF invoice generation using iText 7
- Emailing invoices as PDF attachments
- RESTful API with CORS support for frontend integration

## Technologies Used

- Spring Boot
- Spring Data JPA
- Spring Mail
- PostgreSQL
- iText 7 (PDF generation)
- Lombok

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
