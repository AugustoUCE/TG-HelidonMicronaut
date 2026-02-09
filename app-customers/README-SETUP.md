# Configuración y Ejecución del Servicio Customers

## Descripción
Servicio REST Micronaut que se conecta a PostgreSQL y proporciona dos operaciones:
- **Buscar todos los clientes** (GET /customers)
- **Buscar cliente por first_name** (GET /customers/search?firstName=XXX)

## Configuración de Base de Datos

### Requisitos Previos
- PostgreSQL instalado y ejecutándose en localhost:5432
- Base de datos: `postgres`
- Usuario: `postgres`
- Contraseña: `postgres`

### Esquema de Base de Datos
Crear la tabla customer:

```sql
CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE
);

-- Datos de prueba
INSERT INTO customer (first_name, last_name, email) VALUES
('Juan', 'Pérez', 'juan.perez@email.com'),
('María', 'González', 'maria.gonzalez@email.com'),
('Juan', 'López', 'juan.lopez@email.com'),
('Carlos', 'Martínez', 'carlos.martinez@email.com');
```

## Ejecución de la Aplicación

### Opción 1: Con Maven
```bash
.\mvnw.bat mn:run
```

### Opción 2: JAR Ejecutable
```bash
java -jar target/app-customers-0.1.jar
```

La aplicación se ejecutará en: http://localhost:8040

## Endpoints REST

### 1. Obtener todos los clientes
```bash
# PowerShell
Invoke-RestMethod -Uri http://localhost:8040/customers -Method GET | ConvertTo-Json

# curl
curl http://localhost:8040/customers
```

**Respuesta:**
```json
[
  {
    "id": 1,
    "first_name": "Juan",
    "last_name": "Pérez",
    "email": "juan.perez@email.com",
    "purcharseOrders": []
  },
  {
    "id": 2,
    "first_name": "María",
    "last_name": "González",
    "email": "maria.gonzalez@email.com",
    "purcharseOrders": []
  }
]
```

### 2. Buscar clientes por first_name
```bash
# PowerShell
Invoke-RestMethod -Uri "http://localhost:8040/customers/search?firstName=Juan" -Method GET | ConvertTo-Json

# curl
curl "http://localhost:8040/customers/search?firstName=Juan"
```

**Respuesta:**
```json
[
  {
    "id": 1,
    "first_name": "Juan",
    "last_name": "Pérez",
    "email": "juan.perez@email.com",
    "purcharseOrders": []
  },
  {
    "id": 3,
    "first_name": "Juan",
    "last_name": "López",
    "email": "juan.lopez@email.com",
    "purcharseOrders": []
  }
]
```

### 3. Health Check
```bash
curl http://localhost:8040/health
```

## Configuración (application.yml)
```yaml
micronaut:
  application:
    name: app-customers
  server:
    port: 8040
    cors:
      enabled: true

datasources:
  default:
    url: jdbc:postgresql://localhost:5432/postgres
    driverClassName: org.postgresql.Driver
    username: postgres
    password: postgres
```

## Resumen de Cambios Realizados

### Entidades
- **Customer**: Configurada con `@Id`, `@GeneratedValue`, `@AutoPopulated` y tipo Long
- **PurcharseOrder**: Misma configuración

### Repositorios
- **CustomersRepo**: Usa `GenericRepository` con queries explícitas (@Query)
  - `findAll()`: Obtiene todos los clientes
  - `findByFirstName(String)`: Busca por nombre
- **PurcharseOrderRepo**: También usa `GenericRepository`

### REST Controller (CustomerRest)
- `GET /customers`: Lista todos los clientes
- `GET /customers/search?firstName=XXX`: Busca por nombre

## Solución de Problemas

### Error: "Unable to connect to database"
Verificar que PostgreSQL esté corriendo:
```bash
# Ver servicios de PostgreSQL
Get-Service | Where-Object {$_.Name -like "*postgres*"}
```

### Error: "Table does not exist"
Ejecutar el script SQL para crear la tabla customer.

### Error en compilación
```bash
.\mvnw.bat clean compile
```
