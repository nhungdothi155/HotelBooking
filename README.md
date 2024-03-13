# MVP Hotel Booking


## Table of contents
* [General info](#general-info)
* [Requirments](#Requirements)
* [Technologies](#technologies)
* [Setup](#setup)
* [API](#API)
* [Monitoring](#monitoring)
* [Notes](#notes)


## General info
Create a new Hotel Booking integration for a new API partner who serves 3 million booking requests per hour. As with any other reservation system, our users will need to be able to Search, Create, Update, View and Cancel their hotel bookings. Assume all users have the same role and permission levels.
## Requirements


* All communication with the backend should be via REST API endpoints with JSON payloads in the response 
* User/hotel data will need to be persisted to fulfill the requirement (Up to the developer to decide: in-memory or RDBMS or NoSQL)
* Code should be appropriately documented
* At least a basic set of tests for the happy path scenarios
* If using RDBMS, it would be nice to have a script to generate additional test data Application should be instrumented: Custom metrics, logging, tracing, and monitoring
* To start the application, database, and so on, a docker-compose.yml file would be useful


## Technologies
* Java 11
* Spring Boot 2.7.11
* Maven 3.8.1
* MySQL 8
* Spring Data JPA
* Hibernate search 6
* Spring sleuth
* Logback


## Setup
**Prerequisites:**
> Enviroment
* Java 11
* Maven
* Docker
> Step
* Note remember to stop your mysql port 3360 before run the docker file
* Download project , go the the root directory type :
  ~~~
  mvn clean install -DskipTests
  ~~~
* then run the docker-compose.yml by docker compose
  ~~~
  docker-compose up
  ~~~

## API 
- access to swagger link to see all the active API : http://localhost:8080/v1/swagger-ui-custom.html
  ![image](https://github.com/nhungdothi155/HotelBooking/assets/77849669/d979dee0-8d91-40ae-90c0-962b91c97d7b)

## Metrics, Logging, Tracing, Monitoring (Grafana, Loki, Promotheses,Jaeger )
* **Grafana** : access to that port then login by default password username ( admin, admin )
  `http://localhost:3000/`
  ![image](https://github.com/nhungdothi155/HotelBooking/assets/77849669/cb0b09db-31d4-416f-a94d-cf5c82b85dc2)
* **Loki** Go to **Configuration** > **Data Source** > create new data source **Loki** : input that port
  `http://loki:3100`
![image](https://github.com/nhungdothi155/HotelBooking/assets/77849669/08f49d29-4986-4e49-b530-7433094cb289)
![image](https://github.com/nhungdothi155/HotelBooking/assets/77849669/046e2c47-bf28-4d46-9056-b2496215e5be)


*  **Prometheus** Go to **Configuration** > **Data Source** > create new data source **Prometheus** : input that port
`http://prometheus:9090`
![image](https://github.com/nhungdothi155/HotelBooking/assets/77849669/0b8a5322-8288-4b68-a592-4da3a9a6b4ec)
![image](https://github.com/nhungdothi155/HotelBooking/assets/77849669/c4362421-14d9-4942-a8be-6aebb45ad053)

*  **Jaeger** Go to **Configuration** > **Data Source** > create new data source **Jaeger** : input that port
`http://jaeger-service:16686`
![image](https://github.com/nhungdothi155/HotelBooking/assets/77849669/3b25faef-f176-4657-b664-9c3afced27da)
![image](https://github.com/nhungdothi155/HotelBooking/assets/77849669/611620e2-d44f-4ea7-bb1a-3859e0a71222)

