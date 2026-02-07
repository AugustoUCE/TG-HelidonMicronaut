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
        System.out.println("========================================");
        System.out.println("Iniciando migraciones Flyway...");
        System.out.println("DB URL: " + dbUrl);
        System.out.println("========================================");

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(dbUrl, dbUser, dbPassword)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .cleanDisabled(false)
                    .load();


            // flyway.clean();

            int migrationsExecuted = flyway.migrate().migrationsExecuted;

            System.out.println("Migraciones ejecutadas: " + migrationsExecuted);
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("========================================");
            System.err.println("ERROR");
            System.err.println("   Mensaje: " + e.getMessage());
            System.err.println("========================================");
            e.printStackTrace();
            throw new RuntimeException("Fallo en migraciones flyway en DB", e);
        }
    }
}