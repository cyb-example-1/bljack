## Synopsis

Sample blackjack project 

## Prerequisites

 * JDK 1.8
 * Gradle 3.4

## Configuration

Configuration files are located in
* bj-client\src\main\resources\application.properties
* bj-server\src\main\resources\application.properties

## Installation

###bj-client
```
cd bj-client
gradle shadowJar -x
```
result will be in   build\libs\bj-client-all.jar

###bj-server
```
cd bj-server
gradle war
```
result will be in   build\libs\bj-server-0.1.0.war

## Running
###bj-client
```
java -jar bj-client\build\libs\bj-client-all.jar
```
result will be in   build\libs\bj-client-all.jar

###bj-server
```
cd bj-server
gradle bootRun
```
or deploy WAR to a tomcat. This requires client configuration for a proper context unless delpoy as ROOT.war
result will be in   build\libs\bj-server-0.1.0.war


## API Reference

[REST api](api.md)

## License

MIT