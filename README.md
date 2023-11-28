Add [application.properties](src/main/resources/application.properties)
 ```shell
## Spring Data Source
spring.datasource.url=jdbc:mysql://localhost:3306/jbdl60
spring.datasource.username=root
spring.datasource.password=

## Hibernate
## create-drop -> remove previous data, update -> update with previous data
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```