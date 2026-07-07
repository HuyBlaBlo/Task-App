FROM eclipse-temurin:25-jre-alpine

LABEL maintainer="huybla.com"

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
