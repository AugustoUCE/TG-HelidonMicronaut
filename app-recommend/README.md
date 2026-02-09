# 🦙 App Recommend - Recomendaciones de Libros con IA Local (Llama 3.1)

Aplicación Micronaut que proporciona gestión de clientes (CRUD), órdenes de compra, y **recomendaciones de libros usando IA local** con **Meta Llama 3.1** vía Ollama.

## ✨ ¿Por qué IA Local?

- ✅ **100% Gratis** - Sin costos por uso
- ✅ **Privado** - Tus datos no salen de tu PC
- ✅ **Sin límites** - Usa cuanto quieras
- ✅ **Funciona offline** - No requiere internet
- ✅ **Rápido** - No hay latencia de red

## 🎯 Funcionalidades

### 1. 🤖 Recomendaciones con IA Local (Llama 3.1)
- Usuario ingresa título de un libro
- Llama 3.1 recomienda 2 libros similares
- Devuelve: título, ISBN, editorial, descripción
- Todo procesado localmente en tu PC

### 2. 👥 CRUD Completo de Customers
- Crear, leer, actualizar, eliminar clientes
- Búsqueda por email
- Contador de clientes

### 3. 📦 CRUD de Purchase Orders
- Gestión de órdenes de compra
- Consulta por cliente
- Relación con customers

## ⚡ Inicio Rápido

### 1️⃣ Instalar Ollama

**Windows:**
```powershell
# Descarga desde: https://ollama.com/download/windows
# Ejecuta el instalador y sigue el wizard
```

**Verificar instalación:**
```powershell
ollama --version
```

### 2️⃣ Instalar el Modelo Llama 3.1

```powershell
ollama pull llama3.1:8b
```

Esto descarga ~4.7GB (demora según tu internet).

### 3️⃣ Configurar PostgreSQL

```powershell
# Crear base de datos
createdb customers_db

# O con psql
psql -U postgres
CREATE DATABASE customers_db;
```

### 4️⃣ Ejecutar la Aplicación

**Opción fácil** - Usa el script:
```powershell
.\run-ollama.bat
```

**Opción manual:**
```powershell
# Compilar
.\mvnw.bat clean compile

# Ejecutar
.\mvnw.bat mn:run
```

La app estará en: **http://localhost:8081/app-recommend**

## 🧪 Probar la Aplicación

### Recomendaciones con IA Local

```bash
curl "http://localhost:8081/app-recommend/recommend?title=Harry%20Potter"
```

**Respuesta (generada por Llama 3.1 local):**
```json
[
  {
    "titulo": "Percy Jackson y el ladrón del rayo",
    "isbn": "978-8498386370",
    "editorial": "Salamandra",
    "descripcion": "Serie de aventuras mitológicas para jóvenes"
  },
  {
    "titulo": "Las crónicas de Narnia",
    "isbn": "978-0060764890",
    "editorial": "HarperCollins",
    "descripcion": "Clásica fantasía con mundos mágicos"
  }
]
```

### CRUD de Clientes

```bash
# Crear cliente
curl -X POST http://localhost:8081/app-recommend/customers \
  -H "Content-Type: application/json" \
  -d '{"first_name":"Juan","last_Name":"Pérez","email":"juan@example.com"}'

# Listar clientes
curl http://localhost:8081/app-recommend/customers

# Obtener por ID
curl http://localhost:8081/app-recommend/customers/1
```

### CRUD de Órdenes

```bash
# Crear orden
curl -X POST http://localhost:8081/app-recommend/orders \
  -H "Content-Type: application/json" \
  -d '{"placedOn":"2026-02-08","total":99.99,"customer":{"id":1}}'

# Ver órdenes de un cliente
curl http://localhost:8081/app-recommend/orders/customer/1
```

## 📚 Documentación Completa

- **[GUIA_OLLAMA.md](GUIA_OLLAMA.md)** - Guía detallada de instalación de Ollama y configuración del modelo
- **[EJEMPLOS_USO.md](EJEMPLOS_USO.md)** - Ejemplos completos de todos los endpoints
- **[RESUMEN.md](RESUMEN.md)** - Resumen técnico del proyecto

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────┐
│  Cliente (cURL, Postman, Browser)          │
└─────────────────┬───────────────────────────┘
                  │
                  v
