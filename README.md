# kannel-java #

Java libraries for building applications with the Kannel open source SMS gateway (<http://kannel.org/>). These are currently focused on the SMS functionality of Kannel. These should not yet be considered complete or ready for production use.

## Libraries: ##
- admin: A wrapper on the HTTP administration service exposed by Kannel.
- conf: Reading and writing the Kannel configuration file.
- protocol: The Kannel "box" protocol for building event-driven applications to interact with the bearerbox. (The protocol library was built originally by Oscar Medina Duarte for his graduate thesis project. This project is forked from that one. Original sources can be found on his website <http://www.medina-web.com/kjGateway/index.html>). Currently, the "box" protocol is compatible only with the 1.5.0 development release of Kannel.
- runtime: A utility for starting/stopping the box daemons from Java.
- sms: Wrappers for sending and receiving messages to and from the smsbox HTTP interface, including marshalling and unmarshalling XML used by Kannel for the post-xml version of the sms-service.

## Dependencies: ##
- <http://xmlbeans.apache.org/> - Apache XMLBeans for XML reading and writing.
- <http://commons.apache.org/beanutils/> - Commons BeanUtils for bean property manipulation in 'conf'.
- <http://www.junit.org/> - JUnit for unit tests.
- <http://logging.apache.org/log4j/> - Log4J for logging.
- <http://maven.apache.org/> - Maven is used to build the libraries. All dependencies, except Maven itself will be downloaded during build.
- <http://www.oracle.com/technetwork/java/index-jsp-142945.html> - JMS is used in the 'protocol' sample application. 
- <http://www.oracle.com/technetwork/java/index-jsp-135475.html> - Java Servlets are used in some 'sms' samples.

## Building: ##

    mvn install

The Kannel name is copyright 2001-2010 The Kannel Group. This project is unaffiliated with them.
