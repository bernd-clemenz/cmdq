# Sample Spring-Web-Application running on Java 11

## Abstract

A template or example for a Spring-Webapplication with all modules
compatible with Java 12. The project itself has no further purpose
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
<dependencies>
  <!-- lots of stuff omitted ... -->
  <dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.4.0-b180830.0359</version>
  </dependency>
  <dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>2.4.0-b180830.0438</version>
  </dependency>
</dependencies>
```

Same goes for the SOAP-Webservice parts. In version 1.0.1 a sample Webservice
connection is added. Based proy classes generated from a WSDL and a Maven
plugin. The integration of SOAP-handling is left to Springs SOAP-template
functionality. As the SOAP classes are also removed from Java-SE we add them.
Otherwise you end with some 'ClassNotFound' exceptions.

```xml
<dependencies>
  <!-- lots of stuff omitted ... -->
    <dependency>
      <groupId>javax.xml.ws</groupId>
      <artifactId>jaxws-api</artifactId>
      <version>2.3.1</version>
    </dependency>
    <dependency>
      <groupId>com.sun.xml.messaging.saaj</groupId>
      <artifactId>saaj-impl</artifactId>
      <version>1.5.1</version>
    </dependency>
</dependencies>
```

We also need to add the Springs template component:
```xml
<dependencies>
  <!-- lots of stuff omitted ... -->
   <dependency>
      <groupId>org.springframework.ws</groupId>
      <artifactId>spring-ws-core</artifactId>
      <version>3.0.7.RELEASE</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
    <dependency>
      <groupId>org.apache.httpcomponents</groupId>
      <artifactId>httpclient</artifactId>
      <version>4.5.9</version>
    </dependency>
</dependencies>
```


## Notes

### Lombok

To view the code correctly in your IDE install the 'Lombock'-plugin. Such
plugins exist AFAIK for Eclipse and IntelliJ. As fas as Lombok is used in
this example, Lombok works perfectly fine and is a great time-saver.

### Aspects

Still trouble with Aspectj 1.9.1. Waiting for 1.9.2 to be fully compatible with 
Java 11. But could make Aspects work with Springs instrumentation library.

Now with AspectJ 1.9.3 Aspects work just fine.

### Junit 5

* My IntelliJ (2018.2.5) has trouble debugging Junit5 tests. It claims **'Junit not found
in module'**. Gone in 2019 version.
* Same goes for executing a test in the IDE
* In Maven 3.0.4 everything works as expected
* In Maven 3.5.2 everything works as expected
* In Maven 3.6.0 everything works as expected
* In Maven 3.6.1 everything works as expected

## Compiler warnings

* all @SuppressWarnings("unused") are set as the IDE does not resolve IoC usage.


## Modules

* [Spring-Framework](https://www.spring.io)
* [Lombok](https://projectlombok.org)
* [log4j2](https://logging.apache.org/log4j/2.x/)
* [Jython](http://www.jython.org)

## License

The License for the source code is Apache 2.0. But pay attention to the different licenses
of the dependencies.

## Other Sources

* [Log4j2 tutorial](https://howtodoinjava.com/log4j2/log4j2-properties-example/)
* [migrate-maven-projects-to-java-11, blog](https://winterbe.com/posts/2018/08/29/migrate-maven-projects-to-java-11-jigsaw/)
