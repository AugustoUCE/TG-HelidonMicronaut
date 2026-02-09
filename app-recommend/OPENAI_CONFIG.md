# Configuración de OpenAI para App Recommend

## 🔑 Paso 1: Obtener tu API Key de OpenAI

1. Ve a https://platform.openai.com/api-keys
2. Inicia sesión o crea una cuenta
3. Haz clic en "Create new secret key"
4. Copia la API key (empieza con `sk-`)
5. **¡IMPORTANTE!** Guarda la key en un lugar seguro, no la podrás ver de nuevo

## 💰 Paso 2: Agregar Créditos (si es necesario)

1. Ve a https://platform.openai.com/account/billing
2. Agrega un método de pago
3. OpenAI cobra por uso:
   - **GPT-3.5-turbo**: ~$0.002 por 1000 tokens (~750 palabras)
   - Este proyecto usa GPT-3.5-turbo por defecto (más económico)

## ⚙️ Paso 3: Configurar la API Key en tu aplicación

### Opción 1: Variable de Entorno (Recomendado para desarrollo)

**Windows - PowerShell:**
```powershell
# Temporal (solo para la sesión actual)
$env:OPENAI_API_KEY = "sk-tu-api-key-aqui"

# Permanente (para el sistema)
[System.Environment]::SetEnvironmentVariable("OPENAI_API_KEY", "sk-tu-api-key-aqui", "User")
```

**Windows - CMD:**
```cmd
REM Temporal
set OPENAI_API_KEY=sk-tu-api-key-aqui

REM Permanente
setx OPENAI_API_KEY "sk-tu-api-key-aqui"
```

**Linux/Mac:**
```bash
# Temporal
export OPENAI_API_KEY=sk-tu-api-key-aqui

# Permanente (agregar a ~/.bashrc o ~/.zshrc)
echo 'export OPENAI_API_KEY=sk-tu-api-key-aqui' >> ~/.bashrc
source ~/.bashrc
```

### Opción 2: Configurar directamente en Application.yml

Edita `src/main/resources/Application.yml`:

```yaml
openai:
  api-key: sk-tu-api-key-real-aqui
```

**⚠️ ADVERTENCIA:** No subas este archivo a Git con la API key real. Usa `.gitignore` o variables de entorno.

### Opción 3: Crear archivo .env (para producción)

Crea un archivo llamado `.env` en la raíz del proyecto:

```properties
OPENAI_API_KEY=sk-tu-api-key-aqui
```

Agrega `.env` a tu `.gitignore`:
```
.env
```

## 🧪 Paso 4: Verificar que funciona

### Verificar que la API key está configurada

**PowerShell:**
```powershell
echo $env:OPENAI_API_KEY
```

**CMD:**
```cmd
echo %OPENAI_API_KEY%
```

Deberías ver tu API key (o parte de ella).

### Probar la aplicación

1. Inicia la aplicación:
```bash
mvnw mn:run
```

2. Espera el mensaje:
```
App Recommend iniciada correctamente
Servicio de recomendaciones con OpenAI disponible
```

3. Prueba el endpoint de recomendaciones:
```bash
curl "http://localhost:8081/app-recommend/recommend?title=Harry%20Potter"
```

**Respuesta esperada:**
```json
[
  {
    "titulo": "Percy Jackson y el ladrón del rayo",
    "isbn": "978-8498386370",
    "editorial": "Salamandra",
    "descripcion": "Serie de aventuras de mitología griega..."
  },
  {
    "titulo": "Las crónicas de Narnia",
    "isbn": "978-0060764890",
    "editorial": "HarperCollins",
    "descripcion": "Clásica serie de fantasía..."
  }
]
```

## 🔧 Configuración Avanzada

### Cambiar el modelo de OpenAI

Edita `BooksAiServiceImpl.java` línea ~63:

```java
Map<String, Object> requestBody = Map.of(
    "model", "gpt-4",  // Cambiar aquí
    // ... resto del código
);
```

**Modelos disponibles:**
- `gpt-3.5-turbo` - Rápido y económico (recomendado para pruebas)
- `gpt-4` - Más inteligente pero más costoso
- `gpt-4-turbo` - Balance entre velocidad y calidad

### Ajustar la creatividad de las recomendaciones

Edita el parámetro `temperature` en `BooksAiServiceImpl.java`:

```java
"temperature", 0.7,  // Valores: 0.0 (conservador) a 2.0 (muy creativo)
```

### Cambiar la cantidad de recomendaciones

Edita el prompt en `BooksAiServiceImpl.java` línea ~39:

```java
String promptText = String.format(
    """
    Recomienda 3 libros para alguien que le gustó "%s".  // Cambiar el número aquí
    Devuelve EXCLUSIVAMENTE un JSON con el siguiente formato...
    """, title
);
```

Y ajusta el formato JSON en el prompt para incluir 3 libros en lugar de 2.

### Aumentar el límite de tokens

