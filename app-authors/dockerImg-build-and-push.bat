@echo off
set DOCKER_USER=augustouce
set IMAGE=app-authors-tg
set VERSION=latest

echo ===============================
echo Construyendo imagen Docker
echo ===============================
docker build -t %DOCKER_USER%/%IMAGE%:%VERSION% .

if %ERRORLEVEL% neq 0 (
  echo ❌ Error al construir la imagen
  pause
  exit /b 1
)

echo ===============================
echo Subiendo imagen a Docker Hub
echo ===============================
docker push %DOCKER_USER%/%IMAGE%:%VERSION%

if %ERRORLEVEL% neq 0 (
  echo ❌ Error al hacer push
  pause
  exit /b 1
)

echo ✅ Imagen subida correctamente a Docker Hub
pause
