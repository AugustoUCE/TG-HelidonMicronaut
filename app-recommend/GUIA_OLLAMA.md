# 🦙 Guía de Instalación y Configuración de Ollama + Llama 3.1

## ¿Qué es Ollama?

**Ollama** es la forma más fácil de ejecutar modelos de IA localmente en tu computadora. Es como tener ChatGPT corriendo en tu PC, 100% gratis y privado.

### Ventajas vs OpenAI:
- ✅ **Gratis** - No pagas por cada uso
- ✅ **Local** - Todo corre en tu PC
- ✅ **Privado** - Tus datos no salen de tu máquina
- ✅ **Rápido** - No depende de internet
- ✅ **Sin límites** - Usa cuanto quieras

## 📥 Paso 1: Instalar Ollama

### Windows

1. Descarga Ollama desde: https://ollama.com/download/windows
2. Ejecuta el instalador `OllamaSetup.exe`
3. Sigue el wizard de instalación
4. Ollama se instalará y se ejecutará automáticamente como servicio

### Verificar la instalación

Abre PowerShell o CMD y ejecuta:

```powershell
ollama --version
```

Deberías ver algo como:
```
ollama version is 0.1.26
```

## 📦 Paso 2: Instalar el Modelo Llama 3.1

Ya tienes el archivo `Meta-Llama-3.1-8B-Instruct-Q8_0.gguf`. Ahora necesitas decirle a Ollama que lo use.

### Opción A: Usar Ollama para descargar (Recomendado)

```powershell
ollama pull llama3.1:8b
```

Esto descarga el modelo optimizado directamente desde Ollama.

### Opción B: Usar tu archivo GGUF

Si quieres usar tu archivo `Meta-Llama-3.1-8B-Instruct-Q8_0.gguf`:

1. **Crear un Modelfile**

Crea un archivo llamado `Modelfile` (sin extensión) con este contenido:

```dockerfile
FROM ./Meta-Llama-3.1-8B-Instruct-Q8_0.gguf

TEMPLATE """{{ if .System }}<|start_header_id|>system<|end_header_id|>

{{ .System }}<|eot_id|>{{ end }}{{ if .Prompt }}<|start_header_id|>user<|end_header_id|>

{{ .Prompt }}<|eot_id|>{{ end }}<|start_header_id|>assistant<|end_header_id|>

{{ .Response }}<|eot_id|>"""

PARAMETER stop "<|start_header_id|>"
PARAMETER stop "<|end_header_id|>"
PARAMETER stop "<|eot_id|>"
PARAMETER stop "<|reserved_special_token"

SYSTEM "Eres un asistente útil."
```

2. **Importar el modelo**

```powershell
# Navega hasta donde está tu archivo GGUF
cd C:\ruta\donde\esta\tu\modelo

# Crea el modelo en Ollama
ollama create llama3.1:8b -f Modelfile
```

3. **Verificar que se creó**

```powershell
ollama list
```

Deberías ver tu modelo listado:
```
NAME              ID            SIZE    MODIFIED
llama3.1:8b       abc123...     8.5 GB  2 minutes ago
```

## 🧪 Paso 3: Probar Ollama

### Prueba básica en terminal

```powershell
ollama run llama3.1:8b "Hola, recomiéndame un libro"
```

Deberías ver una respuesta del modelo.

### Verificar que el servicio está corriendo

```powershell
# Probar que Ollama API está activa
curl http://localhost:11434/api/version
```

Deberías ver:
```json
{"version":"0.1.26"}
```

### Prueba con API (como lo usa tu app)

```powershell
# PowerShell
$body = @{
    model = "llama3.1:8b"
    prompt = "Recomienda 2 libros de ficción"
    stream = $false
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:11434/api/generate" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body
```

## 🚀 Paso 4: Configurar tu App

Tu aplicación ya está configurada para usar Ollama. Solo verifica en `Application.yml`:

