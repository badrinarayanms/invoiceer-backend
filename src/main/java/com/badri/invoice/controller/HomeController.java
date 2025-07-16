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
                            padding-bottom: 5px;
                        }
                        h3 {
                            color: #2980b9;
                            margin-top: 30px;
                        }
                        table {
                            width: 100%;
                            border-collapse: collapse;
                            margin-top: 10px;
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
                            padding: 2px 4px;
                            border-radius: 4px;
                            font-size: 14px;
                        }
                        pre {
                            background: #f4f4f4;
                            padding: 10px;
                            border-left: 4px solid #3498db;
                            overflow-x: auto;
                        }
                        p {
                            margin-top: 15px;
                        }
                    </style>
                </head>
                <body>
                    <h2> Invoicer API Documentation</h2>
                    <p>This API powers the Invoicer system. Use it to manage products and generate customer invoices. You can test these endpoints using Postman, or check out the frontend dashboard:</p>
                    <ul>
                        <li>Frontend GitHub: <a href="https://github.com/badrinarayanms/invoiceer-frontend">invoiceer-frontend</a></li>
                        <li>Live Demo: <a href="https://invoiceer-frontend.vercel.app/">https://invoiceer-frontend.vercel.app/</a></li>
                        <li>Backend API: <a href="https://invoiceer-o31i.onrender.com">https://invoiceer-o31i.onrender.com</a></li>
                    </ul>

                    <h3> Products</h3>
                    <table>
                        <tr><th>Method</th><th>Endpoint</th><th>Description</th></tr>
                        <tr><td>GET</td><td>/products</td><td>Get all products</td></tr>
                        <tr><td>GET</td><td>/products/{id}</td><td>Get a specific product by ID</td></tr>
                        <tr><td>POST</td><td>/products</td><td>Add a new product</td></tr>
                        <tr><td>PUT</td><td>/products/{id}</td><td>Update a product by ID</td></tr>
                        <tr><td>DELETE</td><td>/products/{id}</td><td>Delete a product by ID</td></tr>
                    </table>
                    <p> Deleting a product that's used in invoices will return a <code>409 Conflict</code> error to prevent data loss.</p>

                    <h4>JSON Format for <code>POST</code> or <code>PUT /products</code></h4>
                    <pre><code>{
  "name": "Wireless Mouse",
  "price": 1299.99
}</code></pre>

                    <h3> Invoices</h3>
                    <table>
                        <tr><th>Method</th><th>Endpoint</th><th>Description</th></tr>
                        <tr><td>GET</td><td>/invoices</td><td>Get all invoices</td></tr>
                        <tr><td>GET</td><td>/invoices/{id}</td><td>Get a specific invoice by ID</td></tr>
                        <tr><td>POST</td><td>/invoice</td><td>Create an invoice with line items.<br>PDF is generated and emailed to customer</td></tr>
                    </table>
                    <p> While creating an invoice, pass an array of items containing the product ID and quantity.</p>

                    <h4> JSON Format for <code>POST /invoice</code></h4>
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
                </body>
                </html>
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(html, headers, HttpStatus.OK);
    }
}
