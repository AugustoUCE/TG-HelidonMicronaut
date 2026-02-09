@echo off
REM Script para ejecutar la aplicación con Ollama

echo ========================================
echo   App Recommend - Llama 3.1 Local
echo ========================================
echo.

REM Verificar que Ollama está instalado
where ollama >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Ollama no está instalado
    echo.
    echo Descarga Ollama desde: https://ollama.com/download/windows
    echo O ve la GUIA_OLLAMA.md para instrucciones completas
    echo.
    pause
    exit /b 1
)

echo [OK] Ollama instalado
echo.

REM Verificar que Ollama está corriendo
curl -s http://localhost:11434/api/version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [WARNING] Ollama no está corriendo
    echo Iniciando Ollama...
    start "" ollama serve
    timeout /t 3 /nobreak >nul
    echo.
)

echo [OK] Ollama está corriendo
echo.

REM Verificar que el modelo está instalado
ollama list | findstr "llama3.1" >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [WARNING] Modelo llama3.1:8b no encontrado
    echo.
    echo Descargando modelo (esto puede tardar varios minutos)...
    ollama pull llama3.1:8b
    echo.
)

echo [OK] Modelo llama3.1:8b disponible
echo.

REM Verificar PostgreSQL
echo Verificando PostgreSQL...
psql -U postgres -h localhost -p 5432 -d postgres -c "\q" 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [WARNING] No se pudo conectar a PostgreSQL
    echo Asegúrate de que PostgreSQL esté corriendo en localhost:5432
    echo.
)

echo.
echo Compilando el proyecto...
echo ========================================
call mvnw.bat clean compile
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Falló la compilación
    pause
    exit /b 1
)

echo.
echo ========================================
echo Iniciando la aplicación...
echo ========================================
echo.
echo 🤖 IA Local: Llama 3.1 (8B)
echo 📍 Ollama: http://localhost:11434
echo 🌐 API: http://localhost:8081/app-recommend
echo.
echo Endpoints disponibles:
echo    - GET /app-recommend/customers
echo    - GET /app-recommend/orders
echo    - GET /app-recommend/recommend?title=Harry Potter
echo.
echo Presiona Ctrl+C para detener la aplicación
echo.

call mvnw.bat mn:run
