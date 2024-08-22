# Backend of Spring Boot e-Commerce App

This eCommerce backend project is built with a robust and modern tech stack to provide a scalable, secure, and efficient solution for managing an online store. The project leverages Spring Boot, MySQL, Spring Data JPA, Maven, JUnit, Mockito, Redis, Swagger, and Spring Security to offer a comprehensive set of features, ensuring high performance and reliability.

## Tech Stack

- **Spring Boot**: A powerful framework for building Java-based applications, providing a wide range of functionalities out-of-the-box.
- **MySQL**: A reliable and widely-used relational database management system.
- **Spring Data JPA**: Simplifies database interactions using the Java Persistence API, providing a rich repository layer.
- **Maven**: A build automation tool used for managing project dependencies and build lifecycle.
- **JUnit**: A testing framework for writing and running unit tests in Java.
- **Mockito**: A mocking framework for unit tests in Java.
- **Redis**: An in-memory data structure store, used for caching and session management.
- **Swagger**: A tool for generating API documentation and exploring API endpoints.
- **Spring Security**: A comprehensive security framework for authentication, authorization, and more.
- **Lombok**: A Java library that helps reduce boilerplate code by automatically generating getters, setters, constructors, and other   common methods through annotations.
- **RestClient and RestTemplate**: Tools for consuming external service APIs, such as a sales tax API and internal APIs as well.
- **RabbitMQ**: A message broker used for sending and receiving messages, particularly for handling order email confirmations asynchronously.

## Features

- **Modern Java Features**:
  - **`var`**: Utilizes type inference to make the code more concise and readable.
  - **Streams API**: Leverages streams for efficient and expressive data processing, enabling operations like filtering, mapping, and reducing.
  - **Lambda Expressions**: Implements lambda expressions for more concise and functional-style code, particularly in collections and streams.
  - **Method References**: Uses method references as a shorthand for lambda expressions, making the code more readable and expressive.
  - **Optionals**: Uses `Optional` to handle potentially null values in a safe and expressive manner, reducing the risk of `NullPointerException`.
  - **Records**: Implements records for immutable data carriers, reducing boilerplate code and enhancing the clarity of data structure definitions.
  - **Text Blocks**: Employs text blocks for easier handling and readability of multi-line strings, especially in SQL queries.


- **Role-Based Access Control (RBAC)**: Implemented at the method level to ensure fine-grained security for different user roles.
- **Session Management**: Efficient handling of user sessions to maintain security and performance.
- **Entrypoint Customization**: Customization of security entry points to provide a flexible and secure authentication mechanism.
- **Profiling**: Allows the application to run with different profiles, enabling environment-specific configurations.
- **JPA Auditing**: Automatically tracks and logs entity changes, providing an audit trail for database operations.
- **Pagination, Filtering, Searching, and Sorting**: Comprehensive support for managing large datasets with ease.
- **REST API Best Practices**: Follows industry standards to ensure robust, maintainable, and scalable API design.
- **External API Consumption**: Integrates with external service APIs (e.g., sales tax API) using RestClient to retrieve and process data from third-party services.
- **Comprehensive Exception Handling**: Ensures that the application gracefully handles and logs exceptions, providing meaningful error messages.
- **Detailed Swagger Documentation**: Automatically generated API documentation for easy exploration and testing of endpoints.
- **Unit Testing**: Extensive use of JUnit and Mockito for reliable and maintainable tests.
- **CSV Exporting**: Ability to export reports into CSV files based on SQL querying retrieval, enabling easy data analysis and sharing.
- **CSV Import and Persistence**: Supports uploading CSV files and automatically persists the data into the database, streamlining data management and integration.
- **Data Validation**: Ensures that data integrity and correctness are maintained through comprehensive validation mechanisms at various layers.
- **Data Caching**: Implements efficient caching strategies to enhance application performance and reduce the load on the database by storing frequently accessed data in memory.
- **Spring Application Events**: Utilized for handling `OrderCreation`, `ProductCreation`, and `UserCreation` events.


# CategoryController REST Endpoints

### 1. Retrieve All Categories
- **Endpoint:** `GET /api/categories`
- **Description:** Paginated retrieval for all categories.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of categories.
  - `204 No Content` - The list of categories is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 2. Get Category By ID
- **Endpoint:** `GET /api/categories/{id}`
- **Description:** Retrieve a single category by ID.
- **PreAuthorize:** `hasAnyRole('SUPER_ADMIN','ADMIN')`
- **Responses:**
  - `200 OK` - Successfully found the category.
  - `404 Not Found` - Category not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.

---

### 3. Create a New Category
- **Endpoint:** `POST /api/categories`
- **Description:** Create a new category.
- **PreAuthorize:** `hasAnyRole('SUPER_ADMIN','ADMIN')`
- **Request Body:** `CategoryRequestDTO`
- **Responses:**
  - `201 Created` - Successfully created the category.
  - `400 Bad Request` - Invalid request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 4. Update Category
- **Endpoint:** `PUT /api/categories/{id}`
- **Description:** Update an existing category.
- **PreAuthorize:** `hasAnyRole('SUPER_ADMIN','ADMIN')`
- **Request Body:** `CategoryRequestDTO`
- **Responses:**
  - `200 OK` - Successfully updated the category.
  - `404 Not Found` - Category not found.
  - `400 Bad Request` - Invalid ID or request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 5. Delete Category By ID
