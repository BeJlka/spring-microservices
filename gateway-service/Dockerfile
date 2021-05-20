FROM openjdk:8-jdk-alpine
EXPOSE 8989
COPY target/gateway-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "gateway-service-0.0.1-SNAPSHOT.jar"]