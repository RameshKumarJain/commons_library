Dropwizard Client
----
Used dropwizard client for communicating with other services using HttpClient(org.apache.http.client.HttpClient).


Google Guice setup
----
Add guice dependency, annotate the classes with @Singleton that you want to be a singleton. So that where ever you want to inject juice will inject only same object.

In the case of Abstract/Interface/extended classes, provide either factory or instance through bean module(by extending AbstractModule).

Archaius configuration : dynamic property
----
By default archaius reads configuration from config.properties(src/main/resources).

Define all your property at compile time itself rather at run time.

use DynamicPropertyFactory to initialize each property.

By default, every 60s property file will be read as a scheduler job. we can change and initialize using ConfigurationManager.

Hystrix setup
----
consume external RestAPI using Hystrix command.

The class which will consume external API should extend the HystrixCommand<T>.

Here in the project, ClientServiceHandler class extends the HystrixCommand and whenever to make external API call, get the instance of ClientServiceHandler and call its execute method.

Initialize the hystrix params using Archaius properties such as execution_timeout, circuit_req_volume_threshold, etc 

Eureka setup
-----

Download eureka source code from git

	git clone https://github.com/Netflix/eureka.git

Build the Eureka Server and client using below command : 

	cd eureka
	./gradlew clean build

If the build fails due to java version incompatible, then change your java version to 1.8 and rebuild it.
If success, deploy the generated Eureka server in tomcat and use eureka client in the project to register.

while including Eureka in the project, the jersy-client version may be different in dropwizard and eureka.
Which will either lead to allow communicating with eureka server or VM to start(because we initialize HttpClient while starting VM).

Currently we are consuming Eureka server API to register and get client details.

API
----

Register an client
---
URL - http://localhost:8080/eureka/v2/apps/school-web-service
Method - POST
Payload - {"instance":{"hostName":"ID_SERVER_00002","app":"school-examination-service","vipAddress":"school-examination-service","secureVipAddress":"school-examination-service","ipAddr":"0.0.0.0","status":"UP","port":{"$":"9050","@enabled":"true"},"securePort":{"$":"8443","@enabled":"false"},"healthCheckUrl":"http://0.0.0.0:9050","statusPageUrl":"http://0.0.0.0:9050","homePageUrl":"http://0.0.0.0:9050","dataCenterInfo":{"@class":"com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo","name":"MyOwn"}}}


Response - 204 No Content

Get Client Details
-----
URL - http://localhost:8080/eureka/v2/apps/SCHOOL-WEB-SERVICE
Method - GET


Response - 
List of registered instance



ZIPKIN
-------

To analyze our API flow, failure and time tracing we use Zipkin
 
We need 2 components to find out the above cases

1 - Zipkin Server
2 - Zipkin service running on our micro-services

Steps for configuring Zipkin in our microservices
-----
1 Import the Zipkin dependency from 

	https://mvnrepository.com/artifact/com.smoketurner.dropwizard/zipkin-core/1.3.8-1

2 Add configuration in config.xml or load it in Configuration class.
3 Initialize the ZipkinWizardFactory and register to Zipkin server in the Application class.

-------------------------------------

