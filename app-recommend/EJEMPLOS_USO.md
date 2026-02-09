# Ejemplos de Uso - App Recommend

Este archivo contiene ejemplos prácticos de cómo usar la API de recomendaciones.

## 🔑 Configurar OpenAI API Key

### Windows (PowerShell)
```powershell
$env:OPENAI_API_KEY = "sk-tu-api-key-aqui"
```

### Windows (CMD)
```cmd
set OPENAI_API_KEY=sk-tu-api-key-aqui
```

### Linux/Mac
```bash
export OPENAI_API_KEY=sk-tu-api-key-aqui
```

## 🚀 Iniciar la Aplicación

```bash
# Opción 1: Usar el script
run.bat

# Opción 2: Directamente con Maven
mvnw mn:run
```

## 📚 Ejemplos de Uso con cURL

### 1. Obtener Recomendaciones de Libros

```bash
# Ejemplo 1: Recomendaciones para Harry Potter
curl "http://localhost:8081/app-recommend/recommend?title=Harry%20Potter"

# Ejemplo 2: Recomendaciones para El Señor de los Anillos
curl "http://localhost:8081/app-recommend/recommend?title=El%20Se%C3%B1or%20de%20los%20Anillos"

# Ejemplo 3: Recomendaciones para Cien años de soledad
curl "http://localhost:8081/app-recommend/recommend?title=Cien%20a%C3%B1os%20de%20soledad"
```

**Respuesta esperada:**
```json
[
  {
    "titulo": "Harry Potter y la piedra filosofal",
    "isbn": "978-8498387087",
    "editorial": "Salamandra",
    "descripcion": "La primera entrega de la saga de Harry Potter"
  },
  {
    "titulo": "Percy Jackson y el ladrón del rayo",
    "isbn": "978-8498386370",
    "editorial": "Salamandra",
    "descripcion": "Aventuras de mitología griega moderna"
  }
]
```

### 2. Gestión de Clientes

#### Crear un cliente
```bash
curl -X POST http://localhost:8081/app-recommend/customers ^
  -H "Content-Type: application/json" ^
  -d "{\"first_name\":\"Juan\",\"last_Name\":\"Pérez\",\"email\":\"juan.perez@example.com\"}"
```

#### Listar todos los clientes
```bash
curl http://localhost:8081/app-recommend/customers
```

#### Obtener un cliente específico
```bash
curl http://localhost:8081/app-recommend/customers/1
```

#### Buscar cliente por email
```bash
curl http://localhost:8081/app-recommend/customers/email/juan.perez@example.com
```

#### Actualizar un cliente
```bash
curl -X PUT http://localhost:8081/app-recommend/customers/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"first_name\":\"Juan Carlos\",\"last_Name\":\"Pérez García\",\"email\":\"juan.perez@example.com\"}"
```

#### Eliminar un cliente
```bash
curl -X DELETE http://localhost:8081/app-recommend/customers/1
```

#### Contar clientes
```bash
curl http://localhost:8081/app-recommend/customers/count
```

### 3. Gestión de Órdenes de Compra

#### Crear una orden
```bash
curl -X POST http://localhost:8081/app-recommend/orders ^
  -H "Content-Type: application/json" ^
  -d "{\"placedOn\":\"2026-02-08\",\"total\":150.50,\"customer\":{\"id\":1}}"
```

#### Listar todas las órdenes
```bash
curl http://localhost:8081/app-recommend/orders
```

#### Obtener órdenes de un cliente
```bash
curl http://localhost:8081/app-recommend/orders/customer/1
```

#### Obtener una orden específica
```bash
curl http://localhost:8081/app-recommend/orders/1
```

#### Actualizar una orden
```bash
curl -X PUT http://localhost:8081/app-recommend/orders/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"placedOn\":\"2026-02-08\",\"deliveredOn\":\"2026-02-10\",\"total\":150.50,\"customer\":{\"id\":1}}"
```

#### Eliminar una orden
```bash
curl -X DELETE http://localhost:8081/app-recommend/orders/1
```

## 🧪 Flujo de Prueba Completo

### Paso 1: Crear un cliente
```bash
curl -X POST http://localhost:8081/app-recommend/customers ^
  -H "Content-Type: application/json" ^
  -d "{\"first_name\":\"María\",\"last_Name\":\"González\",\"email\":\"maria.gonzalez@example.com\"}"
```

### Paso 2: Obtener recomendaciones de libros
```bash
curl "http://localhost:8081/app-recommend/recommend?title=1984"
```

### Paso 3: Crear una orden basada en la recomendación
```bash
curl -X POST http://localhost:8081/app-recommend/orders ^
  -H "Content-Type: application/json" ^
  -d "{\"placedOn\":\"2026-02-08\",\"total\":25.99,\"customer\":{\"id\":1}}"
```

### Paso 4: Ver las órdenes del cliente
```bash
curl http://localhost:8081/app-recommend/orders/customer/1
```

## 📊 Ejemplos con PowerShell

### Obtener recomendaciones
```powershell
Invoke-RestMethod -Uri "http://localhost:8081/app-recommend/recommend?title=Harry Potter" -Method Get
```

### Crear cliente
```powershell
$body = @{
    first_name = "Ana"
    last_Name = "Martínez"
    email = "ana.martinez@example.com"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/app-recommend/customers" `
  -Method Post `
  -ContentType "application/json" `
  -Body $body
```

### Listar clientes
```powershell
Invoke-RestMethod -Uri "http://localhost:8081/app-recommend/customers" -Method Get
```

## 🔍 Probar con Postman

1. **Importar colección** (crear archivo `app-recommend.postman_collection.json`):

```json
{
  "info": {
    "name": "App Recommend API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Recomendaciones",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8081/app-recommend/recommend?title=Harry Potter",
          "query": [
            {
              "key": "title",
              "value": "Harry Potter"
            }
          ]
        }
      }
    },
    {
      "name": "Crear Cliente",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"first_name\": \"Pedro\",\n  \"last_Name\": \"López\",\n  \"email\": \"pedro.lopez@example.com\"\n}"
        },
        "url": "http://localhost:8081/app-recommend/customers"
      }
    },
    {
      "name": "Listar Clientes",
      "request": {
        "method": "GET",
        "url": "http://localhost:8081/app-recommend/customers"
      }
    }
  ]
}
```

## 🎯 Casos de Uso Reales

### Caso 1: Sistema de recomendaciones personalizado
```bash
# Usuario busca libro similar a uno que le gustó
curl "http://localhost:8081/app-recommend/recommend?title=El%20principito"

# La IA recomienda 2 libros similares
# Usuario crea orden con los libros recomendados
curl -X POST http://localhost:8081/app-recommend/orders ...
```

### Caso 2: Gestión de clientes y sus compras
```bash
# 1. Registrar nuevo cliente
curl -X POST http://localhost:8081/app-recommend/customers...

# 2. Cliente solicita recomendaciones
curl "http://localhost:8081/app-recommend/recommend?title=..."

# 3. Registrar compra
curl -X POST http://localhost:8081/app-recommend/orders...

# 4. Ver historial de compras del cliente
curl http://localhost:8081/app-recommend/orders/customer/1
```

## 📝 Notas

- La API key de OpenAI debe estar configurada antes de usar el endpoint de recomendaciones
- Los endpoints de customers y orders funcionan independientemente de OpenAI
- En caso de error con OpenAI, la API retorna recomendaciones predeterminadas
- El modelo usado es `gpt-3.5-turbo` (más económico para pruebas)
