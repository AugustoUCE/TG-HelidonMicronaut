
# Imagen ligera con JRE
FROM eclipse-temurin:21.0.9_10-jre-alpine-3.23
WORKDIR /app

COPY target/app-authors.jar .


EXPOSE 8080
ENV SERVER_PORT=8080

# Healthcheck
HEALTHCHECK --interval=10s --timeout=3s --start-period=30s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/health/live || exit 1

# Comando para ejecutar la app
CMD ["java", "-jar", "app-authors.jar"]

