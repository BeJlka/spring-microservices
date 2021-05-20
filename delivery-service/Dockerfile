FROM openjdk:8-jdk-alpine
EXPOSE 8083
COPY build/libs/delivery-service-1.0-SNAPSHOT.jar .
#RUN apk add --no-cache bash
#COPY wait-for.sh /wait-for.sh
#RUN chmod +x /wait-for.sh
ENTRYPOINT [
#"/wait-for.sh", "payment-service:8082", "--timeout=70", "--",
"java", "-jar", "delivery-service-0.0.1-SNAPSHOT.jar"]