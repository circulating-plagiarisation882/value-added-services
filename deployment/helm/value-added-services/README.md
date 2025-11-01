# Value Added Services Helm Chart

Kitting, customization, and value-added services

## Installation

```bash
helm install value-added-services .
```

## Configuration

See `values.yaml` for all configuration options.

### Key Configuration

```yaml
image:
  repository: paklog/value-added-services
  tag: latest

service:
  port: 8111
  managementPort: 8081

resources:
  limits:
    cpu: 1000m
    memory: 1Gi
  requests:
    cpu: 500m
    memory: 512Mi

autoscaling:
  enabled: true
  minReplicas: 2
  maxReplicas: 10
```

### Database Configuration

MongoDB is enabled by default.



### Observability

- **Metrics**: Prometheus metrics available at `/actuator/prometheus`
- **Tracing**: OpenTelemetry traces sent to Tempo
- **Logging**: Structured logs sent to Loki

## Uninstallation

```bash
helm uninstall value-added-services
```

## Maintainers

- Paklog Team
