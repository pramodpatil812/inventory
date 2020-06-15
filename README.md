# Inventory and Ordering system

#### Prerequisites

Install ```git, maven, java(>=8) and mysql-server and postman```.

#### Steps to run
1. Clone the project by running ```git clone git@github.com:pramodpatil812/inventory.git <dir>```.
2. Change mysql credentials into **resources/application.properties** file.
3. Import database dump present at **/dump/sayurbox.sql**.
4. Install the dependencies by running ```mvn clean install```.
5. Run spring boot application by running ```mvn spring-boot:run```
6. Import the postman collection present in **/dump/** folder and test the api's.

#### Running application from commandline
```mvn spring-boot:run```

#### Running only unit test cases
```mvn test```

#### Assumptions
For simplicity each request is passed user id in the header. For production based system signed jwt token will be useful in user validation.
