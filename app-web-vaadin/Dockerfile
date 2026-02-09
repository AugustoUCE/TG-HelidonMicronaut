# Spring Boot + Maven build with Vaadin
# Usando Maven image en build stage
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /build

# Copy pom.xml
COPY pom.xml .

# Copy source code
COPY src src

# Download dependencies and build application
# Primero descargamos todas las dependencias
RUN mvn dependency:resolve

# Build application with Vaadin (skip Vaadin plugin en contenedor)
RUN mvn clean package -DskipTests -Dvaadin.productionMode=true

# Runtime stage - lightweight image
FROM eclipse-temurin:21.0.9_10-jre-alpine-3.23

WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /build/target/app-web-vaadin-*.jar app-web-vaadin.jar  

EXPOSE 8080

ENV SERVER_PORT=8080
ENV API_BASE_URL=http://app-books:8030/books  

# Health check
HEALTHCHECK --interval=10s --timeout=3s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080 || exit 1

CMD ["java", "-jar", "app-web-vaadin.jar"]
