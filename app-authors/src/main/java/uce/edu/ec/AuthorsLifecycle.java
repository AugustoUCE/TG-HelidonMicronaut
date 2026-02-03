package uce.edu.ec;

import io.vertx.core.Vertx;
import io.vertx.ext.consul.CheckOptions;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.BeforeDestroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AuthorsLifecycle {

    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "127.0.0.1")
    String consulHost;

    @Inject
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consulPort;

    @Inject
    @ConfigProperty(name = "server.port", defaultValue = "8080")
    Integer appPort;

    @Inject
    Vertx vertx;

    private String serviceId;
    private ConsulClient consulClient;

    public void onStartup(@Observes @Initialized(ApplicationScoped.class) Object event) {
        System.out.println("========================================");
        System.out.println("***** AuthorsLifecycle - INICIANDO *****");
        System.out.println("Registrando servicio en Consul...");
        System.out.println("========================================");

        try {
            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            this.consulClient = ConsulClient.create(vertx, options);

            serviceId = "app-authors-" + UUID.randomUUID().toString();
            String ipAddress = InetAddress.getLocalHost().getHostAddress();

            String urlCheck = String.format("http://%s:%d/health/live", ipAddress, appPort);

            System.out.println("📍 IP del servicio: " + ipAddress);
            System.out.println("🔌 Puerto: " + appPort);
            System.out.println("🏥 Health check URL: " + urlCheck);
            System.out.println("🔗 Consul: " + consulHost + ":" + consulPort);

            CheckOptions checkOptions = new CheckOptions()
                    .setHttp(urlCheck)
                    .setInterval("10s")
                    .setDeregisterAfter("30s");

            List<String> tags = new java.util.ArrayList<>();
            tags.add("traefik.enable=true");
            tags.add("traefik.http.routers.authors.rule=PathPrefix(`/app-authors`)");
            tags.add("traefik.http.middlewares.authors-stripprefix.stripPrefix.prefixes=/app-authors");
            tags.add("traefik.http.routers.authors.middlewares=authors-stripprefix");

            System.out.println("🏷️  Tags a registrar:");
            tags.forEach(tag -> System.out.println("   - " + tag));


            ServiceOptions serviceOptions = new ServiceOptions()
                    .setName("app-authors")
                    .setId(serviceId)
                    .setAddress(ipAddress)
                    .setPort(appPort)
                    .setCheckOptions(checkOptions)
                    .setTags(tags);

            consulClient.registerService(serviceOptions, ar -> {
                if (ar.succeeded()) {
                    System.out.println("========================================");
                    System.out.println("✅ Servicio app-authors registrado en Consul");
                    System.out.println("🆔 Service ID: " + serviceId);
                    System.out.println("========================================");
                } else {
                    System.err.println("========================================");
                    System.err.println("❌ ERROR registrando app-authors en Consul");
                    System.err.println("   Mensaje: " + ar.cause().getMessage());
                    System.err.println("========================================");
                    ar.cause().printStackTrace();
                }
            });

        } catch (Exception e) {
            System.err.println("========================================");
            System.err.println("❌ ERROR FATAL en AuthorsLifecycle.onStartup()");
            System.err.println("========================================");
            e.printStackTrace();
        }
    }

    public void onShutdown(@Observes @BeforeDestroyed(ApplicationScoped.class) Object event) {
        System.out.println("========================================");
        System.out.println("***** AuthorsLifecycle - APAGANDO *****");
        System.out.println("Desregistrando servicio...");
        System.out.println("========================================");

        if (consulClient != null && serviceId != null) {
            consulClient.deregisterService(serviceId, ar -> {
                if (ar.succeeded()) {
                    System.out.println("✅ Servicio app-authors desregistrado de Consul");
                    System.out.println("========================================");
                } else {
                    System.err.println("❌ ERROR desregistrando app-authors");
                    System.err.println("   Mensaje: " + ar.cause().getMessage());
                    System.err.println("========================================");
                }
            });
        }
    }
}