```yaml
ollama:
  url: http://localhost:11434
  model: llama3.1:8b
```

## ▶️ Paso 5: Ejecutar Todo

### 1. Asegúrate que Ollama está corriendo

```powershell
# Verificar servicio
Get-Service -Name "Ollama" -ErrorAction SilentlyContinue

# O probar la API
curl http://localhost:11434/api/version
```

### 2. Inicia tu aplicación

```powershell
cd C:\Users\dell\Documents\distribuida-25-26\tgrupal-helidonmicro\tgrupal\app-recommend

.\mvnw.bat mn:run
```

Deberías ver en los logs:
```
🚀 App Recommend iniciada correctamente
🤖 Servicio de recomendaciones con Llama 3.1 Local
📍 Ollama debe estar corriendo en: http://localhost:11434
```

### 3. Probar el endpoint de recomendaciones

```powershell
curl "http://localhost:8081/app-recommend/recommend?title=Harry%20Potter"
```

Respuesta esperada:
```json
[
  {
    "titulo": "Percy Jackson y el ladrón del rayo",
    "isbn": "978-8498386370",
    "editorial": "Salamandra",
    "descripcion": "Serie de aventuras de mitología griega moderna"
  },
  {
    "titulo": "Las crónicas de Narnia",
    "isbn": "978-0060764890",
    "editorial": "HarperCollins",
    "descripcion": "Clásica serie de fantasía para jóvenes lectores"
  }
]
```

## 🔧 Configuración Avanzada

### Ajustar parámetros del modelo

Puedes modificar el comportamiento en `BooksAiServiceImpl.java`:

```java
Map<String, Object> requestBody = Map.of(
    "model", MODEL_NAME,
    "prompt", promptText,
    "stream", false,
    "format", "json",
    "options", Map.of(
        "temperature", 0.7,      // 0.0 = conservador, 2.0 = creativo
        "num_predict", 800,      // Tokens máximos de respuesta
        "top_k", 40,            // Top-K sampling
        "top_p", 0.9            // Nucleus sampling
    )
);
```

### Usar diferentes modelos

Si quieres probar otros modelos:

```powershell
# Modelos populares
ollama pull llama3:8b           # Llama 3 estándar
ollama pull mistral:7b          # Mistral (muy bueno para español)
ollama pull phi3:mini           # Phi-3 (pequeño y rápido)
ollama pull codellama:7b        # Especializado en código

# Listar modelos disponibles
ollama list
```

Cambia en `BooksAiServiceImpl.java`:
```java
private static final String MODEL_NAME = "mistral:7b";  // Por ejemplo
```

### Optimizar rendimiento

#### Para GPU NVIDIA:
Ollama usa automáticamente tu GPU si tienes CUDA instalado.

#### Para más velocidad (menos precisión):
```powershell
# Usar versiones cuantizadas más pequeñas
ollama pull llama3.1:8b-instruct-q4_0  # 4-bit (más rápido)
ollama pull llama3.1:8b-instruct-q5_0  # 5-bit (balance)
```

## ❓ Troubleshooting

### Error: "Ollama service not found"

```powershell
# Iniciar Ollama manualmente
ollama serve
```

O reinicia el servicio:
```powershell
Restart-Service -Name "Ollama"
```

### Error: "model not found"

```powershell
# Ver qué modelos tienes
ollama list

# Descargar el modelo que necesitas
ollama pull llama3.1:8b
```

### Error: "connection refused" en tu app

1. Verifica que Ollama está corriendo:
```powershell
curl http://localhost:11434/api/version
```

2. Si no responde, inicia Ollama:
```powershell
ollama serve
```

### Las recomendaciones son malas o no tienen formato JSON

Ajusta el prompt en `BooksAiServiceImpl.java`. El modelo a veces necesita ejemplos más claros:

