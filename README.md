# tracking-number-generator
RESTful API that generates unique tracking numbers.

---

### Available functionality
* Generate a unique tracking number.

#### Pre-requisites
* Java Development Kit (JDK) 21
* Apache Maven 3.9.6
* PostgreSQL 17

### Environment used for developments
* OS: Windows 11
* IDE: IntelliJ IDEA 2024.3.5
* Back end technologies: Java with Spring Boot 3

### Config & Build
1. Goto the location of the project directory.

2. Run following command.

`mvn clean install`

3. Execute the `db.sql` inside the `src/main/resources` directory using PostgreSQL client to create the required database and tables.
4. Configure the database connection in the `application.properties` file located inside the `src/main/resources` directory.


### Execute the program
1. Goto the project built directory.

`<project-directory>/target`

2. Execute the jar file as follows.

`java -jar library<project-version>.jar`

e.g: `java -jar api-0.0.1-SNAPSHOT.jar`

i.e: Please refer the following link to find more ways to build and execute Spring Boot application.

https://spring.io/guides/gs/spring-boot

### Access the program
By default, program will start on port 8080. You may access the API using the following cURL.

```
curl --location 'http://localhost:8080/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=15.234&created_at=2018-11-20T19%3A29%3A32%2B08%3A00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics' \
--header 'Cookie: JSESSIONID=3B5C571430F497C7A33F8F76943D0DAB'
```

Please refer to the following link to find the API documentation.
https://app.swaggerhub.com/apis-docs/kasun_test/Tracking_Number_Generator/1.0.0

### JMeter test video
Tested using Apache JMeter by sending 1000 requests with 10 seconds.
**https://1drv.ms/v/c/b4cceae27e0e7904/EYbvsDVfxaZLn6hr3jx5u6oBV8hW4tV10vRbHJocW2TA-g?e=kDj6u4**