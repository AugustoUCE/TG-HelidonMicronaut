@echo off
echo =========================================
echo  Desplegando Book Store en OpenShift
echo =========================================


echo [1/9] Desplegando PostgreSQL (con PVC)...
oc apply -f 01-postgres.yaml


echo [2/9] Desplegando PostgreSQL Exporter...
oc apply -f 02-postgres-exporter.yaml

echo [3/9] Desplegando Consul (con PVC)...
oc apply -f 03-consul.yaml

echo Esperando a que Consul se inicie correctamente...
timeout /t 5 /nobreak

echo [4/9] Desplegando Traefik...
oc apply -f 04-traefik.yaml
timeout /t 5 /nobreak

echo [5/9] Desplegando App Authors...
oc apply -f 05-app-authors.yaml
timeout /t 5 /nobreak

echo [6/9] Desplegando App Books...
oc apply -f 06-app-books.yaml
timeout /t 5 /nobreak

echo [7/9] Desplegando App Web Vaadin...
oc apply -f 07-app-web-vaadin.yaml
timeout /t 5 /nobreak

echo [8/9] Desplegando Prometheus...
oc apply -f 08-prometheus.yaml

echo [9/9] Desplegando Grafana...
oc apply -f 09-grafana.yaml

echo.
echo =========================================
echo  Despliegue Completado
echo =========================================
echo.
echo Verificando estado de los pods:
oc get pods
echo.
echo Obteniendo URLs de acceso:
oc get routes
echo.
echo Presiona cualquier tecla para salir...
pause > nul