```java
String promptText = String.format(
    """
    Eres un bibliotecario experto. Responde SOLO con JSON válido.
    
    Usuario: Me gustó "%s"
    
    Recomienda 2 libros similares en este formato EXACTO:
    [
        {"titulo":"...", "isbn":"...", "editorial":"...", "descripcion":"..."},
        {"titulo":"...", "isbn":"...", "editorial":"...", "descripcion":"..."}
    ]
    
    NO agregues texto antes o después del JSON.
    """, title
);
```

### El modelo es muy lento

Prueba un modelo más pequeño:
```powershell
ollama pull phi3:mini
```

Y cambia en el código:
```java
private static final String MODEL_NAME = "phi3:mini";
```

### Error: "Out of memory"

Tu PC no tiene suficiente RAM. Opciones:

1. Usa un modelo más pequeño (phi3:mini usa ~2GB)
2. Cierra otras aplicaciones
3. Usa una versión cuantizada más agresiva (q4_0)

## 📊 Monitoreo

### Ver uso de recursos
```powershell
# Windows Task Manager
# Busca el proceso "ollama"
# Verás uso de CPU, GPU y RAM
```

### Logs de Ollama
```powershell
# Windows
Get-EventLog -LogName Application -Source Ollama -Newest 50

# O ver logs en tiempo real al ejecutar
ollama serve
```

## 🎯 Comandos Útiles

```powershell
# Ver modelos instalados
ollama list

# Eliminar un modelo
ollama rm llama3.1:8b

# Ver info de un modelo
ollama show llama3.1:8b

# Actualizar Ollama
ollama update

# Detener Ollama
Stop-Service -Name "Ollama"

# Iniciar Ollama
Start-Service -Name "Ollama"
```

## 📚 Recursos

- **Documentación oficial**: https://github.com/ollama/ollama
- **Modelos disponibles**: https://ollama.com/library
- **Discord de Ollama**: https://discord.gg/ollama
- **Issues/Soporte**: https://github.com/ollama/ollama/issues

## 🎓 Diferencias con OpenAI

| Aspecto | OpenAI | Ollama |
|---------|--------|--------|
| **Costo** | Pago por uso (~$0.002/1000 tokens) | Gratis |
| **API Key** | Requerida | No necesaria |
| **Privacidad** | Datos van a servidores OpenAI | Todo local |
| **Internet** | Requerido | Opcional |
| **Velocidad** | Depende de conexión | Depende de tu PC |
| **Modelos** | GPT-3.5, GPT-4, etc. | Llama, Mistral, etc. |
| **Límites** | Cuota de API | Sin límites |

## 💡 Tips

1. **Primera ejecución lenta**: El modelo se carga en memoria la primera vez. Después es instantáneo.

2. **Mantén Ollama corriendo**: Configúralo para iniciarse con Windows:
   ```powershell
   Set-Service -Name "Ollama" -StartupType Automatic
   ```

3. **Probar modelos**: Experimenta con diferentes modelos para encontrar el balance entre calidad y velocidad.

4. **Caché**: Ollama mantiene el modelo en RAM mientras se usa. Si no se usa por un tiempo, libera la memoria automáticamente.

5. **GPU**: Si tienes NVIDIA GPU, instala CUDA Toolkit para acelerar 10x: https://developer.nvidia.com/cuda-downloads

## ✅ Checklist de Instalación Completa

- [ ] Ollama instalado
- [ ] Servicio Ollama corriendo
- [ ] Modelo llama3.1:8b descargado/importado
- [ ] `ollama list` muestra el modelo
- [ ] `curl http://localhost:11434/api/version` responde
- [ ] Aplicación compila sin errores
- [ ] Aplicación se conecta a Ollama
- [ ] Endpoint `/recommend` retorna recomendaciones
- [ ] Las recomendaciones están en formato JSON válido

¡Listo! Ahora tienes IA local y gratuita corriendo en tu aplicación Micronaut. 🚀