- **Endpoint:** `DELETE /api/categories/{id}`
- **Description:** Delete a category by ID.
- **PreAuthorize:** `hasAnyRole('SUPER_ADMIN','ADMIN')`
- **Responses:**
  - `204 No Content` - Successfully deleted the category.
  - `404 Not Found` - Category not found.
  - `400 Bad Request` - Invalid ID.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 6. Search Categories
- **Endpoint:** `GET /api/categories/search`
- **Description:** Search for categories based on name, description, or product name.
- **Query Parameters:**
  - `name` (optional) - Category name to search for.
  - `desc` (optional) - Category description to search for.
  - `productName` (optional) - Product name to search for.
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the search results.
  - `204 No Content` - No results found.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 7. Add Products to Category
- **Endpoint:** `PATCH /api/categories/add_products`
- **Description:** Add products to an existing category.
- **PreAuthorize:** `hasAnyRole('SUPER_ADMIN','ADMIN')`
- **Request Body:** `AddProductsToCategoryRequest`
- **Responses:**
  - `200 OK` - Successfully added products to the category.
  - `404 Not Found` - Category not found.
  - `400 Bad Request` - Invalid ID or request body.
  - `500 Internal Server Error` - An error occurred on the server.



# ProductController REST Endpoints

### 1. Retrieve All Products
- **Endpoint:** `GET /api/products`
- **Description:** Paginated retrieval for all products.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of products.
  - `204 No Content` - The list of products is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 2. Get Product By ID
- **Endpoint:** `GET /api/products/{id}`
- **Description:** Retrieve a single product by ID.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Responses:**
  - `200 OK` - Successfully found the product.
  - `404 Not Found` - Product not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.

---

### 3. Create a New Product
- **Endpoint:** `POST /api/products`
- **Description:** Create a new product.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Request Body:** `ProductRequestDTO`
- **Responses:**
  - `201 Created` - Successfully created the product.
  - `400 Bad Request` - Invalid request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 4. Update Product
- **Endpoint:** `PUT /api/products/{id}`
- **Description:** Update an existing product.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Request Body:** `ProductRequestDTO`
- **Responses:**
  - `200 OK` - Successfully updated the product.
  - `404 Not Found` - Product not found.
  - `400 Bad Request` - Invalid ID or request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 5. Delete Product By ID
- **Endpoint:** `DELETE /api/products/{id}`
- **Description:** Delete a product by ID.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Responses:**
  - `204 No Content` - Successfully deleted the product.
  - `404 Not Found` - Product not found.
  - `400 Bad Request` - Invalid ID.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 6. Search Products
- **Endpoint:** `GET /api/products/search`
- **Description:** Search for products based on various criteria.
- **Query Parameters:**
  - `name` (optional) - Product name to search for.
  - `desc` (optional) - Product description to search for.
  - `discountStatus` (optional) - Discount status to search for.
  - `categoryName` (optional) - Category name to search for.
  - `minPrice` (optional) - Minimum price to search for.
  - `maxPrice` (optional) - Maximum price to search for.
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the search results.
  - `204 No Content` - No results found.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 7. Find Products By Category ID
- **Endpoint:** `GET /api/products/category/{id}`
- **Description:** Retrieve all products for a specific category by ID.
- **PreAuthorize:** `hasRole('ADMIN')`
- **Responses:**
  - `200 OK` - Successfully retrieved the list of products for the category.
  - `204 No Content` - No products found for the category.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 8. Retrieve Products by Set of Ids
- **Endpoint:** `GET /api/products/product_ids`
- **Responses:**
  - `200 OK` - Successfully retrieved the list of products for the category.
  - `204 No Content` - No products found for the category.
  - `500 Internal Server Error` - An error occurred on the server.


# DiscountController REST Endpoints

### 1. Retrieve All Discounts
- **Endpoint:** `GET /api/discounts`
- **Description:** Paginated retrieval for all discounts.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Query Parameters:**
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of discounts.
  - `204 No Content` - The list of discounts is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 2. Get Discount By ID
- **Endpoint:** `GET /api/discounts/{id}`
- **Description:** Retrieve a single discount by ID.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Path Variable:**
  - `id` - The ID of the discount to retrieve.
- **Responses:**
  - `200 OK` - Successfully found the discount.
  - `404 Not Found` - Discount not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.

---

### 3. Create a New Discount
- **Endpoint:** `POST /api/discounts`
- **Description:** Create a new discount.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Request Body:** `DiscountRequestDTO`
- **Responses:**
  - `201 Created` - Successfully created the discount.
  - `400 Bad Request` - Invalid request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 4. Update Discount
- **Endpoint:** `PUT /api/discounts/{id}`
- **Description:** Update an existing discount.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Request Body:** `DiscountRequestDTO`
- **Path Variable:**
  - `id` - The ID of the discount to update.
- **Responses:**
  - `200 OK` - Successfully updated the discount.
  - `404 Not Found` - Discount not found.
  - `400 Bad Request` - Invalid ID or request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 5. Delete Discount By ID
- **Endpoint:** `DELETE /api/discounts/{id}`
- **Description:** Delete a discount by ID.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Path Variable:**
  - `id` - The ID of the discount to delete.
- **Responses:**
  - `204 No Content` - Successfully deleted the discount.
  - `404 Not Found` - Discount not found.
  - `400 Bad Request` - Invalid ID.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 6. Add Products to Discount
