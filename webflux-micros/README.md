# Spring Boot Microservices Architecture with WebFlux

This project is a small example of a microservices architecture built with Spring Boot. It aims to demonstrate the basics of microservices, Eureka for service discovery, and inter-service communication using WebClient. The project includes the following components:

### Architecture

![microservices-architecture.png](microservices-architecture.png)

### Services

1. **API Gateway**: This service acts as a gateway for routing requests to the appropriate microservices. It is an Eureka client.

2. **Rent Service**: Manages the renting process, including creating and retrieving rental information. It is also an Eureka client.

3. **Service Registry**: A Eureka server that registers all microservices and provides service discovery.

4. **User Service**: Manages user information and operations. It is an Eureka client.

5. **Vehicle Service**: Manages vehicle information and operations. It is an Eureka client.

### Database Usage

- **user-service**: Manages user information using PostgreSQL.
- **vehicle-service**: Manages vehicle information using PostgreSQL.
- **rent-service**: Manages rental operations using PostgreSQL.

Each service in this project utilizes its own PostgreSQL database for data storage and management.


### Communication between Services

The microservices communicate with each other using WebClient. WebClient is a non-blocking, reactive client used for making HTTP requests. In this project, it is used to enable efficient and asynchronous communication between the services.

### Running the Project

Each service has its own `docker-compose.yaml` file for containerization. Additionally, there is a root-level `docker-compose.yaml` file for Zipkin, which is used for distributed tracing.

To run the project:

1. **Start the Service Registry**:
   ```bash
   cd service-registry
   docker-compose up -d

2. **Start the Microservices**:
   ```bash
   cd user-service
   docker-compose up -d

   cd vehicle-service
   docker-compose up -d
   
   cd rent-service
   docker-compose up -d
   
   cd api-gateway
   docker-compose up -d

### Features
* Service Discovery: Using Netflix Eureka for dynamic service registration and discovery.
* API Gateway: Centralized access point to the microservices.
* WebClient: Efficient inter-service communication.
* Docker Compose: Containerization of each microservice and Zipkin for easy deployment.
* Distributed Tracing: Using Zipkin to trace requests across microservices.

---

**Resources:**
https://github.com/tamani-coding/spring-boot-webflux-r2dbc-postgrest-example/tree/main
https://github.com/thombergs/code-examples/tree/master
https://github.com/YunusEmreNalbant/sample-spring-microservices-with-webflux/tree/main
https://github.com/mostafacs/ecommerce-microservices-spring-reactive-webflux/tree/master

# kafka
https://github.com/mokaddemhicham/Apache-Kafka-with-Spring-Boot-Reactive-and-WebFlux/blob/main/kafka-producer/pom.xml

https://github.com/sergiosquevedo/webflux_kafka
https://utronics.hashnode.dev/spring-webflux-reactive-kafka-cassandra-complete-reactive-spring-apps


**microservices spring webflux:**

# Eureka
http://localhost:8761/

# Rent service

http://localhost:8073/api/rents
http://localhost:8073/api/users
http://localhost:8073/api/vehicles
http://localhost:8073/api/rents



postgres:16-alpine


flyway:
enabled: 'true'
url: jdbc:postgresql://localhost:5434/vehicle_service
user: root
password: 123123123

r2dbc:
url: r2dbc:postgresql://localhost:5434/vehicle_service
password: 123123123
username: roota
