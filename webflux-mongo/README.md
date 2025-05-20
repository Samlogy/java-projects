
# **Spring WebFlux & Non-Blocking Programming:** 

## **1️⃣ What is WebFlux?**
Spring **WebFlux** is a **reactive, non-blocking** framework designed for **asynchronous, event-driven** applications. It is an **alternative** to traditional **Spring MVC** but designed to handle **high-concurrency** workloads efficiently.

📌 **Key Characteristics**:
- Fully **reactive** using **Project Reactor (Mono & Flux)**
- Uses **non-blocking I/O** (does not block threads)
- Optimized for **high-throughput, real-time applications**
- Supports **functional programming & declarative API**

✅ **WebFlux is ideal for building scalable and reactive microservices!**

---

## **2️⃣ What is Non-Blocking Programming?**
In traditional **blocking programming**, each request **waits** for a response before continuing.  
In **non-blocking programming**, the system **does not wait** and can handle multiple tasks **concurrently**.

📌 **Ex: Blocking vs. Non-Blocking Calls**
### **🛑 Blocking Code (Spring MVC)**

💡 **Problem?** If the database is slow, the thread is **blocked**, reducing efficiency.

### **✅ Non-Blocking Code (Spring WebFlux)**

💡 **Solution?** The request is handled **asynchronously** without blocking the thread.

---

## **3️⃣ What Problem Does WebFlux Solve?**
Spring MVC uses **thread-per-request** blocking architecture, which struggles under **high concurrency**.  
WebFlux solves this by using **event-driven, non-blocking I/O**, allowing it to handle **thousands of concurrent requests** with fewer threads.

### **🛑 Problem with Blocking I/O (Spring MVC)**
- A thread is **blocked** while waiting for a database/API response.
- High load means **more threads needed**, leading to **high memory usage**.
- Scaling requires **more threads**, increasing **CPU & RAM consumption**.

### **✅ How WebFlux Solves It (Non-Blocking I/O)**
- Uses **event loop & reactive streams** instead of blocking threads.
- Can handle **many concurrent requests** with **fewer threads**.
- Great for **real-time applications & high-traffic APIs**.

---

## **4️⃣ When Should You Use WebFlux? (Use Cases)**
Spring WebFlux is **not always the best choice**. It is useful for **specific scenarios**:

| **✅ Use WebFlux When...** | **❌ Avoid WebFlux When...** |
|--------------------------|--------------------------|
| Handling **high-concurrency** workloads (1000s of requests/sec) | Simple CRUD apps with low concurrency |
| **Streaming real-time data** (SSE/WebSockets) | You need **blocking** traditional transactions (e.g., financial apps) |
| Calling **multiple external APIs asynchronously** | You're using **JDBC** (which is blocking) |
| **Microservices with WebClient** (instead of RestTemplate) | Your team is new to reactive programming |
| **IoT, Chat apps, Live dashboards** | You don't need non-blocking performance |

📌 **Best Use Cases for WebFlux**

✅ High-performance **REST APIs**  
✅ **Streaming apps** (real-time dashboards, stock markets)  
✅ **Microservices** that call multiple services (async API composition)  
✅ **IoT & WebSockets-based chat applications**

---

## **5️⃣ WebFlux vs. Spring MVC: Key Differences**
| Feature | Spring MVC (Blocking) | Spring WebFlux (Non-Blocking) |
|---------|----------------------|-----------------------------|
| **Threading Model** | One thread per request | Event-driven, fewer threads |
| **Performance** | Slower under heavy load | Handles **high concurrency** well |
| **REST API Calls** | Uses `RestTemplate` (Blocking) | Uses `WebClient` (Non-blocking) |
| **Database Access** | Uses JDBC (Blocking) | Uses R2DBC (Non-blocking) |
| **Scalability** | More threads needed | Uses **less resources** |
| **Streaming** | Not optimized | **Supports SSE, WebSockets** |

📌 **Rule of Thumb:**  
Use **Spring MVC** for simple apps, and **WebFlux** for high-performance, non-blocking apps.

---

## **6️⃣ Summary**
| **Feature** | **Explanation** |
|------------|---------------|
| **WebFlux** | Reactive, non-blocking alternative to Spring MVC |
| **Non-blocking I/O** | Handles requests **asynchronously**, increasing scalability |
| **Mono & Flux** | Core WebFlux types for reactive programming |
| **WebClient** | Replaces `RestTemplate` for async API calls |
| **Backpressure Handling** | Prevents system overload in high-concurrency situations |
| **Best for** | **Microservices, real-time apps, high-performance APIs** |

## Spring WebFlux

| 🌟 **Feature** | 📌 **Description** |
|--------------|----------------|
| **Fully Non-Blocking** | Uses **Reactor (Mono & Flux)** to process requests asynchronously. |
| **Backpressure Handling** | Manages **data flow control** to prevent overwhelming the system. |
| **WebClient (Non-blocking HTTP Client)** | Replaces `RestTemplate` for making reactive API calls. |
| **Functional Endpoints** | Supports an **alternative way** to define routes using `RouterFunction`. |
| **Reactive Streams Support** | Uses **Project Reactor** to implement **Publisher-Subscriber patterns**. |
| **Optimized for Microservices** | Works well with **Kafka, RabbitMQ, RSocket** for async messaging. |
| **Reactive Security** | Uses `SecurityWebFilterChain` instead of traditional filters. |
| **Integration with R2DBC (Reactive DB Access)** | Works with **MongoDB, PostgreSQL (R2DBC), Redis** reactively. |