- **Endpoint:** `PATCH /api/discounts/add_products`
- **Description:** Add products to an existing discount.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Request Body:** `AddProductsToDiscountRequest`
- **Responses:**
  - `200 OK` - Successfully added products to the discount.
  - `404 Not Found` - Discount not found.
  - `400 Bad Request` - Invalid request body or discount ID.
  - `500 Internal Server Error` - An error occurred on the server.



# InventoryController REST Endpoints

### 1. Retrieve All Inventories
- **Endpoint:** `GET /api/inventories`
- **Description:** Paginated retrieval for all inventories.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Query Parameters:**
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of inventories.
  - `204 No Content` - The list of inventories is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 2. Get Inventory By ID
- **Endpoint:** `GET /api/inventories/{id}`
- **Description:** Retrieve a single inventory by ID.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Path Variable:**
  - `id` - The ID of the inventory to retrieve.
- **Responses:**
  - `200 OK` - Successfully found the inventory.
  - `404 Not Found` - Inventory not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.
 
---

### 3. Get Inventory By Product Id
- **Endpoint:** `GET /api/inventories/product/{id}`
- **Description:** Retrieve a single Inventory by Product Id.
- **Path Variable:**
  - `id` - Id of the Product to retrieve inventory for.
- **Responses:**
  - `200 OK` - Successfully found the inventory.
  - `404 Not Found` - Inventory not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.

---

### 4. Create a New Inventory
- **Endpoint:** `POST /api/inventories`
- **Description:** Create a new inventory.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Request Body:** `InventoryDTO`
- **Responses:**
  - `201 Created` - Successfully created the inventory.
  - `400 Bad Request` - Invalid request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 5. Update Inventory
- **Endpoint:** `PUT /api/inventories/{id}`
- **Description:** Update an existing inventory.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Request Body:** `InventoryDTO`
- **Path Variable:**
  - `id` - The ID of the inventory to update.
- **Responses:**
  - `200 OK` - Successfully updated the inventory.
  - `404 Not Found` - Inventory not found.
  - `400 Bad Request` - Invalid ID or request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 6. Delete Inventory By ID
- **Endpoint:** `DELETE /api/inventories/{id}`
- **Description:** Delete an inventory by ID.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Path Variable:**
  - `id` - The ID of the inventory to delete.
- **Responses:**
  - `204 No Content` - Successfully deleted the inventory.
  - `404 Not Found` - Inventory not found.
  - `400 Bad Request` - Invalid ID.
  - `500 Internal Server Error` - An error occurred on the server.



# OrderController REST Endpoints

### 1. Retrieve All Orders
- **Endpoint:** `GET /api/orders`
- **Description:** Paginated retrieval for all orders.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Query Parameters:**
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of orders.
  - `204 No Content` - The list of orders is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 2. Get Order By ID
- **Endpoint:** `GET /api/orders/{id}`
- **Description:** Retrieve a single order by ID.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Path Variable:**
  - `id` - The ID of the order to retrieve.
- **Responses:**
  - `200 OK` - Successfully found the order.
  - `404 Not Found` - Order not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.

---

### 3. Create a New Order
- **Endpoint:** `POST /api/orders`
- **Description:** Create a new order.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Request Body:** `OrderDTO`
- **Responses:**
  - `201 Created` - Successfully created the order.
  - `400 Bad Request` - Invalid request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 4. Update Order
- **Endpoint:** `PUT /api/orders/{id}`
- **Description:** Update an existing order.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Request Body:** `OrderDTO`
- **Path Variable:**
  - `id` - The ID of the order to update.
- **Responses:**
  - `200 OK` - Successfully updated the order.
  - `404 Not Found` - Order not found.
  - `400 Bad Request` - Invalid ID or request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 5. Delete Order By ID
- **Endpoint:** `DELETE /api/orders/{id}`
- **Description:** Delete an order by ID.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Path Variable:**
  - `id` - The ID of the order to delete.
- **Responses:**
  - `204 No Content` - Successfully deleted the order.
  - `404 Not Found` - Order not found.
  - `400 Bad Request` - Invalid ID.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 6. Find Orders by User ID
- **Endpoint:** `GET /api/orders/user/{id}`
- **Description:** Retrieve all orders associated with a specific user ID.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Path Variable:**
  - `id` - The ID of the user to find orders for.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of orders for the user.
  - `204 No Content` - No orders found for the user.
  - `500 Internal Server Error` - An error occurred on the server.



# CartItemController REST Endpoints

### 1. Retrieve All Cart Items
- **Endpoint:** `GET /api/cartitems`
- **Description:** Paginated retrieval of all cart items.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Query Parameters:**
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of cart items.
  - `204 No Content` - The list of cart items is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 2. Get Cart Item By ID
- **Endpoint:** `GET /api/cartitems/{id}`
- **Description:** Retrieve a single cart item by ID.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Path Variable:**
  - `id` - The ID of the cart item to retrieve.
- **Responses:**
  - `200 OK` - Successfully found the cart item.
  - `404 Not Found` - Cart item not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.

---

### 3. Create a New Cart Item
- **Endpoint:** `POST /api/cartitems`
- **Description:** Create a new cart item.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Request Body:** `CartItemDTO`
- **Responses:**
  - `201 Created` - Successfully created the cart item.
  - `400 Bad Request` - Invalid request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 4. Update Cart Item
- **Endpoint:** `PUT /api/cartitems/{id}`
- **Description:** Update an existing cart item.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Request Body:** `CartItemDTO`
- **Path Variable:**
  - `id` - The ID of the cart item to update.
