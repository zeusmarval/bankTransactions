FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/bank_transactions_spring.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]
