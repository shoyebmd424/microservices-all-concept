 **YAML-based configuration guide** for key topics in **Microservices with Spring Boot 3**. Each section includes the necessary YAML configurations for setting up and managing microservices.

---

## **1. Basic Spring Boot Application Configuration**
```yaml
# application.yml
server:
  port: 8081  # Port for the microservice
spring:
  application:
    name: user-service  # Service name
  datasource:
    url: jdbc:mysql://localhost:3306/userdb  # Database URL
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update  # Automatically update the schema
    show-sql: true  # Show SQL queries in logs
```

---

## **2. Service Discovery with Eureka**
```yaml
# application.yml
server:
  port: 8081
spring:
  application:
    name: user-service
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/  # Eureka server URL
    register-with-eureka: true  # Register this service with Eureka
    fetch-registry: true  # Fetch the registry from Eureka
```

---

## **3. API Gateway Configuration**
```yaml
# application.yml (for Spring Cloud Gateway)
server:
  port: 8080
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: http://localhost:8081  # Route to user-service
          predicates:
            - Path=/users/**  # Match paths starting with /users
        - id: order-service
          uri: http://localhost:8082  # Route to order-service
          predicates:
            - Path=/orders/**  # Match paths starting with /orders
```

---

## **4. Distributed Configuration with Spring Cloud Config**
```yaml
# bootstrap.yml (for the client microservice)
spring:
  cloud:
    config:
      uri: http://localhost:8888  # Config server URL
      fail-fast: true  # Fail fast if config server is unavailable
```

---

## **5. Resilience4j Configuration**
```yaml
# application.yml
resilience4j:
  circuitbreaker:
    instances:
      userService:
        failure-rate-threshold: 50  # Failure rate threshold in percentage
        wait-duration-in-open-state: 5000  # Wait duration in milliseconds
        sliding-window-size: 10  # Sliding window size
  retry:
    instances:
      userService:
        max-attempts: 3  # Maximum retry attempts
        wait-duration: 1000  # Wait duration between retries
```

---

## **6. Distributed Tracing with Micrometer and Zipkin**
```yaml
# application.yml
management:
  tracing:
    enabled: true  # Enable distributed tracing
    sampling:
      probability: 1.0  # Sample 100% of requests
spring:
  zipkin:
    base-url: http://localhost:9411  # Zipkin server URL
  sleuth:
    sampler:
      probability: 1.0  # Sample 100% of requests
```

---

## **7. Security Configuration (OAuth2 with JWT)**
```yaml
# application.yml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:9000  # OAuth2 issuer URI
          jwk-set-uri: http://localhost:9000/.well-known/jwks.json  # JWK Set URI
```

---

## **8. Messaging with Kafka**
```yaml
# application.yml
spring:
  kafka:
    bootstrap-servers: localhost:9092  # Kafka broker address
    consumer:
      group-id: user-group  # Consumer group ID
      auto-offset-reset: earliest  # Start reading from the earliest offset
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

---

## **9. Monitoring with Prometheus**
```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: "*"  # Expose all actuator endpoints
  metrics:
    tags:
      application: ${spring.application.name}  # Add application name as a metric tag
  prometheus:
    enabled: true  # Enable Prometheus metrics
```

---

## **10. Logging with Logback**
```yaml
# application.yml
logging:
  level:
    root: INFO  # Set root logging level
    org.springframework.web: DEBUG  # Set logging level for Spring Web
  file:
    name: logs/user-service.log  # Log file location
  pattern:
    file: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"  # Log file pattern
```

---

## **11. Docker Configuration**
```yaml
# docker-compose.yml (for running multiple microservices)
version: '3.8'
services:
  user-service:
    image: user-service:latest
    ports:
      - "8081:8081"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
  order-service:
    image: order-service:latest
    ports:
      - "8082:8082"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
  eureka-server:
    image: eureka-server:latest
    ports:
      - "8761:8761"
```

---

## **12. Kubernetes Deployment**
```yaml
# deployment.yaml (for user-service)
apiVersion: apps/v1
kind: Deployment
metadata:
  name: user-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: user-service
  template:
    metadata:
      labels:
        app: user-service
    spec:
      containers:
        - name: user-service
          image: user-service:latest
          ports:
            - containerPort: 8081
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: "prod"
---
# service.yaml (for user-service)
apiVersion: v1
kind: Service
metadata:
  name: user-service
spec:
  selector:
    app: user-service
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8081
```

---

## **13. Profiles for Environment-Specific Configuration**
```yaml
# application-dev.yml (for development)
spring:
  datasource:
    url: jdbc:h2:mem:testdb  # Use in-memory H2 database
    username: sa
    password: password
    driver-class-name: org.h2.Driver
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
```

```yaml
# application-prod.yml (for production)
spring:
  datasource:
    url: jdbc:mysql://prod-db:3306/userdb
    username: prod-user
    password: prod-password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: validate  # Validate schema, do not update
```

---

## **14. Caching with Spring Cache**
```yaml
# application.yml
spring:
  cache:
    type: caffeine  # Use Caffeine as the cache provider
    cache-names: users  # Define cache names
    caffeine:
      spec: maximumSize=500,expireAfterAccess=600s  # Cache configuration
```

---

## **15. Health Checks with Actuator**
```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics  # Expose specific actuator endpoints
  health:
    enabled: true
    diskspace:
      enabled: true
      path: /tmp
      threshold: 10MB
```

---
