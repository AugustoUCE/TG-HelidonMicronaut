# Imagen ligera con JRE
FROM eclipse-temurin:21.0.9_10-jre-alpine-3.23
WORKDIR /app

# Copiamos el JAR de la app-books
COPY target/app-books.jar .

# Exponemos el puerto de la aplicación
EXPOSE 8030
ENV SERVER_PORT=8030

# Healthcheck
HEALTHCHECK --interval=10s --timeout=3s --start-period=30s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8030/health/live || exit 1

# Comando para ejecutar la app
CMD ["java", "-jar", "app-books.jar"]
