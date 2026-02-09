# App Recommend - Sistema de Recomendaciones de Libros con IA

Aplicación Micronaut que proporciona gestión de clientes (CRUD) y recomendaciones de libros usando OpenAI GPT-3.5-turbo.

## 🎯 Funcionalidades Principales

### 1. Gestión de Clientes (Customers)
- CRUD completo de clientes
- Búsqueda por email
- Contador de clientes

### 2. Gestión de Órdenes de Compra (Purchase Orders)
- CRUD completo de órdenes
- Consulta de órdenes por cliente
- Relación entre customers y órdenes

### 3. Recomendaciones de Libros con IA
- El usuario ingresa el título de un libro
- OpenAI GPT-3.5 recomienda 2 libros similares
- Devuelve: título, ISBN, editorial y descripción

## 🚀 Configuración Inicial

### 1. Configurar la Base de Datos PostgreSQL

```bash
# Crear la base de datos
createdb customers_db

# O usando psql
psql -U postgres
CREATE DATABASE customers_db;
```

### 2. Configurar OpenAI API Key

Edita `src/main/resources/Application.yml` y configura tu API key:

```yaml
openai:
  api-key: sk-tu-api-key-de-openai-aqui
```

O usando variable de entorno:

```bash
# Windows
set OPENAI_API_KEY=sk-tu-api-key-de-openai-aqui

# Linux/Mac
export OPENAI_API_KEY=sk-tu-api-key-de-openai-aqui
```

Para obtener tu API key:
1. Visita https://platform.openai.com/api-keys
2. Crea una cuenta o inicia sesión
3. Genera una nueva API key

### 3. Compilar y Ejecutar

```bash
# Compilar el proyecto
mvnw clean compile

# Ejecutar la aplicación
mvnw mn:run
```

La aplicación estará disponible en: `http://localhost:8081/app-recommend`

## 📡 Endpoints API

### Recomendaciones de Libros con IA

#### Obtener recomendaciones
```http
GET /app-recommend/recommend?title=Cien%20años%20de%20soledad
```

**Respuesta:**
```json
[
  {
    "titulo": "El amor en los tiempos del cólera",
    "isbn": "978-0307389732",
    "editorial": "Vintage Español",
    "descripcion": "Una historia épica de amor que desafía el tiempo"
  },
  {
    "titulo": "Rayuela",
    "isbn": "978-8420405964",
    "editorial": "Alfaguara",
    "descripcion": "Una novela experimental que revolucionó la literatura latinoamericana"
  }
]
```

### Gestión de Clientes

#### Listar todos los clientes
```http
GET /app-recommend/customers
```

#### Obtener cliente por ID
```http
GET /app-recommend/customers/{id}
```

#### Buscar cliente por email
```http
GET /app-recommend/customers/email/{email}
```

#### Crear nuevo cliente
```http
POST /app-recommend/customers
Content-Type: application/json

{
  "first_name": "Juan",
  "last_Name": "Pérez",
  "email": "juan.perez@example.com"
}
```

#### Actualizar cliente
```http
PUT /app-recommend/customers/{id}
Content-Type: application/json

{
  "first_name": "Juan",
  "last_Name": "Pérez García",
  "email": "juan.perez@example.com"
}
```

#### Eliminar cliente
```http
DELETE /app-recommend/customers/{id}
```

#### Contar clientes
```http
GET /app-recommend/customers/count
```

### Gestión de Órdenes de Compra

#### Listar todas las órdenes
```http
GET /app-recommend/orders
```

#### Obtener orden por ID
```http
GET /app-recommend/orders/{id}
```

#### Obtener órdenes de un cliente
```http
GET /app-recommend/orders/customer/{customerId}
```

#### Crear nueva orden
```http
POST /app-recommend/orders
Content-Type: application/json

{
  "placedOn": "2026-02-08",
  "total": 150.50,
  "customer": {
    "id": 1
  }
}
```

#### Actualizar orden
```http
PUT /app-recommend/orders/{id}
Content-Type: application/json

{
  "placedOn": "2026-02-08",
  "deliveredOn": "2026-02-10",
  "total": 150.50,
  "customer": {
    "id": 1
  }
}
```

