# Test task for Java Autotests

Simple Rest API tests.

Run tests with:
```
mvn clean test -DsuiteXMLFile=src/test/resources/testng.xml
```
 
Generate report with:
 ```
mvn site io.qameta.allure:allure-maven:serve
 ```