- **Responses:**
  - `200 OK` - Successfully updated the cart item.
  - `404 Not Found` - Cart item not found.
  - `400 Bad Request` - Invalid ID or request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 5. Delete Cart Item By ID
- **Endpoint:** `DELETE /api/cartitems/{id}`
- **Description:** Delete a cart item by ID.
- **PreAuthorize:** `hasAnyRole('SUPERADMIN', 'ADMIN')`
- **Path Variable:**
  - `id` - The ID of the cart item to delete.
- **Responses:**
  - `204 No Content` - Successfully deleted the cart item.
  - `404 Not Found` - Cart item not found.
  - `400 Bad Request` - Invalid ID.
  - `500 Internal Server Error` - An error occurred on the server.




# OrderItemController REST Endpoints

### 1. Retrieve All Order Items
- **Endpoint:** `GET /api/orderitems`
- **Description:** Paginated retrieval of all order items.
- **Query Parameters:**
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of order items.
  - `204 No Content` - The list of order items is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 2. Get Order Item By ID
- **Endpoint:** `GET /api/orderitems/{id}`
- **Description:** Retrieve a single order item by ID.
- **Path Variable:**
  - `id` - The ID of the order item to retrieve.
- **Responses:**
  - `200 OK` - Successfully found the order item.
  - `404 Not Found` - Order item not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.

---

### 3. Create a New Order Item
- **Endpoint:** `POST /api/orderitems`
- **Description:** Create a new order item.
- **Request Body:** `OrderItemDTO`
- **Responses:**
  - `201 Created` - Successfully created the order item.
  - `400 Bad Request` - Invalid request body or ordered quantity exceeds product inventory.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 4. Update Order Item
- **Endpoint:** `PUT /api/orderitems/{id}`
- **Description:** Update an existing order item.
- **Request Body:** `OrderItemDTO`
- **Path Variable:**
  - `id` - The ID of the order item to update.
- **Responses:**
  - `200 OK` - Successfully updated the order item.
  - `404 Not Found` - Order item not found.
  - `400 Bad Request` - Invalid ID, request body, or ordered quantity exceeds product inventory.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 5. Delete Order Item By ID
- **Endpoint:** `DELETE /api/orderitems/{id}`
- **Description:** Delete an order item by ID.
- **Path Variable:**
  - `id` - The ID of the order item to delete.
- **Responses:**
  - `204 No Content` - Successfully deleted the order item.
  - `404 Not Found` - Order item not found.
  - `400 Bad Request` - Invalid ID.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 6. Find Order Details With Product ID
- **Endpoint:** `GET /api/orderitems/product/{id}`
- **Description:** Retrieve order details associated with a specific product ID.
- **Path Variable:**
  - `id` - The product ID to retrieve associated order items.
- **Responses:**
  - `200 OK` - Successfully found the order items for the product.
  - `204 No Content` - No order items found for the specified product.
  - `500 Internal Server Error` - An error occurred on the server.




# PaymentDetailController REST Endpoints

### 1. Retrieve All Payment Details
- **Endpoint:** `GET /api/payment_details`
- **Description:** Paginated retrieval of all payment details.
- **Query Parameters:**
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of payment details.
  - `204 No Content` - The list of payment details is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 2. Get Payment Detail By ID
- **Endpoint:** `GET /api/payment_details/{id}`
- **Description:** Retrieve a single payment detail by ID.
- **Path Variable:**
  - `id` - The ID of the payment detail to retrieve.
- **Responses:**
  - `200 OK` - Successfully found the payment detail.
  - `404 Not Found` - Payment detail not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.

---

### 3. Create a New Payment Detail
- **Endpoint:** `POST /api/payment_details`
- **Description:** Create a new payment detail.
- **Request Body:** `PaymentDetailDTO`
- **Responses:**
  - `201 Created` - Successfully created the payment detail.
  - `400 Bad Request` - Invalid request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 4. Update Payment Detail
- **Endpoint:** `PUT /api/payment_details/{id}`
- **Description:** Update an existing payment detail.
- **Request Body:** `PaymentDetailDTO`
- **Path Variable:**
  - `id` - The ID of the payment detail to update.
- **Responses:**
  - `200 OK` - Successfully updated the payment detail.
  - `404 Not Found` - Payment detail not found.
  - `400 Bad Request` - Invalid ID or request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 5. Delete Payment Detail By ID
- **Endpoint:** `DELETE /api/payment_details/{id}`
- **Description:** Delete a payment detail by ID.
- **Path Variable:**
  - `id` - The ID of the payment detail to delete.
- **Responses:**
  - `204 No Content` - Successfully deleted the payment detail.
  - `404 Not Found` - Payment detail not found.
  - `400 Bad Request` - Invalid ID.
  - `500 Internal Server Error` - An error occurred on the server.




# ReviewsController REST Endpoints

### 1. Retrieve All Reviews by Product ID
- **Endpoint:** `GET /api/reviews/product_id/{productId}`
- **Description:** Paginated retrieval of all reviews for a given product ID.
- **Path Variable:**
  - `productId` - The ID of the product to retrieve reviews for.
- **Query Parameters:**
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of reviews.
  - `204 No Content` - The list of reviews is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 2. Retrieve All Reviews by Product Name
- **Endpoint:** `GET /api/reviews/product_name/{productName}`
- **Description:** Paginated retrieval of all reviews for a given product name.
- **Path Variable:**
  - `productName` - The name of the product to retrieve reviews for.