#### Eliminar orden
```http
DELETE /app-recommend/orders/{id}
```

#### Contar órdenes
```http
GET /app-recommend/orders/count
```

## 🧪 Pruebas con cURL

### Probar Recomendaciones de Libros
```bash
curl "http://localhost:8081/app-recommend/recommend?title=Harry%20Potter"
```

### Crear un Cliente
```bash
curl -X POST http://localhost:8081/app-recommend/customers \
  -H "Content-Type: application/json" \
  -d '{
    "first_name": "María",
    "last_Name": "González",
    "email": "maria.gonzalez@example.com"
  }'
```

### Listar todos los Clientes
```bash
curl http://localhost:8081/app-recommend/customers
```

### Crear una Orden
```bash
curl -X POST http://localhost:8081/app-recommend/orders \
  -H "Content-Type: application/json" \
  -d '{
    "placedOn": "2026-02-08",
    "total": 99.99,
    "customer": {"id": 1}
  }'
```

## 🏗️ Estructura del Proyecto

```
src/main/java/uce/edu/ec/
├── Application.java              # Punto de entrada
├── db/                           # Entidades JPA
│   ├── Customer.java             # Entidad Cliente
│   └── PurcharseOrder.java       # Entidad Orden
├── dto/                          # DTOs
│   └── BookRecDto.java           # DTO Recomendación
├── repo/                         # Repositorios
│   ├── CustomersRepo.java
│   └── PurcharseOrderRepo.java
├── rest/                         # Controllers REST
│   ├── BooksRecommendRest.java   # API Recomendaciones
│   ├── CustomersRestController.java
│   └── PurcharseOrderRestController.java
└── servicios/                    # Servicios
    ├── BooksAiService.java       # Interfaz
    └── BooksAiServiceImpl.java   # Implementación OpenAI
```

## ⚙️ Tecnologías Utilizadas

- **Micronaut 4.10.7** - Framework
- **Java 21** - Lenguaje
- **PostgreSQL** - Base de datos
- **Micronaut Data JDBC** - ORM
- **OpenAI GPT-3.5-turbo** - IA para recomendaciones
- **Lombok** - Reducción de boilerplate
- **Jackson** - Serialización JSON
- **SLF4J + Logback** - Logging

## 🔧 Configuración Avanzada

### Cambiar Puerto
Edita `Application.yml`:
```yaml
micronaut:
  server:
    port: 8082
```

### Configurar Base de Datos
```yaml
datasources:
  default:
    url: jdbc:postgresql://localhost:5432/customers_db
    username: postgres
    password: tu-contraseña
```

### Ajustar Modelo de OpenAI
Edita `BooksAiServiceImpl.java`:
```java
Map<String, Object> requestBody = Map.of(
    "model", "gpt-4",  // Cambiar modelo
    "temperature", 0.8, // Ajustar creatividad
    "max_tokens", 2000  // Ajustar longitud
);
```

## 📝 Notas Importantes

1. **OpenAI API Key**: Necesitas una API key válida para que funcionen las recomendaciones
2. **PostgreSQL**: La base de datos debe estar corriendo antes de iniciar la app
3. **Modelo Básico**: Usa `gpt-3.5-turbo` por defecto (más económico para pruebas)
4. **Recomendaciones por Defecto**: Si OpenAI falla, retorna libros predeterminados

## 🐛 Troubleshooting

### Error: No se puede conectar a PostgreSQL
```bash
# Verificar que PostgreSQL esté corriendo
pg_isready -U postgres
```

### Error: OpenAI API Key inválida
- Verifica que la API key sea correcta
- Asegúrate de que tenga créditos disponibles
- Revisa los logs de la aplicación

### Error: Puerto 8081 en uso
```bash
# Windows
netstat -ano | findstr :8081

# Linux/Mac
lsof -i :8081
```

## 📄 Licencia

Este proyecto es parte de un trabajo académico de la Universidad Central del Ecuador.
