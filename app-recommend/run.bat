@echo off
REM Script para configurar y ejecutar la aplicación de recomendaciones

echo ========================================
echo   App Recommend - Setup & Run
echo ========================================
echo.

REM Verificar si la API key está configurada
if "%OPENAI_API_KEY%"=="" (
    echo [ERROR] La variable de entorno OPENAI_API_KEY no está configurada
    echo.
    echo Por favor, configura tu API key de OpenAI:
    echo    set OPENAI_API_KEY=tu-api-key-aqui
    echo.
    echo Obtén tu API key en: https://platform.openai.com/api-keys
    echo.
    pause
    exit /b 1
)

echo [OK] OpenAI API Key configurada
echo.

REM Verificar que PostgreSQL esté corriendo
echo Verificando conexión a PostgreSQL...
psql -U postgres -h localhost -p 5432 -d postgres -c "\q" 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [WARNING] No se pudo conectar a PostgreSQL
    echo Asegurate de que PostgreSQL este corriendo en localhost:5432
    echo.
    pause
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
echo La aplicación estará disponible en:
echo    http://localhost:8081/app-recommend
echo.
echo Endpoints disponibles:
echo    - GET /app-recommend/customers
echo    - GET /app-recommend/orders
echo    - GET /app-recommend/recommend?title=Harry Potter
echo.

call mvnw.bat mn:run
