# Dispatch Load Balancer – Spring Boot Backend

## Overview

This project implements a **Dispatch Load Balancer** that assigns delivery orders to a fleet of vehicles based on:

* **Vehicle capacity constraints**
* **Order priority (HIGH → MEDIUM → LOW)**
* **Minimizing travel distance using the Haversine formula**

The system exposes **REST APIs** that allow clients to:

1. Submit delivery orders
2. Register vehicle fleet details
3. Retrieve an optimized dispatch plan

The application is designed with **modular architecture**, **clear separation of concerns**, and is **testable using Postman or Swagger**.

---

# Architecture

The application follows a **layered architecture**:

```
Controller Layer
    ↓
Service Layer
    ↓
Optimization Service
    ↓
Utility Layer (Haversine Distance)
```

### Package Structure

```
com.dispatch.balancer
│
├── controller
│     DispatchController.java
│
├── service
│     DispatchService.java
│     OptimizationService.java
│
├── model
│     Order.java
│     Vehicle.java
│     DispatchPlan.java
│     Priority.java
│
├── dto
│     OrderRequest.java
│     VehicleRequest.java
│     ApiResponse.java
│
├── util
│     HaversineUtil.java
│
├── exception
│     GlobalExceptionHandler.java
│
└── DispatchBalancerApplication.java
```

### Responsibilities

| Layer               | Responsibility                   |
| ------------------- | -------------------------------- |
| Controller          | Handles REST API requests        |
| Service             | Business logic and orchestration |
| OptimizationService | Dispatch assignment algorithm    |
| Util                | Distance calculation             |
| DTO                 | Request/Response payloads        |

---

# Dispatch Optimization Algorithm

The dispatch logic follows a **greedy optimization approach**:

1. Orders are **sorted by priority**
2. For each order:

    * Find the **nearest vehicle**
    * Ensure **vehicle capacity is not exceeded**
3. Assign the order to the selected vehicle
4. Update vehicle load and location
5. Accumulate **total travel distance**

Distance between coordinates is calculated using the **Haversine formula**.

### Algorithm Complexity

```
O(n × m)

n = number of orders
m = number of vehicles
```

This approach performs efficiently for typical fleet dispatch scenarios.

---

# Distance Calculation

The application uses the **Haversine formula** to calculate the great-circle distance between two geographical coordinates.

Example:

```
distance = Haversine(vehicleLatitude, vehicleLongitude,
                     orderLatitude, orderLongitude)
```

Distances are returned in **kilometers (km)**.

---

# REST API Endpoints

## 1. Add Delivery Orders

**POST**

```
/api/dispatch/orders
```

### Request

```json
{
  "orders": [
    {
      "orderId": "ORD001",
      "latitude": 12.9716,
      "longitude": 77.5946,
      "address": "MG Road Bangalore",
      "packageWeight": 10,
      "priority": "HIGH"
    }
  ]
}
```

### Response

```json
{
  "message": "Delivery orders accepted",
  "status": "success"
}
```

---

# 2. Add Fleet Vehicles

**POST**

```
/api/dispatch/vehicles
```

### Request

```json
{
  "vehicles": [
    {
      "vehicleId": "V001",
      "capacity": 50,
      "currentLatitude": 12.97,
      "currentLongitude": 77.59,
      "currentAddress": "Indiranagar Bangalore"
    }
  ]
}
```

### Response

```json
{
  "message": "Vehicle details accepted",
  "status": "success"
}
```

---

# 3. Retrieve Dispatch Plan

**GET**

```
/api/dispatch/plan
```

### Response Example

```json
[
  {
    "vehicleId": "V001",
    "totalLoad": 10,
    "totalDistance": 0.53,
    "totalDistanceWithUnit": "0.53 km",
    "assignedOrders": [
      {
        "orderId": "ORD001",
        "latitude": 12.9716,
        "longitude": 77.5946,
        "address": "MG Road Bangalore",
        "packageWeight": 10,
        "priority": "HIGH"
      }
    ]
  }
]
```

---

# Error Handling

The application implements **global exception handling** to ensure consistent error responses.

Handled scenarios include:

* Invalid request payloads
* Capacity constraint violations
* Missing required fields
* Unexpected runtime errors

Example error response:

```json
{
  "message": "Invalid request payload",
  "status": "error"
}
```

---

# Testing

The application includes **JUnit test cases** covering:

* Distance calculation
* Order assignment logic
* Capacity constraint validation
* Priority ordering

Example test classes:

```
src/test/java/com/dispatch/balancer

HaversineUtilTest.java
OptimizationServiceTest.java
DispatchControllerTest.java
```

Tests validate key dispatch scenarios and edge cases.

---

# Running the Project

### Requirements

```
Java 17+
Maven 3+
```

### Clone Repository

```
git clone https://github.com/your-repo/dispatch-balancer.git
cd dispatch-balancer
```

### Build Project

```
mvn clean install
```

### Run Application

```
mvn spring-boot:run
```

Application starts at:

```
http://localhost:8080
```

---

# API Testing

You can test the APIs using:

* **Postman**
* **Swagger UI**

Swagger documentation:

```
http://localhost:8080/swagger-ui.html
```

---

# Data Storage

For simplicity and ease of testing, the current implementation uses **in-memory data structures** to store orders and vehicle data.

In a production environment, this can be extended with:

* **Spring Data JPA**
* **PostgreSQL / MySQL**
* Repository-based persistence
* Upsert operations for order updates
* Historical dispatch tracking

---

# Performance Considerations

The system is optimized for:

* Efficient priority sorting
* Greedy nearest-vehicle assignment
* Lightweight in-memory operations

This allows the application to handle **large batches of orders with minimal latency**.

---

# Technologies Used

* Java 17
* Spring Boot
* Maven
* Lombok
* JUnit 5

---

# Future Improvements

Potential enhancements include:

* Database persistence layer
* Vehicle route optimization
* Order clustering
* Redis caching
* Real-time dispatch monitoring
* Support for multiple dispatch regions

---

## Author

**KESABA-BARIK**

Backend Developer – Assignment Submission

* GitHub: https://github.com/KESABA-BARIK
* Email: [kesababarik007@gmail.com](mailto:kesababarik007@gmail.com)


This project implements a dispatch load balancer that optimizes delivery order allocation to vehicles using priority-based assignment and Haversine distance calculations.
