FROM docker.io/eclipse-temurin:22-jre-alpine

LABEL maintainer="Luiz Toni <luiztoni@example.com>"
LABEL version="1.0"
LABEL description="Ciriguela"

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=target/*.jar
WORKDIR /opt/app
COPY ${JAR_FILE} spring-app.jar

ENV DB_HOST="localhost"
ENV DB_USER="postgres"
ENV DB_PASSWORD="pgroot"
ENV DB_PORT="5432"
ENV DB_NAME="pgdb"

ENV ADM_EMAIL="admin@admin.com"
ENV ADM_PASSWORD="password"

ENTRYPOINT ["java","-jar","spring-app.jar"]
