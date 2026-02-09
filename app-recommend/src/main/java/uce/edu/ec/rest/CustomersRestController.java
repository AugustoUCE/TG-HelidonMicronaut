package uce.edu.ec.rest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uce.edu.ec.db.Customer;
import uce.edu.ec.repo.CustomersRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller("/customers")
public class CustomersRestController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomersRestController.class);

    private final CustomersRepo customersRepo;

    @Inject
    public CustomersRestController(CustomersRepo customersRepo) {
        this.customersRepo = customersRepo;
    }


    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Customer>> getAllCustomers() {
        LOG.info("Obteniendo todos los clientes");
        List<Customer> customers = StreamSupport
                .stream(customersRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return HttpResponse.ok(customers);
    }


    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Customer> getCustomerById(@PathVariable Integer id) {
        LOG.info("Obteniendo cliente con ID: {}", id);
        Optional<Customer> customer = customersRepo.findById(id);
        return customer
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }


    @Get(value = "/email/{email}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Customer> getCustomerByEmail(@PathVariable String email) {
        LOG.info("Buscando cliente con email: {}", email);
        Optional<Customer> customer = customersRepo.findByEmail(email);
        return customer
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }


    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Customer> createCustomer(@Body Customer customer) {
        LOG.info("Creando nuevo cliente: {}", customer.getEmail());
        
        if (customer.getEmail() != null && customersRepo.findByEmail(customer.getEmail()).isPresent()) {
            return HttpResponse.badRequest();
        }
        
        Customer saved = customersRepo.save(customer);
        return HttpResponse.created(saved);
    }


    @Put(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Customer> updateCustomer(@PathVariable Integer id, @Body Customer customer) {
        LOG.info("Actualizando cliente con ID: {}", id);
        
        Optional<Customer> existingCustomer = customersRepo.findById(id);
        if (existingCustomer.isEmpty()) {
            return HttpResponse.notFound();
        }
        
        Customer toUpdate = existingCustomer.get();
        toUpdate.setFirst_name(customer.getFirst_name());
        toUpdate.setLast_Name(customer.getLast_Name());
        toUpdate.setEmail(customer.getEmail());
        
        Customer updated = customersRepo.update(toUpdate);
        return HttpResponse.ok(updated);
    }


    @Delete(value = "/{id}")
    public HttpResponse<Void> deleteCustomer(@PathVariable Integer id) {
        LOG.info("Eliminando cliente con ID: {}", id);
        
        Optional<Customer> customer = customersRepo.findById(id);
        if (customer.isEmpty()) {
            return HttpResponse.notFound();
        }
        
        customersRepo.deleteById(id);
        return HttpResponse.noContent();
    }


    @Get(value = "/count", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Long> countCustomers() {
        LOG.info("Contando clientes");
        long count = customersRepo.count();
        return HttpResponse.ok(count);
    }
}
