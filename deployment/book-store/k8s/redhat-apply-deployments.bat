@echo off
echo =========================================
echo  Desplegando Book Store en OpenShift
echo =========================================


echo [1/9] Desplegando PostgreSQL (con PVC)...
oc apply -f 01-postgres.yml


echo [2/9] Desplegando PostgreSQL Exporter...
oc apply -f 02-postgres-exporter.yml

echo [3/9] Desplegando Consul (con PVC)...
oc apply -f 03-consul.yml

echo Esperando a que Consul se inicie correctamente...
timeout /t 10 /nobreak

echo [4/9] Desplegando Traefik...
oc apply -f 04-traefik.yml
timeout /t 5 /nobreak

echo [5/9] Desplegando App Authors...
oc apply -f 05-app-authors.yml
timeout /t 10 /nobreak

echo [6/9] Desplegando App Books...
oc apply -f 06-app-books.yml
timeout /t 10 /nobreak

echo [7/9] Desplegando App Web Vaadin...
oc apply -f 07-app-web-vaadin.yml
timeout /t 5 /nobreak

echo [8/9] Desplegando Prometheus...
oc apply -f 08-prometheus.yml

echo [9/9] Desplegando Grafana...
oc apply -f 09-grafana.yml

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