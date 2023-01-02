FROM eclipse-temurin:17.0.5_8-jre
ARG JAR_FILE=target/*.jar
RUN mkdir /opt/app
COPY ${JAR_FILE} /opt/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]