- **Query Parameters:**
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of reviews.
  - `204 No Content` - The list of reviews is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 3. Get Review By ID
- **Endpoint:** `GET /api/reviews/{id}`
- **Description:** Retrieve a single review by ID.
- **Path Variable:**
  - `id` - The ID of the review to retrieve.
- **Responses:**
  - `200 OK` - Successfully found the review.
  - `404 Not Found` - Review not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.

---

### 4. Create a New Review
- **Endpoint:** `POST /api/reviews`
- **Description:** Create a new review.
- **Request Body:** `ReviewsRequestDTO`
- **Responses:**
  - `201 Created` - Successfully created the review.
  - `400 Bad Request` - Invalid request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 5. Update Review
- **Endpoint:** `PUT /api/reviews/{id}`
- **Description:** Update an existing review.
- **Path Variable:**
  - `id` - The ID of the review to update.
- **Request Body:** `ReviewsUpdateRequestDTO`
- **Responses:**
  - `200 OK` - Successfully updated the review.
  - `404 Not Found` - Review not found.
  - `400 Bad Request` - Invalid ID or request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 6. Delete Review By ID
- **Endpoint:** `DELETE /api/reviews/{id}`
- **Description:** Delete a review by ID.
- **Path Variable:**
  - `id` - The ID of the review to delete.
- **Responses:**
  - `204 No Content` - Successfully deleted the review.
  - `404 Not Found` - Review not found.
  - `400 Bad Request` - Invalid ID.
  - `500 Internal Server Error` - An error occurred on the server.




# SessionController REST Endpoints

### 1. Retrieve All User Shopping Sessions
- **Endpoint:** `GET /api/sessions`
- **Description:** Paginated retrieval of all user shopping sessions.
- **Query Parameters:**
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of user shopping sessions.
  - `204 No Content` - The list of user shopping sessions is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 2. Get Shopping Session By ID
- **Endpoint:** `GET /api/sessions/{id}`
- **Description:** Retrieve a single shopping session by ID.
- **Path Variable:**
  - `id` - The ID of the shopping session to retrieve.
- **Responses:**
  - `200 OK` - Successfully found the shopping session.
  - `404 Not Found` - Shopping session not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.

---

### 3. Create a New User Shopping Session
- **Endpoint:** `POST /api/sessions`
- **Description:** Create a new user shopping session.
- **Request Body:** `UserShoppingSessionDTO`
- **Responses:**
  - `201 Created` - Successfully created the user shopping session.
  - `400 Bad Request` - Invalid request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 4. Update Shopping Session
- **Endpoint:** `PUT /api/sessions/{id}`
- **Description:** Update an existing shopping session.
- **Path Variable:**
  - `id` - The ID of the shopping session to update.
- **Request Body:** `UserShoppingSessionDTO`
- **Responses:**
  - `200 OK` - Successfully updated the shopping session.
  - `404 Not Found` - Shopping session not found.
  - `400 Bad Request` - Invalid ID or request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 5. Delete Shopping Session By ID
- **Endpoint:** `DELETE /api/sessions/{id}`
- **Description:** Delete a shopping session by ID.
- **Path Variable:**
  - `id` - The ID of the shopping session to delete.
- **Responses:**
  - `204 No Content` - Successfully deleted the shopping session.
  - `404 Not Found` - Shopping session not found.
  - `400 Bad Request` - Invalid ID.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 6. Get Shopping Session By User ID
- **Endpoint:** `GET /api/sessions/user/{id}`
- **Description:** Retrieve the shopping session for a specific user by user ID.
- **Path Variable:**
  - `id` - The ID of the user to retrieve the shopping session for.
- **Responses:**
  - `200 OK` - Successfully retrieved the shopping session for the user.
  - `404 Not Found` - Shopping session not found.
  - `500 Internal Server Error` - An error occurred on the server.




# UserAddressController REST Endpoints