---

## **🔑 Essential Concepts You Must Master in WebFlux**
Since you're **new to WebFlux**, focus on these **core concepts** first:

### **1️⃣ Reactive Programming (Project Reactor)**
WebFlux is built on **Project Reactor**, which provides two main reactive types:  
✅ `Mono<T>` → Handles **a single** asynchronous result.  
✅ `Flux<T>` → Handles **multiple** results (like a stream).

🎯 **Key Topics**:
- **Backpressure** (Controlling data flow)
- **Schedulers** (Parallel execution in WebFlux)
- **Threading Model** (How WebFlux uses the event loop)

---

### **2️⃣ Building REST APIs with WebFlux**
Unlike Spring MVC, WebFlux returns **Mono** and **Flux** instead of objects.

🎯 **Key Topics**:
- Using `Mono` and `Flux` in **Controllers & Services**
- **Error Handling** (Return proper HTTP errors in reactive APIs)

---

### **3️⃣ WebClient - Making Non-Blocking API Calls**
Instead of `RestTemplate`, use **WebClient** for calling external APIs asynchronously.


🎯 **Key Topics**:
- **Handling errors** in WebClient (`onStatus()`, `exchangeToMono()`)
- **Using WebClient with authentication**

---

### **4️⃣ Exception & Error Handling in WebFlux**
Handling errors is different in WebFlux compared to traditional Spring MVC.

🎯 **Key Topics**:
- **Global Exception Handling** using `@ControllerAdvice`
- **switchIfEmpty()** vs **onErrorResume()** for error recovery

---

### **5️⃣ Functional Endpoints - Alternative to Controllers**
Instead of `@RestController`, WebFlux allows **functional routing**.

🎯 **Key Topics**:
- When to use **functional endpoints** vs **annotated controllers**
- How to test functional endpoints

---

### **6️⃣ Reactive Databases - MongoDB & R2DBC**
In WebFlux, you need **reactive database drivers** for **non-blocking data access**.

🎯 **Key Topics**:
- **R2DBC vs JDBC** (Blocking vs Non-blocking)
- Using **Redis** with WebFlux (Reactive caching)

---

### **7️⃣ Security in WebFlux (JWT + Spring Security)**
Since WebFlux **doesn't use traditional security filters**, you need `SecurityWebFilterChain`.

🎯 **Key Topics**:
- **SecurityWebFilterChain** instead of `WebSecurityConfigurerAdapter`
- **JWT authentication** in WebFlux

---

### **8️⃣ Real-time Streaming with WebFlux (SSE/WebSockets)**
Since WebFlux supports **real-time data streaming**, you can use **Server-Sent Events (SSE)** or **WebSockets**.

🎯 **Key Topics**:
- **WebSockets vs SSE** in WebFlux
- **Kafka/RabbitMQ streaming with WebFlux**  





**Test WebFlux API:**
```sh
curl -H 'Content-Type: application/json' \
      -d '{ "name": "product name 1","price": 20.85, "id": 1}' \
      -X POST \
      http://localhost:8080/products
curl -H 'Content-Type: application/json' \
      -d '{ "name": "product name 2","price": 20.85, "id": 2}' \
      -X POST \
      http://localhost:8080/products
curl -H 'Content-Type: application/json' \
      -d '{ "name": "product name 3","price": 20.85, "id": 3}' \
      -X POST \
      http://localhost:8080/products
      
curl -X GET http://localhost:8080/products/1

curl -X GET http://localhost:8080/products

curl -X "DELETE" 'http://localhost:8080/products/2'
```



**Test WebClient API:**
```sh
curl -X GET http://localhost:8080/api/posts

curl -X GET http://localhost:8080/api/posts/1

curl -X POST http://localhost:8080/api/posts \
     -H "Content-Type: application/json" \
     -d '{
           "userId": 1,
           "id": 1,
           "title": "Nouveau Post",
           "body": "Contenu du post"
         }'

curl -X PUT http://localhost:8080/api/posts/1 \
     -H "Content-Type: application/json" \
     -d '{
           "userId": 1,
           "id": 1,
           "title": "Post modifié",
           "body": "Contenu mis à jour"
         }'

curl -X DELETE http://localhost:8080/api/posts/1
```



**Tests Auth (JWT/Role-Based):**
```bash
# register
curl -X POST http://localhost:8080/auth/register \
     -H "Content-Type: application/json" \
     -d '{"id": 2, "username": "john", "password": "secret", "role": "ADMIN"}'

curl -X POST http://localhost:8080/auth/register \
     -H "Content-Type: application/json" \
     -d '{"id": 1, "username": "jane", "password": "secret", "role": "MODERATOR"}'
     
# Login
curl -X POST "http://localhost:8080/auth/login?username=john&password=secret"  
curl -X POST "http://localhost:8080/auth/login?username=jane&password=secret"

# check protected routes
curl -H "Authorization: Bearer token" -X GET "http://localhost:8080/auth/admin/secure"
curl -H "Authorization: Bearer token" -X GET "http://localhost:8080/auth/moderator/secure"
```



