## Micronaut 4.10.7 Documentation

- [User Guide](https://docs.micronaut.io/4.10.7/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.10.7/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.10.7/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Micronaut Maven Plugin documentation](https://micronaut-projects.github.io/micronaut-maven-plugin/latest/)
## Feature maven-enforcer-plugin documentation
##
- [https://maven.apache.org/enforcer/maven-enforcer-plugin/](https://maven.apache.org/enforcer/maven-enforcer-plugin/)

## documentation project 
# COMANDOS PARA CONSTRUIR Y EJECUTAR

# 1. Compilar el proyecto
mvn clean package -DskipTests

# 2. Construir la imagen Docker
docker build -t augustouce/app-authors:latest .

# 3. Levantar todo con docker-compose
docker-compose up -d

# 4. Ver logs del servicio authors
docker-compose logs -f authors

# 5. Verificar URLs
# Consul UI: http://localhost:8500/ui/
# Traefik Dashboard: http://localhost:8888/dashboard/
# Tu servicio: http://localhost:80/app-authors/authors