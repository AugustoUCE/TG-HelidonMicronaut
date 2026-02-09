package uce.edu.ec.rest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uce.edu.ec.db.PurcharseOrder;
import uce.edu.ec.repo.PurcharseOrderRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST Controller para gestión de Purchase Orders (Órdenes de Compra)
 * Proporciona CRUD completo y consultas por cliente
 */
@Controller("/orders")
public class PurcharseOrderRestController {

    private static final Logger LOG = LoggerFactory.getLogger(PurcharseOrderRestController.class);

    private final PurcharseOrderRepo purcharseOrderRepo;

    @Inject
    public PurcharseOrderRestController(PurcharseOrderRepo purcharseOrderRepo) {
        this.purcharseOrderRepo = purcharseOrderRepo;
    }

    /**
     * Obtener todas las órdenes
     * GET /orders
     */
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PurcharseOrder>> getAllOrders() {
        LOG.info("Obteniendo todas las órdenes de compra");
        List<PurcharseOrder> orders = StreamSupport
                .stream(purcharseOrderRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return HttpResponse.ok(orders);
    }

    /**
     * Obtener una orden por ID
     * GET /orders/{id}
     */
    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<PurcharseOrder> getOrderById(@PathVariable Integer id) {
        LOG.info("Obteniendo orden con ID: {}", id);
        Optional<PurcharseOrder> order = purcharseOrderRepo.findById(id);
        return order
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    /**
     * Obtener órdenes por ID de cliente
     * GET /orders/customer/{customerId}
     */
    @Get(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PurcharseOrder>> getOrdersByCustomerId(@PathVariable Integer customerId) {
        LOG.info("Obteniendo órdenes del cliente con ID: {}", customerId);
        List<PurcharseOrder> orders = purcharseOrderRepo.findByCustomerId(customerId);
        return HttpResponse.ok(orders);
    }

    /**
     * Crear una nueva orden
     * POST /orders
     */
    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<PurcharseOrder> createOrder(@Body PurcharseOrder order) {
        LOG.info("Creando nueva orden de compra");
        PurcharseOrder saved = purcharseOrderRepo.save(order);
        return HttpResponse.created(saved);
    }

    /**
     * Actualizar una orden existente
     * PUT /orders/{id}
     */
    @Put(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<PurcharseOrder> updateOrder(@PathVariable Integer id, @Body PurcharseOrder order) {
        LOG.info("Actualizando orden con ID: {}", id);
        
        Optional<PurcharseOrder> existingOrder = purcharseOrderRepo.findById(id);
        if (existingOrder.isEmpty()) {
            return HttpResponse.notFound();
        }
        
        // Actualizar los campos
        PurcharseOrder toUpdate = existingOrder.get();
        toUpdate.setPlacedOn(order.getPlacedOn());
        toUpdate.setDeliveredOn(order.getDeliveredOn());
        toUpdate.setTotal(order.getTotal());
        toUpdate.setCustomer(order.getCustomer());
        
        PurcharseOrder updated = purcharseOrderRepo.update(toUpdate);
        return HttpResponse.ok(updated);
    }

    /**
     * Eliminar una orden
     * DELETE /orders/{id}
     */
    @Delete(value = "/{id}")
    public HttpResponse<Void> deleteOrder(@PathVariable Integer id) {
        LOG.info("Eliminando orden con ID: {}", id);
        
        Optional<PurcharseOrder> order = purcharseOrderRepo.findById(id);
        if (order.isEmpty()) {
            return HttpResponse.notFound();
        }
        
        purcharseOrderRepo.deleteById(id);
        return HttpResponse.noContent();
    }

    /**
     * Obtener cantidad total de órdenes
     * GET /orders/count
     */
    @Get(value = "/count", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Long> countOrders() {
        LOG.info("Contando órdenes");
        long count = purcharseOrderRepo.count();
        return HttpResponse.ok(count);
    }
}
