# Building a Real-Time Chat Application with Spring Boot, WebSocket, Cassandra, Redis and RabbitMQ

![eBook Chat Application](/images/ebook-chat-application.png)

## Check my eBook for a step-by-step code guide for the `ebook-chat` project:
- https://leanpub.com/building-a-real-time-chat-application-with-spring-boot-websocket-cassandra-redis-and-rabbitmq

**There is a sample book version available for download on the above link as well. It has 80 pages and brings many interesting topics like Introduction to NoSQL, Cassandra, Redis, Spring Boot, Spring Data and Introduction to WebSockets and STOMP protocol**.

# Technologies used in this project

- Spring Boot
- Spring Data (JPA / Cassandra / Redis)
- Spring Security
- Spring WebSocket
- Spring Session
- Cassandra
- Redis
- RabbitMQ
- MySQL
- JUnit, Mockito and TestContainers (spin up Docker containers for Integration Tests)
- Thymeleaf, JQuery and Bootstrap
- Apache Maven (Surefire and Failsafe plugins)

# Setting up this project locally

> **Note:**
The fastest way to get this application up and running locally is using **Docker** and **Docker Compose**.  Be sure that you have at least **Docker 1.13.0** and **Docker Compose 1.11.2** installed on your machine.

1. Clone this repository
```shell
$ git clone git@github.com:jorgeacetozi/ebook-chat-app-spring-websocket-cassandra-redis-rabbitmq.git
```
2. Setup the dependencies (Cassandra, Redis, MySQL and RabbitMQ with STOMP support)

```shell
$ docker-compose -f docker-compose/dependencies.yml up
```

3. Start the application

```shell
$ wget https://github.com/jorgeacetozi/ebook-chat-app-spring-websocket-cassandra-redis/releases/download/ebook-chat-1.0.0/ebook-chat-1.0.0.jar && java -jar ebook-chat-1.0.0.jar
```

# Basic Usage

1. Sign in with username **admin** and password **admin**
2. Create a **New Chat Room** and logout
3. Create your private account
4. Sign in with your account credentials
5. Join the chat room
6. Open a new incognito window and create another account
7. Sign in with this another account
8. Join the chat room
9. Send some messages
10. Open the other browser window and see the messages coming
11. Click the username to send private messages
