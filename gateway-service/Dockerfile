FROM openjdk:8-jdk-alpine
EXPOSE 8989
COPY build/libs/gateway-service-0.0.1-SNAPSHOT.jar .
#RUN apk add --no-cache bash
#COPY wait-for.sh /wait-for.sh
#RUN chmod +x /wait-for.sh
ENTRYPOINT [
#"/wait-for.sh", "discovery-service:8761", "--timeout=70", "--",
"java", "-jar", "gateway-service-0.0.1-SNAPSHOT.jar"]