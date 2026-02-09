FROM eclipse-temurin:21.0.9_10-jre-alpine-3.23
WORKDIR /app
COPY target/app-customers-0.1.jar app-customers.jar
EXPOSE 8070
ENV MICRONAUT_SERVER_PORT=8070
ENTRYPOINT ["java", "-jar", "app-customers.jar"]
