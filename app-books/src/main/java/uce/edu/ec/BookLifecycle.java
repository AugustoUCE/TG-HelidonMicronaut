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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BookLifecycle {

    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "127.0.0.1")
    String consulHost;

    @Inject
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consulPort;

    @Inject
    @ConfigProperty(name = "server.port", defaultValue = "8030")
    Integer appPort;

    @Inject
    Vertx vertx;

    private String serviceId;
    private ConsulClient consulClient;

    // ---------- STARTUP ----------
    public void onStartup(@Observes @Initialized(ApplicationScoped.class) Object event) {
        System.out.println("========================================");
        System.out.println("***** BookLifecycle - INICIANDO *****");
        System.out.println("Registrando servicio en Consul...");
        System.out.println("========================================");

        try {
            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            this.consulClient = ConsulClient.create(vertx, options);

            serviceId = "app-books-" + UUID.randomUUID();
            String ipAddress = InetAddress.getLocalHost().getHostAddress();

            String urlCheck = String.format(
                    "http://%s:%d/health/live",
                    ipAddress,
                    appPort
            );

            CheckOptions checkOptions = new CheckOptions()
                    .setHttp(urlCheck)
                    .setInterval("10s")
                    .setDeregisterAfter("30s");

            List<String> tags = new ArrayList<>();
            tags.add("traefik.enable=true");
            tags.add("traefik.http.routers.books.rule=PathPrefix(`/app-books`)");
            tags.add("traefik.http.middlewares.books-stripprefix.stripPrefix.prefixes=/app-books");
            tags.add("traefik.http.routers.books.middlewares=books-stripprefix");

            ServiceOptions serviceOptions = new ServiceOptions()
                    .setName("app-books")
                    .setId(serviceId)
                    .setAddress(ipAddress)
                    .setPort(appPort)
                    .setCheckOptions(checkOptions)
                    .setTags(tags);

            consulClient.registerService(serviceOptions, ar -> {
                if (ar.succeeded()) {
                    System.out.println("========================================");
                    System.out.println("Servicio app-books registrado en Consul");
                    System.out.println("Service ID: " + serviceId);
                    System.out.println("========================================");
                } else {
                    System.err.println("========================================");
                    System.err.println(" ERROR registrando app-books en Consul");
                    System.err.println("   Mensaje: " + ar.cause().getMessage());
                    System.err.println("========================================");
                    ar.cause().printStackTrace();
                }
            });

        } catch (Exception e) {
            System.err.println("========================================");
            System.err.println(" ERROR FATAL en BookLifecycle.onStartup()");
            System.err.println("========================================");
            e.printStackTrace();
        }
    }

    // ---------- SHUTDOWN ----------
    public void onShutdown(@Observes @BeforeDestroyed(ApplicationScoped.class) Object event) {
        System.out.println("========================================");
        System.out.println("***** BookLifecycle - APAGANDO *****");
        System.out.println("Desregistrando servicio...");
        System.out.println("========================================");

        if (consulClient != null && serviceId != null) {
            consulClient.deregisterService(serviceId, ar -> {
                if (ar.succeeded()) {
                    System.out.println(" Servicio app-books desregistrado de Consul");
                    System.out.println("========================================");
                } else {
                    System.err.println(" ERROR desregistrando app-books");
                    System.err.println("   Mensaje: " + ar.cause().getMessage());
                    System.out.println("========================================");
                }
            });
        }
    }
}
