FROM openjdk:8-jdk-alpine
EXPOSE 8082
COPY target/payment-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "payment-service-0.0.1-SNAPSHOT.jar"]