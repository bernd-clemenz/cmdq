# Sample Spring-Web-Application running on Java 11

## Abstract
A template or example for a Spring-Webapplication with all modules
compatible with Java 11.

## Deprecated and removed @PostConstruct and @PreDestroy
Therefore added the dependency:
```xml
<dependency>
    <groupId>javax.annotation</groupId>
    <artifactId>javax.annotation-api</artifactId>
    <version>1.3.2</version>
</dependency>
```
## Modules
[Spring](https://www.spring.io)
[Lombock](https://projectlombock.org)