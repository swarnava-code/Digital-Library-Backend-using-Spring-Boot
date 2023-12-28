# Digital Library Management System



## Setup
Step-1:<br>
Add [application.properties](src/main/resources/application.properties)
 ```properties
## App Info
spring.application.name=digital-library-service

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

## Transaction
transact.max_issue_allowed=2
transact.total_borrow_days=10

# Gmail SMTP Example
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=s...codejr@gmail.com
spring.mail.password=xxxx xxxx xxxx xxxx
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# BCC recipients to keep track as ADMIN
email.bcc=admin.tracking@gmail.com
```
Step-2:<br>
Run [SpringBootLibraryApplication.java](src/main/java/com/sclab/library/SpringBootLibraryApplication.java) to start app.


### Important Note:

Before starting the Spring Boot application, ensure that the following components are active and configured properly:

1. **Database Configuration:**
    - Make sure your database server is running.
    - Check the application's `application.properties` or `application.yml` file for the correct database connection details.

2. **External Services:**
   Verify the availability and proper configuration of the external services the application depends on 
    - message brokers: Kafka
    - Caching: Redis

3. **Logging and Monitoring:**
    - Set up logging and monitoring tools as needed for tracking application behavior and performance.


## ER Diagram

```mermaid
---
title: Entity-Relationship Diagram
---
erDiagram
    BOOK ||--|{ AUTHOR_BOOK : writtenByAuthorTrack
    AUTHOR ||--|{ AUTHOR_BOOK : isWroteBookTrack
    
    STUDENT ||..|| CARD  : uses_active
    
    CARD ||..|{ TRANSACTION : issueOrReturn
    BOOK ||..|| TRANSACTION : issueOrReturn
    
    BOOK {
        String id PK "uuid"
        String name
        int number_of_pages "at-least 1 page"
        String language
        boolean available "true, false"
        enum genre "[FICTIONAL,
            NON_FICTIONAL,
            GEOGRAPHY,
            HISTORY,
            POLITICAL_SCIENCE,
            BOTANY,
            CHEMISTRY,
            MATHEMATICS,
            PHYSICS]"
        String isbn_number "13 digit"
        String published_date "timestamp"
    }
    AUTHOR {
        String id PK "uuid"
        int age
        String countery
        String email
        String name
    }
    AUTHOR_BOOK {
        String id PK "uuid"
        String author_id FK "author(id), uuid"
        String book_id FK "book(id), uuid"
    }
    CARD {
        String id PK "uuid"
        date created_on
        String email
        enum status "[ACTIVE, EXPIRED]"
        int total_issued_book
        date updated_on
        date valid_upto
    }
    STUDENT {
        String id PK "uuid"
        int age
        date created_on
        String email
        String name
        String phone_number
        date update_on
        String card_id FK "card(id), uuid"
    }
    TRANSACTION {
        String id PK "uuid"
        date book_due_date
        date created_on
        double fine_amount
        boolean is_issued
        boolean is_returned
        enum status "[ACTIVE,EXPIRED]"
        date transaction_date
        date updated_on
        String book_id FK "book(id), uuid"
        String card_id FK "card(id), uuid"
    }
```

## Architecture Diagram



```mermaid
---
title: Architecture Diagram
---
flowchart LR

    subgraph externalClientApp[External Client Apps]
        PostMan[["PostMan"]]:::externalSystem
        Client[["Client"]]:::externalSystem
        WebApp[["Web App"]]:::externalSystem
        MobileApp[["Mobile App"]]:::externalSystem
    end

    subgraph controller
        PostMan--interact-->TestController
        Client--interact-->AuthorController
        Client--interact-->BookController
        Client--interact-->AssignAuthorBookController
        MobileApp--interact-->TransactionController
        MobileApp--interact-->StudentController
        MobileApp--interact-->CardController
        WebApp--interact-->ReportController
   end

    subgraph apiService["API service"]
        AuthorController--interact-->AuthorService
        BookController--interact-->BookService
        AssignAuthorBookController--interact-->AssignAuthorBookService
        StudentController--interact-->StudentService
        CardController--interact-->CardService
        TransactionController--interact-->TransactionService
        ReportController--interact-->TransactionService
        ReportController--interact-->StudentService
    end
    
    subgraph repository
        AuthorService--interact-->AuthorRepository
        BookService--interact-->BookRepository
        AssignAuthorBookService--interact-->AuthorBookRepository
        StudentService--interact-->StudentRepository
        CardService--interact-->CardRepository
        TransactionService--interact-->TransactionRepository
    end

    subgraph entity["database"]
        AuthorRepository--insert-->Author
        BookRepository--insert-->Book
        AuthorBookRepository--insert-->AuthorBook
        StudentRepository--insert-->Student
        CardRepository--insert-->Card
        TransactionRepository--insert-->Transaction
        User
    end

    subgraph kafkaService["Kafka-Mail-Notification sub service"]
        KafkaListenerService--sendSimpleEmail-->EmailService
        CardService--getById-->KafkaListenerService
        KafkaProducerService
        Kafka(("Kafka MQ"))--consume notification data-->KafkaListenerService
        TransactionService--sendBookIssuedNotification-->KafkaProducerService
        KafkaProducerService--send notification-->Kafka
    end
    kafkaService:::internalSystem
    
    EmailService--Sends notification emails to-->StudentGmail(("Student Gmail"))
    StudentGmail:::person
    

%% Element type definitions
    classDef person fill:#08427b, color:#fff
    classDef internalSystem fill:#1168bd
    classDef externalSystem fill:#4040bd, color:#fff
```

## Grafana dashboard
- Run Docker to host prometheus and grafana
- Open [Grafana Dashboard](http://localhost:3000/dashboards)
- Check existing template [here](http://localhost:3000/dashboards)
- If template not visible or want new one, import template
   - Click `+` and select `Import Dashboard` or visit [import page](http://localhost:3000/dashboard/import)
   - Put template code(`11378`) under `Import via grafana.com`. Template details: [11378](https://grafana.com/grafana/dashboards/11378-justai-system-monitor/)
   - Click Import


## References
 - [Spring Docs](https://spring.io/guides)
 - [JPA query-methods-details](https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html)
 - [JPA query-methods](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html)
 - [Unit Test Sample Code](https://github.com/in28minutes/spring-unit-testing-with-junit-and-mockito)
 - [Monitor setup](https://medium.com/simform-engineering/revolutionize-monitoring-empowering-spring-boot-applications-with-prometheus-and-grafana-e99c5c7248cf)
 - [Exception Priority Set](https://stackoverflow.com/questions/40334360/how-to-set-priority-in-exceptionhandling-via-controlleradvice)
 - [mermaid flowchart](https://lukemerrett.com/building-c4-diagrams-in-mermaid/)
 - [mermaid flowchart official](https://mermaid.js.org/syntax/flowchart.html)

