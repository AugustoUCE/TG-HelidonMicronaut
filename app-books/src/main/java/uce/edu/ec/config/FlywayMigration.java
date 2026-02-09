package uce.edu.ec.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

@ApplicationScoped
public class FlywayMigration {

    @Inject
    @ConfigProperty(name = "javax.sql.DataSource.jdbc/postgres.dataSource.url")
    String dbUrl;

    @Inject
    @ConfigProperty(name = "javax.sql.DataSource.jdbc/postgres.dataSource.user")
    String dbUser;

    @Inject
    @ConfigProperty(name = "javax.sql.DataSource.jdbc/postgres.dataSource.password")
    String dbPassword;

    public void onStartup(@Observes @Initialized(ApplicationScoped.class) Object event) {
        // app-books NO ejecuta migraciones porque la BD es compartida con app-authors
        // app-authors es responsable de ejecutar todas las migraciones (V1.0.1, V1.0.2, V1.0.3, V1.0.4)
        System.out.println("========================================");
        System.out.println("app-books: Flyway desactivado - BD compartida con app-authors");
        System.out.println("========================================");
    }
}