┌─────────────────────────────────────────────┐
│  Micronaut App (Puerto 8081)                │
│  ┌─────────────────────────────────────┐   │
│  │  REST Controllers                   │   │
│  │  - BooksRecommendRest               │   │
│  │  - CustomersRestController          │   │
│  │  - PurcharseOrderRestController     │   │
│  └──────────┬──────────────────────────┘   │
│             │                               │
│             v                               │
│  ┌─────────────────────────────────────┐   │
│  │  Service Layer                      │   │
│  │  - BooksAiServiceImpl               │   │
│  └──────────┬──────────────────────────┘   │
│             │                               │
│             v                               │
│  ┌─────────────────────────────────────┐   │
│  │  HTTP Client (Micronaut)            │   │
│  └──────────┬──────────────────────────┘   │
└─────────────┼───────────────────────────────┘
              │
              v
┌─────────────────────────────────────────────┐
│  Ollama (Puerto 11434)                      │
│  ┌─────────────────────────────────────┐   │
│  │  Meta-Llama-3.1-8B-Instruct         │   │
│  │  (Modelo de IA Local)               │   │
│  └─────────────────────────────────────┘   │
└─────────────────────────────────────────────┘
              +
┌─────────────────────────────────────────────┐
│  PostgreSQL (Puerto 5432)                   │
│  - customers_db                             │
│    - customer                               │
│    - purcharse_order                        │
└─────────────────────────────────────────────┘
```

## 🔧 Tecnologías

- **Framework**: Micronaut 4.10.7
- **Lenguaje**: Java 21
- **IA**: Meta Llama 3.1 (8B) via Ollama
- **Base de datos**: PostgreSQL
- **ORM**: Micronaut Data JDBC
- **HTTP Client**: Micronaut HTTP Client
- **Serialización**: Jackson + Micronaut Serde
- **Logging**: SLF4J + Logback
- **Build**: Maven

## ⚙️ Configuración

### Cambiar el Modelo de IA

Edita [BooksAiServiceImpl.java](src/main/java/uce/edu/ec/servicios/BooksAiServiceImpl.java):

```java
// Línea 29
private static final String MODEL_NAME = "llama3.1:8b";  // Cambiar aquí
```

**Modelos recomendados:**
```bash
ollama pull llama3.1:8b      # Recomendado (4.7GB)
ollama pull mistral:7b       # Excelente para español (4.1GB)
ollama pull phi3:mini        # Más pequeño y rápido (2.3GB)
ollama pull codellama:7b     # Especializado en código (3.8GB)
```

### Ajustar Creatividad de las Recomendaciones

En `BooksAiServiceImpl.java` línea ~77:

```java
"temperature", 0.7,  // 0.0 = conservador, 2.0 = muy creativo
```

### Cambiar Puerto de la Aplicación

En `Application.yml`:

```yaml
micronaut:
  server:
    port: 8082  # Cambiar aquí
```

## 📊 Endpoints API

### 🤖 Recomendaciones con IA

```
GET /app-recommend/recommend?title={titulo}
```

### 👥 Customers

```
GET    /app-recommend/customers              # Listar todos
GET    /app-recommend/customers/{id}         # Obtener por ID
GET    /app-recommend/customers/email/{email}  # Buscar por email
POST   /app-recommend/customers              # Crear
PUT    /app-recommend/customers/{id}         # Actualizar
DELETE /app-recommend/customers/{id}         # Eliminar
GET    /app-recommend/customers/count        # Contar
```

### 📦 Orders

```
GET    /app-recommend/orders                   # Listar todas
GET    /app-recommend/orders/{id}             # Obtener por ID
GET    /app-recommend/orders/customer/{id}    # Por cliente
POST   /app-recommend/orders                  # Crear
PUT    /app-recommend/orders/{id}             # Actualizar
DELETE /app-recommend/orders/{id}             # Eliminar
GET    /app-recommend/orders/count            # Contar
```

## 🐛 Troubleshooting

### Ollama no responde

```powershell
# Verificar que está corriendo
curl http://localhost:11434/api/version

# Si no responde, iniciar manualmente
ollama serve
```

### Modelo no encontrado

```powershell
# Ver modelos instalados
ollama list

# Descargar el modelo
ollama pull llama3.1:8b
```

### App no compila

```powershell
# Limpiar y recompilar
.\mvnw.bat clean compile

# Si aún falla, verifica Java 21
java -version
```

### PostgreSQL no conecta

```powershell
# Verificar que está corriendo
pg_isready -U postgres

