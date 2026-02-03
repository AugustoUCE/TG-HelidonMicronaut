package uce.edu.ec.health;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class AuthorsReadinessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {

        boolean dependencyUp = true; // luego puedes cambiarlo

        if (dependencyUp) {
            return HealthCheckResponse.up("Dependencias OK");
        } else {
            return HealthCheckResponse.down("Dependencias CAÍDAS");
        }
    }
}
