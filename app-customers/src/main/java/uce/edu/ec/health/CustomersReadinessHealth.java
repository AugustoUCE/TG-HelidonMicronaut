package uce.edu.ec.health;

import io.micronaut.context.annotation.Requires;
import io.micronaut.health.HealthStatus;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Custom readiness health check
 * Endpoints disponibles:
 * - /health
 * - /health/liveness
 * - /health/readiness
 */
@Singleton
@Requires(property = "endpoints.health.enabled", notEquals = "false")
public class CustomersReadinessHealth implements HealthIndicator {

    @Override
    public Publisher<HealthResult> getResult() {
        return Mono.fromCallable(() -> {
            boolean dbOk = true; // simula dependencia

            if (dbOk) {
                return HealthResult.builder("customers-readiness", HealthStatus.UP)
                        .details(Map.of("database", "OK"))
                        .build();
            }

            return HealthResult.builder("customers-readiness", HealthStatus.DOWN)
                    .details(Map.of("database", "ERROR"))
                    .build();
        });
    }
}