### 1. Retrieve All User Addresses
- **Endpoint:** `GET /api/addresses`
- **Description:** Paginated retrieval of all user addresses.
- **Query Parameters:**
  - `page` (default: `0`) - The page number to retrieve.
  - `size` (default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of user addresses.
  - `204 No Content` - The list of user addresses is empty.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 2. Get User Address By ID
- **Endpoint:** `GET /api/addresses/{id}`
- **Description:** Retrieve a single user address by ID.
- **Path Variable:**
  - `id` - The ID of the user address to retrieve.
- **Responses:**
  - `200 OK` - Successfully found the user address.
  - `404 Not Found` - User address not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.

---

### 3. Create a New User Address
- **Endpoint:** `POST /api/addresses`
- **Description:** Create a new user address.
- **Request Body:** `UserAddressDTO`
- **Responses:**
  - `201 Created` - Successfully created the user address.
  - `400 Bad Request` - Invalid request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 4. Update User Address
- **Endpoint:** `PUT /api/addresses/{id}`
- **Description:** Update an existing user address.
- **Path Variable:**
  - `id` - The ID of the user address to update.
- **Request Body:** `UserAddressDTO`
- **Responses:**
  - `200 OK` - Successfully updated the user address.
  - `404 Not Found` - User address not found.
  - `400 Bad Request` - Invalid ID or request body.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 5. Delete User Address By ID
- **Endpoint:** `DELETE /api/addresses/{id}`
- **Description:** Delete a user address by ID.
- **Path Variable:**
  - `id` - The ID of the user address to delete.
- **Responses:**
  - `204 No Content` - Successfully deleted the user address.
  - `404 Not Found` - User address not found.
  - `400 Bad Request` - Invalid ID.
  - `500 Internal Server Error` - An error occurred on the server.

---

### 6. Get User Address By User ID
- **Endpoint:** `GET /api/addresses/user/{id}`
- **Description:** Retrieve the address for a specific user by user ID.
- **Path Variable:**
  - `id` - The ID of the user to retrieve the address for.
- **Responses:**
  - `200 OK` - Successfully retrieved the address for the user.
  - `404 Not Found` - User address not found.
  - `500 Internal Server Error` - An error occurred on the server.



# UserAuthenticationController REST Endpoints

### 1. Register a New User
- **Endpoint:** `POST /auth/signup`
- **Description:** Register a new user with the provided details.
- **Request Body:** `UserRegistrationRequestDTO`
- **Responses:**
  - `201 Created` - User is successfully registered.
  - `400 Bad Request` - Invalid request body (e.g., missing required fields, validation errors).

---

### 2. Login User
- **Endpoint:** `POST /auth/login`
- **Description:** Authenticate a user and return a login token.
- **Request Body:** `UserLoginRequestDTO`
- **Responses:**
  - `200 OK` - Successfully authenticated, returns login details.
  - `400 Bad Request` - Invalid request body (e.g., missing required fields, validation errors).
  - `401 Unauthorized` - Invalid credentials.

---

### 3. Register a New Admin
- **Endpoint:** `POST /auth/admin/register`
- **Description:** Register a new admin user with the provided details.
- **Request Body:** `UserRegistrationRequestDTO`
- **Responses:**
  - `201 Created` - Admin is successfully registered.
  - `400 Bad Request` - Invalid request body (e.g., missing required fields, validation errors).
- **Security:** Requires `SUPERADMIN` role.

---

### 2. Get User By ID
- **Endpoint:** `GET /api/users/{id}`
- **Description:** Retrieve a single User by ID.
- **Path Variable:**
  - `id` - The ID of the User to retrieve.
- **Responses:**
  - `200 OK` - Successfully found the user.
  - `404 Not Found` - user not found.
  - `400 Bad Request` - Invalid ID (e.g., negative ID).
  - `500 Internal Server Error` - An error occurred on the server.



# UserPaymentController REST Endpoints

### 1. Retrieve All User Payments
- **Endpoint:** `GET /api/user_payments`
- **Description:** Retrieve a paginated list of all user payments.
- **Query Parameters:**
  - `page` (optional, default: 0) - The page number to retrieve.
  - `size` (optional, default: 10) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of user payments.
  - `204 No Content` - No user payments found.
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 2. Get User Payment By Id
- **Endpoint:** `GET /api/user_payments/{id}`
- **Description:** Retrieve a specific user payment by its ID.
- **Path Variables:**
  - `id` - The ID of the user payment to retrieve.
- **Responses:**
  - `200 OK` - Successfully found the user payment.
  - `404 Not Found` - User payment with the given ID was not found.
  - `400 Bad Request` - Invalid ID provided.
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 3. Create a New User Payment
- **Endpoint:** `POST /api/user_payments`
- **Description:** Create a new user payment with the provided details.
- **Request Body:** `UserPaymentDTO`
- **Responses:**
  - `201 Created` - Successfully created the user payment.
  - `400 Bad Request` - Invalid request body (e.g., missing required fields, validation errors).
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 4. Update User Payment
- **Endpoint:** `PUT /api/user_payments/{id}`
- **Description:** Update an existing user payment by its ID.
- **Path Variables:**
  - `id` - The ID of the user payment to update.
- **Request Body:** `UserPaymentDTO`
- **Responses:**
  - `200 OK` - Successfully updated the user payment.
  - `404 Not Found` - User payment with the given ID was not found.
  - `400 Bad Request` - Invalid ID or request body (e.g., missing required fields, validation errors).
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 5. Delete User Payment By Id
- **Endpoint:** `DELETE /api/user_payments/{id}`
- **Description:** Delete a specific user payment by its ID.
- **Path Variables:**
  - `id` - The ID of the user payment to delete.
- **Responses:**
  - `204 No Content` - Successfully deleted the user payment.
  - `404 Not Found` - User payment with the given ID was not found.
  - `400 Bad Request` - Invalid ID provided.
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 6. Find Payments By User Id
- **Endpoint:** `GET /api/user_payments/user/{id}`
- **Description:** Retrieve a list of user payments for a specific user by their ID.
- **Path Variables:**
  - `id` - The ID of the user to find payments for.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of user payments.
  - `404 Not Found` - User payments for the given user ID were not found.
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 7. Find Payments By Provider
- **Endpoint:** `GET /api/user_payments/provider/{providerName}`
- **Description:** Retrieve a list of user payments by payment provider.
- **Path Variables:**
  - `providerName` - The name of the payment provider to filter by.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of user payments by provider.
  - `204 No Content` - No payments found for the given provider.
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 8. Find Payments By Type
- **Endpoint:** `GET /api/user_payments/type/{typeName}`
- **Description:** Retrieve a list of user payments by payment type.
- **Path Variables:**
  - `typeName` - The name of the payment type to filter by.
- **Responses:**
  - `200 OK` - Successfully retrieved the list of user payments by type.
  - `204 No Content` - No payments found for the given type.
  - `500 Internal Server Error` - Unexpected error occurred.




# WishListController REST Endpoints

### 1. Create a New Wish List
- **Endpoint:** `POST /api/wishlists`
- **Description:** Create a new wish list with the provided details.
- **Request Body:** `WishListCreationRequest`
- **Responses:**
  - `201 Created` - Successfully created the wish list.
  - `400 Bad Request` - Invalid request body (e.g., missing required fields, validation errors).
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 2. Merge Wish List
- **Endpoint:** `PUT /api/wishlists`
- **Description:** Merge a wish list with the provided details.
- **Request Body:** `WishListMergeRequest`
- **Responses:**
  - `201 Created` - Successfully merged the wish list.
  - `400 Bad Request` - Invalid request body (e.g., missing required fields, validation errors).
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 3. Update Wish List
- **Endpoint:** `PUT /api/wishlists/{id}`
- **Description:** Update an existing wish list by its ID.
- **Path Variables:**
  - `id` - The ID of the wish list to update.
- **Request Body:** `WishListUpdateRequest`
- **Responses:**
  - `200 OK` - Successfully updated the wish list.
  - `404 Not Found` - Wish list with the given ID was not found.
  - `400 Bad Request` - Invalid ID or request body (e.g., missing required fields, validation errors).
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 4. Delete Wish List By Id
- **Endpoint:** `DELETE /api/wishlists/{id}`
- **Description:** Delete a specific wish list by its ID.
- **Path Variables:**
  - `id` - The ID of the wish list to delete.
- **Responses:**
  - `204 No Content` - Successfully deleted the wish list.
  - `404 Not Found` - Wish list with the given ID was not found.
  - `400 Bad Request` - Invalid ID provided.
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 5. Add Product to Wish List
- **Endpoint:** `PATCH /api/wishlists/add/product_id/{pid}/wishlist_id/{wid}`
- **Description:** Add a product to a wish list.
- **Path Variables:**
  - `pid` - The ID of the product to add.
  - `wid` - The ID of the wish list to add the product to.
- **Responses:**
  - `200 OK` - Successfully added the product to the wish list.
  - `404 Not Found` - Wish list or product with the given ID was not found.
  - `400 Bad Request` - Invalid ID provided.
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 6. Remove Product from Wish List
- **Endpoint:** `PATCH /api/wishlists/remove/product_id/{pid}/wishlist_id/{wid}`
- **Description:** Remove a product from a wish list.
- **Path Variables:**
  - `pid` - The ID of the product to remove.
  - `wid` - The ID of the wish list to remove the product from.
- **Responses:**
  - `200 OK` - Successfully removed the product from the wish list.
  - `404 Not Found` - Wish list or product with the given ID was not found.
  - `400 Bad Request` - Invalid ID provided.
  - `500 Internal Server Error` - Unexpected error occurred.





# CsvImportController REST Endpoints

### 1. Upload Categories CSV
- **Endpoint:** `POST /api/upload_csv_file/categories`
- **Description:** Upload a CSV file containing category data.
- **Request Parameters:**
  - `file` - The CSV file to upload.
- **Request Body:** `MultipartFile`
- **Responses:**
  - `200 OK` - Successfully processed the CSV file. Returns the number of categories uploaded.
  - `400 Bad Request` - Invalid file or request.
  - `500 Internal Server Error` - Unexpected error occurred.

---

### 2. Upload Products CSV
- **Endpoint:** `POST /api/upload_csv_file/products`
- **Description:** Upload a CSV file containing product data.
- **Request Parameters:**
  - `file` - The CSV file to upload.
- **Request Body:** `MultipartFile`
- **Responses:**
  - `200 OK` - Successfully processed the CSV file. Returns the number of products uploaded.
  - `400 Bad Request` - Invalid file or request.
  - `500 Internal Server Error` - Unexpected error occurred.





# ReportController REST Endpoints

## 1. Export Products by Category Name
- **Endpoint:** `GET /api/reports/product_by_category_name/{category_name}`
- **Description:** Generate a report of all products in a specific category and save it as a CSV file.
- **Request Parameters:**
  - `category_name` (Path Variable) - The name of the category to retrieve products for.
- **Responses:**
  - `200 OK` - Report generated and saved to `C:\Users\hp\Documents\NetBeansProjects\eComMaster\product_by_category_name.csv`.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 2. Export Active Discount Products
- **Endpoint:** `GET /api/reports/active_discount_products`
- **Description:** Generate a report of all products with active discounts and save it as a CSV file.
- **Responses:**
  - `200 OK` - Report generated and saved to `C:\Users\hp\Documents\NetBeansProjects\eComMaster\active_discount_products.csv`.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 3. Export Products by Rating Above
- **Endpoint:** `GET /api/reports/products_by_rating_above/{rating}`
- **Description:** Generate a report of all products with ratings above a specified value and save it as a CSV file.
- **Request Parameters:**
  - `rating` (Path Variable) - The minimum rating to filter products.
- **Responses:**
  - `200 OK` - Report generated and saved to `C:\Users\hp\Documents\NetBeansProjects\eComMaster\products_by_rating_above.csv`.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 4. Export Out of Stock Products
- **Endpoint:** `GET /api/reports/out_of_stock_products`
- **Description:** Generate a report of all products that are out of stock and save it as a CSV file.
- **Responses:**
  - `200 OK` - Report generated and saved to `C:\Users\hp\Documents\NetBeansProjects\eComMaster\out_of_stock_products.csv`.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 5. Export Orders with Products from Category
- **Endpoint:** `GET /api/reports/orders_with_category_name/{category_name}`
- **Description:** Generate a report of all orders that include products from a specific category and save it as a CSV file.
- **Request Parameters:**
  - `category_name` (Path Variable) - The name of the category to retrieve orders for.
- **Responses:**
  - `200 OK` - Report generated and saved to `C:\Users\hp\Documents\NetBeansProjects\eComMaster\orders_with_category_name.csv`.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 6. Export Users Who Used a Payment Provider
- **Endpoint:** `GET /api/reports/users_who_used_provider/{provider}`
- **Description:** Generate a report of all users who have used a specific payment provider and save it as a CSV file.
- **Request Parameters:**
  - `provider` (Path Variable) - The payment provider to filter users by.
- **Responses:**
  - `200 OK` - Report generated and saved to `C:\Users\hp\Documents\NetBeansProjects\eComMaster\users_who_used_provider.csv`.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 7. Export Reviews for Category
- **Endpoint:** `GET /api/reports/reviews_for_category_name/{category_name}`
- **Description:** Generate a report of all reviews for products in a specific category and save it as a CSV file.
- **Request Parameters:**
  - `category_name` (Path Variable) - The name of the category to retrieve reviews for.
- **Responses:**
  - `200 OK` - Report generated and saved to `C:\Users\hp\Documents\NetBeansProjects\eComMaster\reviews_for_category_name.csv`.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 8. Get Average Rating for Product
- **Endpoint:** `GET /api/reports/avg_rating_for_product/{productName}`
- **Description:** Retrieve the average rating for a specific product.
- **Request Parameters:**
  - `productName` (Path Variable) - The name of the product to retrieve the average rating for.
- **Responses:**
  - `200 OK` - Average rating of the product.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 9. Get Total Cart Quantity for User
- **Endpoint:** `GET /api/reports/total_cart_qty_for_user/{userId}`
- **Description:** Retrieve the total quantity of items in the cart for a specific user.
- **Request Parameters:**
  - `userId` (Path Variable) - The ID of the user to retrieve the total cart quantity for.
- **Responses:**
  - `200 OK` - Total cart quantity for the user.
  - `500 Internal Server Error` - Unexpected error occurred.




# SalesTaxController REST Endpoints

## 1. Retrieve All Sales Taxes
- **Endpoint:** `GET /api/sales_taxes`
- **Description:** Paginated retrieval of all sales taxes.
- **Request Parameters:**
  - `page` (Query Parameter, default: `0`) - The page number to retrieve.
  - `size` (Query Parameter, default: `10`) - The number of items per page.
- **Responses:**
  - `200 OK` - Successfully retrieved sales tax list. Response contains a paginated list of `SalesTaxResponse`.
  - `204 No Content` - List of sales taxes is empty.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 2. Get Sales Tax By Id
- **Endpoint:** `GET /api/sales_taxes/{id}`
- **Description:** Retrieve a single sales tax by its ID.
- **Request Parameters:**
  - `id` (Path Variable) - ID of the sales tax to retrieve.
- **Responses:**
  - `200 OK` - Sales tax successfully found. Response contains a `SalesTaxResponse`.
  - `404 Not Found` - Sales tax with the given ID is not found.
  - `400 Bad Request` - Client entered a negative ID.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 3. Create a New Sales Tax
- **Endpoint:** `POST /api/sales_taxes`
- **Description:** Create a new sales tax.
- **Request Body:**
  - `SalesTaxRequest` - Details of the sales tax to create.
- **Responses:**
  - `201 Created` - Sales tax successfully created. Response contains a `SalesTaxResponse`.
  - `400 Bad Request` - Client entered an invalid entity body.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 4. Update Sales Tax
- **Endpoint:** `PUT /api/sales_taxes/{id}`
- **Description:** Update an existing sales tax.
- **Request Parameters:**
  - `id` (Path Variable) - ID of the sales tax to update.
- **Request Body:**
  - `SalesTaxRequest` - Updated details of the sales tax.
- **Responses:**
  - `200 OK` - Sales tax successfully updated. Response contains a `SalesTaxResponse`.
  - `404 Not Found` - Sales tax with the given ID is not found.
  - `400 Bad Request` - Client entered a negative ID or an invalid entity body.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 5. Delete Sales Tax By Id
- **Endpoint:** `DELETE /api/sales_taxes/{id}`
- **Description:** Delete a sales tax by its ID.
- **Request Parameters:**
  - `id` (Path Variable) - ID of the sales tax to delete.
- **Responses:**
  - `204 No Content` - Sales tax successfully deleted.
  - `404 Not Found` - Sales tax with the given ID is not found.
  - `400 Bad Request` - Client entered a negative ID.
  - `500 Internal Server Error` - Unexpected error occurred.

---

## 6. Get Tax Rate for a Specific Country
- **Endpoint:** `GET /api/sales_taxes/country/{country}`
- **Description:** Retrieve the sales tax rate for a specific country.
- **Request Parameters:**
  - `country` (Path Variable) - Country to retrieve the tax rate for.
- **Responses:**
  - `200 OK` - Sales tax rate successfully retrieved. Response contains the tax rate as a `Double`.
  - `404 Not Found` - Sales tax rate for the given country is not found.
  - `400 Bad Request` - Client entered a blank country.
  - `500 Internal Server Error` - Unexpected error occurred.

