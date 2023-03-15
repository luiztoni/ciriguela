FROM eclipse-temurin:17.0.5_8-jre
ARG JAR_FILE=target/*.jar
RUN mkdir /opt/app
COPY ${JAR_FILE} /opt/app/app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["java", $JAVA_OPTS, "-jar", "/opt/app/app.jar"]
