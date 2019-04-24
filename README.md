# commons_library

How to build and use the commons_library
---

1. Run `mvn clean install` to build your application
2. Use it as a dependency in your microservice for including required jars (like Dropwizard, Aarchaius, Hystrix, Zipkin, Eureka, etc).
3. It provides basic helper and configuration files required to register to Eureka, Zipkin, mongo-service, and Hystrix for making external calls.

Health Check
---

To see the health of your application enter URL `http://localhost:8081/healthcheck`
