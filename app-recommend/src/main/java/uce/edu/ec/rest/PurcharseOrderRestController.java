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


@Controller("/orders")
public class PurcharseOrderRestController {

    private static final Logger LOG = LoggerFactory.getLogger(PurcharseOrderRestController.class);

    private final PurcharseOrderRepo purcharseOrderRepo;

    @Inject
    public PurcharseOrderRestController(PurcharseOrderRepo purcharseOrderRepo) {
        this.purcharseOrderRepo = purcharseOrderRepo;
    }


    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PurcharseOrder>> getAllOrders() {
        LOG.info("Obteniendo todas las órdenes de compra");
        List<PurcharseOrder> orders = StreamSupport
                .stream(purcharseOrderRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return HttpResponse.ok(orders);
    }


    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<PurcharseOrder> getOrderById(@PathVariable Integer id) {
        LOG.info("Obteniendo orden con ID: {}", id);
        Optional<PurcharseOrder> order = purcharseOrderRepo.findById(id);
        return order
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }


    @Get(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PurcharseOrder>> getOrdersByCustomerId(@PathVariable Integer customerId) {
        LOG.info("Obteniendo órdenes del cliente con ID: {}", customerId);
        List<PurcharseOrder> orders = purcharseOrderRepo.findByCustomerId(customerId);
        return HttpResponse.ok(orders);
    }


    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<PurcharseOrder> createOrder(@Body PurcharseOrder order) {
        LOG.info("Creando nueva orden de compra");
        PurcharseOrder saved = purcharseOrderRepo.save(order);
        return HttpResponse.created(saved);
    }


    @Put(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<PurcharseOrder> updateOrder(@PathVariable Integer id, @Body PurcharseOrder order) {
        LOG.info("Actualizando orden con ID: {}", id);
        
        Optional<PurcharseOrder> existingOrder = purcharseOrderRepo.findById(id);
        if (existingOrder.isEmpty()) {
            return HttpResponse.notFound();
        }
        
        PurcharseOrder toUpdate = existingOrder.get();
        toUpdate.setPlacedOn(order.getPlacedOn());
        toUpdate.setDeliveredOn(order.getDeliveredOn());
        toUpdate.setTotal(order.getTotal());
        toUpdate.setCustomer(order.getCustomer());
        
        PurcharseOrder updated = purcharseOrderRepo.update(toUpdate);
        return HttpResponse.ok(updated);
    }


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


    @Get(value = "/count", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Long> countOrders() {
        LOG.info("Contando órdenes");
        long count = purcharseOrderRepo.count();
        return HttpResponse.ok(count);
    }
}
