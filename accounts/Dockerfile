#Start with a base image containing Java runtime
FROM openjdk:17-slim as build

#Information around who maintains the image
MAINTAINER eazybytes.com

# Add the application's jar to the container
COPY target/accounts-1.0-SNAPSHOT.jar accounts-1.0-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java", "-jar", "/accounts-1.0-SNAPSHOT.jar"]

