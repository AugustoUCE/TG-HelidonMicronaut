package uce.edu.ec.config;

import io.vertx.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class VertxProducer {

    @Produces
    public Vertx vertx() {
        return Vertx.vertx();
    }
}