# Ver servicios
Get-Service | Where-Object {$_.Name -like "*postgres*"}
```

### Las recomendaciones son lentas

1. Primera ejecución es lenta (carga el modelo en RAM)
2. Usa un modelo más pequeño: `phi3:mini`
3. Si tienes GPU NVIDIA, instala CUDA Toolkit

## 📦 Requisitos del Sistema

### Mínimos
- **CPU**: 4 cores
- **RAM**: 8 GB (modelo cargado usa ~5GB)
- **Disco**: 10 GB libre
- **Java**: JDK 21
- **PostgreSQL**: 12+

### Recomendados
- **CPU**: 8+ cores
- **RAM**: 16 GB
- **GPU**: NVIDIA con CUDA (opcional, acelera 10x)
- **SSD**: Para carga rápida del modelo

## 🚀 Despliegue

### Desarrollo Local
```bash
.\mvnw.bat mn:run
```

### Producción (JAR)
```bash
.\mvnw.bat package
java -jar target/app-recommend-0.1.jar
```

### Docker (futuro)
```dockerfile
FROM azul/zulu-openjdk:21-latest
# ... configuración ...
```

## 🤝 Comparación: OpenAI vs Ollama

| Característica | OpenAI | Ollama (Local) |
|---|---|---|
| **Costo** | ~$0.002/1000 tokens | **Gratis** |
| **Privacidad** | Datos en la nube | **Todo local** |
| **API Key** | Requerida | **No necesaria** |
| **Internet** | Requerido | **Opcional** |
| **Velocidad inicial** | Rápido | Lento (1ra vez) |
| **Velocidad después** | Depende de red | **Instantáneo** |
| **Límites de uso** | Cuota de API | **Sin límites** |
| **Modelos** | GPT-3.5, GPT-4 | Llama, Mistral, etc |

## 📝 Estructura del Proyecto

```
app-recommend/
├── src/main/java/uce/edu/ec/
│   ├── Application.java                 # Punto de entrada
│   ├── AppRecommendMain.java            # Startup listener
│   ├── db/
│   │   ├── Customer.java               # Entidad Cliente
│   │   └── PurcharseOrder.java         # Entidad Orden
│   ├── dto/
│   │   └── BookRecDto.java             # DTO Recomendación
│   ├── repo/
│   │   ├── CustomersRepo.java          # Repository Cliente
│   │   └── PurcharseOrderRepo.java     # Repository Orden
│   ├── rest/
│   │   ├── BooksRecommendRest.java     # API Recomendaciones IA
│   │   ├── CustomersRestController.java # API Clientes
│   │   └── PurcharseOrderRestController.java # API Órdenes
│   └── servicios/
│       ├── BooksAiService.java         # Interfaz
│       └── BooksAiServiceImpl.java     # Implementación Ollama ⭐
├── src/main/resources/
│   ├── application.properties
│   ├── Application.yml                  # Configuración principal
│   └── logback.xml                      # Configuración logging
├── pom.xml                              # Dependencias Maven
├── run-ollama.bat                       # Script de inicio ⭐
├── GUIA_OLLAMA.md                       # Guía Ollama ⭐
├── EJEMPLOS_USO.md                      # Ejemplos de uso
└── README.md                            # Este archivo ⭐
```

## 💡 Tips y Mejores Prácticas

1. **Mantén Ollama corriendo**: Configúralo para iniciarse con Windows
2. **Usa SSD**: Carga del modelo es mucho más rápida
3. **GPU acelera mucho**: Si tienes NVIDIA, instala CUDA
4. **Experimenta con modelos**: Cada uno tiene sus fortalezas
5. **Ajusta el prompt**: Si las respuestas no son buenas, mejora el prompt
6. **Caché**: Ollama mantiene el modelo en RAM entre usos

## 🎓 Para Aprender Más

- **Ollama Docs**: https://github.com/ollama/ollama
- **Micronaut Docs**: https://docs.micronaut.io/
- **Llama 3.1**: https://ai.meta.com/blog/meta-llama-3-1/
- **Micronaut HTTP Client**: https://docs.micronaut.io/latest/guide/#httpClient

## 📄 Licencia

Proyecto académico - Universidad Central del Ecuador

---

**¿Dudas?** Revisa [GUIA_OLLAMA.md](GUIA_OLLAMA.md) para instrucciones detalladas.

**¿Ejemplos?** Ve [EJEMPLOS_USO.md](EJEMPLOS_USO.md) para casos de uso completos.

¡Disfruta tu IA local y gratuita! 🦙🚀


