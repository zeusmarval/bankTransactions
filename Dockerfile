FROM openjdk:17-jdk-slim

WORKDIR /

COPY target/*.jar app.jar

EXPOSE 8086

CMD ["java", "-jar", "app.jar"]
