# Monitoreo - Helidon MP 4.3.3

##  URLs de los servicios
- App Authors: http://192.168.100.5/app-authors
- App Books: http://192.168.100.5/app-books
- Métricas (Prometheus scrape):
    - Authors: http://192.168.100.5/app-authors/metrics
    - Books: http://192.168.100.5/app-books/metrics

##  Grafana
- ID Postgres (Datasource): 9628
- Dashboard: Helidon MP 4.x - MicroProfile Metrics
- UID: helidon-mp-basic

##  Dashboard (JSON)
```json
{
  "uid": "helidon-mp-basic",
  "title": "Helidon MP 4.x - MicroProfile Metrics",
  "timezone": "browser",
  "schemaVersion": 38,
  "version": 1,
  "refresh": "5s",
  "panels": [
    {
      "id": 1,
      "type": "timeseries",
      "title": "Requests por microservicio",
      "gridPos": { "x": 0, "y": 0, "w": 12, "h": 8 },
      "targets": [
        {
          "expr": "sum by (job) (requests_count_total)",
          "legendFormat": "{{job}}",
          "refId": "A"
        }
      ]
    },
    {
      "id": 2,
      "type": "timeseries",
      "title": "Heap usado (bytes)",
      "gridPos": { "x": 12, "y": 0, "w": 12, "h": 8 },
      "targets": [
        {
          "expr": "memory_usedHeap_bytes",
          "legendFormat": "{{job}}",
          "refId": "A"
        }
      ]
    },
    {
      "id": 3,
      "type": "timeseries",
      "title": "Threads activos",
      "gridPos": { "x": 0, "y": 8, "w": 12, "h": 8 },
      "targets": [
        {
          "expr": "thread_count",
          "legendFormat": "{{job}}",
          "refId": "A"
        }
      ]
    },
    {
      "id": 4,
      "type": "timeseries",
      "title": "GC total (colecciones)",
      "gridPos": { "x": 12, "y": 8, "w": 12, "h": 8 },
      "targets": [
        {
          "expr": "sum by (name) (gc_total)",
          "legendFormat": "{{name}}",
          "refId": "A"
        }
      ]
    }
  ],
  "time": { "from": "now-15m", "to": "now" },
  "templating": { "list": [] }
}
