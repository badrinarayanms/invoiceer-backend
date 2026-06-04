package com.badri.invoice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> showApiInfo() {

        String html = """
            <html>
            <head>
                <title>Invoicer API Documentation</title>
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        margin: 40px;
                        background-color: #f9f9f9;
                        color: #333;
                    }
                    h2 {
                        color: #2c3e50;
                        border-bottom: 2px solid #3498db;
                        padding-bottom: 6px;
                    }
                    h3 {
                        color: #2980b9;
                        margin-top: 30px;
                    }
                    table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-top: 12px;
                    }
                    table, th, td {
                        border: 1px solid #ccc;
                    }
                    th {
                        background-color: #ecf0f1;
                        padding: 12px;
                        text-align: left;
                    }
                    td {
                        padding: 10px;
                    }
                    code {
                        background-color: #eee;
                        padding: 3px 6px;
                        border-radius: 4px;
                        font-size: 14px;
                    }
                    pre {
                        background: #f4f4f4;
                        padding: 12px;
                        border-left: 4px solid #3498db;
                        overflow-x: auto;
                    }
                    p {
                        margin-top: 14px;
                    }
                    ul li {
                        margin-bottom: 6px;
                    }
                </style>
            </head>
            <body>

                <h2>Invoicer API Documentation</h2>

                <p>
                    This API powers the <b>Invoicer</b> system — a full-stack invoice management platform
                    for creating products, generating invoices, producing PDF bills, and emailing them to customers.
                </p>

                <ul>
                    <li><b>Frontend GitHub:</b> <a href="https://github.com/badrinarayanms/invoiceer-frontend">invoiceer-frontend</a></li>
                    <li><b>Live Frontend:</b> <a href="https://invoiceer-frontend.vercel.app/">https://invoiceer-frontend.vercel.app</a></li>
                    <li><b>Backend API:</b> <a href="https://invoiceer-o31i.onrender.com">https://invoiceer-o31i.onrender.com</a></li>
                </ul>

                <h3>Authentication</h3>
                <table>
                    <tr><th>Method</th><th>Endpoint</th><th>Description</th></tr>
                    <tr><td>POST</td><td>/auth/signup</td><td>Create a new user account</td></tr>
                    <tr><td>POST</td><td>/auth/login</td><td>Login using email & password (JWT issued as HttpOnly cookie)</td></tr>
                    <tr><td>POST</td><td>/logout</td><td>Logout user and clear JWT cookie</td></tr>
                </table>

                <p>
                    After login, a <code>JWT</code> token is stored securely in an <b>HttpOnly cookie</b>.
                    All protected endpoints require authentication.
                </p>

                <h4>Signup / Login JSON</h4>
                <pre><code>{
  "email": "user@example.com",
  "password": "strongPassword123",
  "companyName": "My Company Pvt Ltd"
}</code></pre>

                <h3>Products</h3>
                <table>
                    <tr><th>Method</th><th>Endpoint</th><th>Description</th></tr>
                    <tr><td>GET</td><td>/products</td><td>Get all active products (user-specific)</td></tr>
                    <tr><td>GET</td><td>/products/{id}</td><td>Get product by ID (owned by user)</td></tr>
                    <tr><td>POST</td><td>/products</td><td>Create a new product</td></tr>
                    <tr><td>PUT</td><td>/products/{id}</td><td>Update product details</td></tr>
                    <tr><td>DELETE</td><td>/products/{id}</td><td>Soft delete product (set active = false)</td></tr>
                </table>

                <p>
                    Products are <b>soft deleted</b> to preserve invoice history.
                    Deleted products are excluded from active listings.
                </p>

                <h4>Product JSON</h4>
                <pre><code>{
  "name": "Wireless Mouse",
  "price": 1299.99
}</code></pre>

                <h3>Invoices</h3>
                <table>
                    <tr><th>Method</th><th>Endpoint</th><th>Description</th></tr>
                    <tr><td>GET</td><td>/invoices</td><td>Get all invoices created by logged-in user</td></tr>
                    <tr><td>GET</td><td>/invoices/{id}</td><td>Get invoice details by ID</td></tr>
                    <tr><td>POST</td><td>/invoice</td><td>Create invoice, generate PDF, and email customer</td></tr>
                </table>

                <h4>Create Invoice JSON</h4>
                <pre><code>{
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
}</code></pre>

                <p>
                    On successful invoice creation:
                    <ul>
                        <li>Total amount is calculated automatically</li>
                        <li>PDF invoice is generated using iText</li>
                        <li>Invoice PDF is emailed via Gmail SMTP</li>
                    </ul>
                </p>

            </body>
            </html>
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(html, headers, HttpStatus.OK);
    }
}