Edita `max_tokens` en `BooksAiServiceImpl.java`:

```java
"max_tokens", 2000  // Más tokens = respuestas más largas
```

### Configurar timeout del HTTP Client

Edita `Application.yml`:

```yaml
micronaut:
  http:
    client:
      read-timeout: 60s  # Aumentar si las respuestas son lentas
```

## 🛡️ Seguridad - Mejores Prácticas

### ✅ SÍ hacer:
- Usar variables de entorno para la API key
- Agregar `.env` al `.gitignore`
- Rotar la API key periódicamente
- Monitorear el uso desde el dashboard de OpenAI
- Establecer límites de gasto en OpenAI

### ❌ NO hacer:
- Subir la API key a Git/GitHub
- Compartir la API key públicamente
- Mostrar la API key en logs
- Hardcodear la API key en el código

## 📊 Monitoreo de Uso

### Ver uso de la API

1. Ve a https://platform.openai.com/usage
2. Verás gráficos de:
   - Tokens usados
   - Costo acumulado
   - Requests por día

### Establecer límites de gasto

1. Ve a https://platform.openai.com/account/billing/limits
2. Configura:
   - **Hard limit**: Límite máximo (ej: $10)
   - **Soft limit**: Alerta antes de llegar al límite

## 🐛 Troubleshooting

### Error: "Incorrect API key provided"

**Causa:** La API key es inválida o está mal configurada.

**Solución:**
1. Verifica que la API key empiece con `sk-`
2. Regenera una nueva API key en OpenAI
3. Verifica que no haya espacios extras al copiar/pegar

### Error: "You exceeded your current quota"

**Causa:** No tienes créditos suficientes en OpenAI.

**Solución:**
1. Ve a https://platform.openai.com/account/billing
2. Agrega un método de pago
3. Compra créditos adicionales

### Error: "Rate limit exceeded"

**Causa:** Demasiadas peticiones en poco tiempo.

**Solución:**
1. Espera unos segundos e intenta de nuevo
2. Implementa un sistema de rate limiting en tu app
3. Considera actualizar tu plan en OpenAI

### La app retorna libros por defecto (fallback)

**Causa:** La conexión a OpenAI falló.

**Solución:**
1. Revisa los logs de la aplicación
2. Verifica tu conexión a internet
3. Confirma que la API key sea válida
4. Verifica que OpenAI esté disponible: https://status.openai.com/

### Error: "Connection timeout"

**Causa:** La petición a OpenAI tardó demasiado.

**Solución:**
1. Aumenta el timeout en `Application.yml`:
   ```yaml
   micronaut:
     http:
       client:
         read-timeout: 60s
   ```
2. Verifica tu conexión a internet
3. Intenta con un modelo más rápido (`gpt-3.5-turbo`)

## 📈 Optimización de Costos

### Reducir costos
- Usa `gpt-3.5-turbo` en lugar de `gpt-4` (10x más barato)
- Reduce `max_tokens` para respuestas más cortas
- Implementa caché de recomendaciones frecuentes
- Limita las peticiones por usuario

### Ejemplo de caché simple

```java
// En BooksAiServiceImpl.java
private final Map<String, List<BookRecDto>> cache = new ConcurrentHashMap<>();

@Override
public List<BookRecDto> recommend(String title) {
    // Verificar caché primero
    if (cache.containsKey(title.toLowerCase())) {
        LOG.info("Retornando recomendaciones de caché");
        return cache.get(title.toLowerCase());
    }
    
    // Llamar a OpenAI
    var recommendations = callOpenAI(title);
    
    // Guardar en caché
    cache.put(title.toLowerCase(), recommendations);
    
    return recommendations;
}
```

## 📚 Recursos Adicionales

- **Documentación OpenAI API**: https://platform.openai.com/docs
- **Pricing**: https://openai.com/pricing
- **API Reference**: https://platform.openai.com/docs/api-reference
- **Best Practices**: https://platform.openai.com/docs/guides/production-best-practices
- **Status de OpenAI**: https://status.openai.com/

## 🎓 Preguntas Frecuentes

**P: ¿Cuánto cuesta usar OpenAI?**  
R: Con GPT-3.5-turbo, aproximadamente $0.002 por 1000 tokens. Una recomendación típica usa ~500 tokens = $0.001 por recomendación.

**P: ¿Necesito tarjeta de crédito?**  
R: Sí, OpenAI requiere un método de pago después del período de prueba.

**P: ¿Puedo usar la API gratis?**  
R: OpenAI ofrece $5 de crédito gratis al crear una cuenta nueva (puede variar).

**P: ¿Las recomendaciones son siempre iguales?**  
R: No, debido al parámetro `temperature`, las recomendaciones pueden variar entre llamadas.

**P: ¿Qué pasa si no tengo API key?**  
R: La aplicación retornará recomendaciones por defecto (hardcodeadas) y seguirá funcionando.
