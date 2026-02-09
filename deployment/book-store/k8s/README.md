# Book Store - Red Hat OpenShift

Manifiestos YAML para desplegar en **Red Hat OpenShift 4.x+**.

## Archivos

| Archivo | Descripción |
|---------|-------------|
| `00-namespace.yaml` | Namespace y PVCs |
| `01-postgres.yaml` | PostgreSQL |
| `02-postgres-exporter.yaml` | Exportador métricas PostgreSQL |
| `03-consul.yaml` | Service Discovery |
| `04-traefik.yaml` | API Gateway |
| `05-app-authors.yaml` | Microservicio Autores |
| `06-app-books.yaml` | Microservicio Libros |
| `07-app-web-vaadin.yaml` | Web Vaadin |
| `08-prometheus.yaml` | Monitoreo |
| `09-grafana.yaml` | Visualización |

## Requisitos

- Red Hat OpenShift 4.10+
- `oc` CLI instalado
- Acceso al cluster con permisos de administración

## Despliegue Rápido

```bash
# 1. Obtener e actualizar dominio
DOMAIN=$(oc get ingress.config.openshift.io cluster -o jsonpath='{.spec.domain}')
sed -i "s/apps\.openshift\.example\.com/$DOMAIN/g" *.yaml

# 2. Crear proyecto
oc new-project book-store

# 3. Desplegar todos los manifiestos
oc apply -f .

# 4. Verificar despliegue
oc get pods -n book-store
oc get routes -n book-store
```

## Despliegue Ordenado

```bash
# Crear proyecto
oc new-project book-store

# Actualizar dominio (IMPORTANTE)
DOMAIN=$(oc get ingress.config.openshift.io cluster -o jsonpath='{.spec.domain}')
sed -i "s/apps\.openshift\.example\.com/$DOMAIN/g" *.yaml

# Desplegar en orden
oc apply -f 00-namespace.yaml
oc apply -f 01-postgres.yaml
oc apply -f 02-postgres-exporter.yaml
oc apply -f 03-consul.yaml
oc apply -f 04-traefik.yaml
oc apply -f 05-app-authors.yaml
oc apply -f 06-app-books.yaml
oc apply -f 07-app-web-vaadin.yaml
oc apply -f 08-prometheus.yaml
oc apply -f 09-grafana.yaml

# Verificar
oc get pods -n book-store
oc get routes -n book-store
```

## Acceso a Aplicaciones

```bash
# Ver todas las rutas (URLs)
oc get routes -n book-store

# Acceder a:
# - Consul: https://consul.apps.openshift.example.com
# - Grafana: https://grafana.apps.openshift.example.com (admin/admin)
# - Prometheus: https://prometheus.apps.openshift.example.com
# - Vaadin: https://vaadin.apps.openshift.example.com
# - Traefik Dashboard: https://traefik-dashboard.apps.openshift.example.com
```

## Comandos Útiles

```bash
# Ver logs
oc logs -f deployment/postgres -n book-store

# Monitorear en tiempo real
oc get pods -n book-store -w

# Ver eventos
oc get events -n book-store

# Verificar volúmenes
oc get pvc -n book-store

# Escalar servicio
oc scale deployment/app-authors --replicas=3 -n book-store

# Eliminar todo
oc delete project book-store
```

## Credenciales Predeterminadas

- **Grafana:** `admin` / `admin`
- **PostgreSQL:** `postgres` / `postgres`

>  Cambiar en producción antes de desplegar

## Notas OpenShift

- Storage: configurado con `gp2` (OpenShift estándar)
- Networking: usa Routes (no LoadBalancer)
- Security: UIDs asignados automáticamente por OpenShift SCCs
