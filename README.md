# Sample Spring-Web-Application running on Java 11

## Abstract
A template or example for a Spring-Webapplication with all modules
compatible with Java 11. The project itself has no further purpose
as proving the compatibility of the selected modules.

## Deprecated and removed from standard libraries: @PostConstruct and @PreDestroy
Therefore added the dependency:
```xml
<dependency>
    <groupId>javax.annotation</groupId>
    <artifactId>javax.annotation-api</artifactId>
    <version>1.3.2</version>
</dependency>
```

The same fate hit **jaxb**. Deprecated in Java 9 and removed in Java 11
```xml
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.4.0-b180725.0427</version>
</dependency>
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>2.4.0-b180725.0644</version>
</dependency>
```
## Notes
To view the code correctly in your IDE install the 'Lombock'-plugin. Such
plugins exist AFAIK for Eclipse and IntelliJ.

Still trouble with Aspectj 1.9.1. Waiting for 1.9.2 to be compatible with 
Java 11. 

## Modules
* [Spring-Framework](https://www.spring.io)
* [Lombock](https://projectlombock.org)
* [log4j2](https://logging.apache.org/log4j/2.x/)

## Other Sources
* [Log4j2 tutorial](https://howtodoinjava.com/log4j2/log4j2-properties-example/)
* [migrate-maven-projects-to-java-11, blog](https://winterbe.com/posts/2018/08/29/migrate-maven-projects-to-java-11-jigsaw/)