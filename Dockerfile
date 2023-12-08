FROM openjdk:17-jdk-slim

WORKDIR /

COPY target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
