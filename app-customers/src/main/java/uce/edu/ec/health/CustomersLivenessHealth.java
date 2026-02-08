package uce.edu.ec.health;

import io.micronaut.context.annotation.Requires;
import io.micronaut.health.HealthStatus;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Map;

@Singleton
@Requires(property = "endpoints.health.enabled", notEquals = "false")
public class CustomersLivenessHealth implements HealthIndicator {

    @Override
    public Publisher<HealthResult> getResult() {
        return Mono.fromCallable(() ->
                HealthResult.builder("customers-liveness", HealthStatus.UP)
                        .details(Map.of("app-customers", "vive"))
                        .build()
        );
    }
}