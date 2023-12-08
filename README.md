# Digital Library Management System

## Setup
Step-1:<br>
Add [application.properties](src/main/resources/application.properties)
 ```properties
## Spring Data Source
spring.datasource.url=jdbc:mysql://localhost:3306/dbname
spring.datasource.username=root
spring.datasource.password=root

## Hibernate
## create-drop -> remove previous data, update -> update with previous data
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

## JSON
spring.jackson.date-format=yyyy-MM-dd

## Redis
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379
logging.level.org.springframework.cache=DEBUG

## Kafka
logging.level.org.springframework.kafka.listener=ERROR
logging.level.org.apache.kafka.clients.NetworkClient=ERROR
logging.level.org.apache.kafka.clients.consumer.internals.ConsumerCoordinator=ERROR
logging.level.org.apache.kafka.clients.consumer.ConsumerConfig=ERROR
```
Step-2:<br>
Run [SpringBootLibraryApplication.java](src/main/java/com/sclab/library/SpringBootLibraryApplication.java) to start app.

## Resources
 - [Spring Docs](https://spring.io/guides)
 - [JPA query-methods-details](https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html)
 - [JPA query-methods](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html)
