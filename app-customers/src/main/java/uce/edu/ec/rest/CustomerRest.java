package uce.edu.ec.rest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.transaction.Transactional;
import uce.edu.ec.db.Customer;
import uce.edu.ec.dto.CustomerDto;
import uce.edu.ec.dto.PurcharseOrderDto;
import uce.edu.ec.repo.CustomersRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/customers")
public class CustomerRest {

    private final CustomersRepo customersRepo;

    public CustomerRest(CustomersRepo customersRepo) {
        this.customersRepo = customersRepo;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = new ArrayList<>();
        customersRepo.findAll().forEach(customer -> {
            CustomerDto dto = CustomerDto.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .purcharseOrders(new ArrayList<>())
                    .build();
            customers.add(dto);
        });
        return HttpResponse.ok(customers);
    }

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    @Transactional
    public HttpResponse<CustomerDto> getCustomer(Integer id) {
        return customersRepo.findById(id)
                .map(customer -> {
                    List<PurcharseOrderDto> purcharseOrders = customer.getPurcharseOrders() != null
                            ? customer.getPurcharseOrders().stream()
                            .map(po -> PurcharseOrderDto.builder()
                                    .id(po.getId())
                                    .placedOn(po.getPlacedOn())
                                    .deliveredOn(po.getDeliveredOn())
                                    .total(po.getTotal())
                                    .build())
                            .collect(Collectors.toList())
                            : new ArrayList<>();

                    CustomerDto dto = CustomerDto.builder()
                            .id(customer.getId())
                            .name(customer.getName())
                            .email(customer.getEmail())
                            .purcharseOrders(purcharseOrders)
                            .build();

                    return HttpResponse.ok(dto);
                })
                .orElse(HttpResponse.notFound());
    }

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<CustomerDto> createCustomer(@Body CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());

        Customer saved = customersRepo.save(customer);

        CustomerDto responseDto = CustomerDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .purcharseOrders(new ArrayList<>())
                .build();

        return HttpResponse.created(responseDto);
    }

    @Put(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<CustomerDto> updateCustomer(Integer id, @Body CustomerDto customerDto) {
        return customersRepo.findById(id)
                .map(customer -> {
                    customer.setName(customerDto.getName());
                    customer.setEmail(customerDto.getEmail());

                    Customer updated = customersRepo.update(customer);

                    CustomerDto responseDto = CustomerDto.builder()
                            .id(updated.getId())
                            .name(updated.getName())
                            .email(updated.getEmail())
                            .purcharseOrders(new ArrayList<>())
                            .build();

                    return HttpResponse.ok(responseDto);
                })
                .orElse(HttpResponse.notFound());
    }

    @Delete("/{id}")
    public HttpResponse<Void> deleteCustomer(Integer id) {
        return customersRepo.findById(id)
                .map(customer -> {
                    customersRepo.delete(customer);
                    return HttpResponse.<Void>noContent();
                })
                .orElse(HttpResponse.notFound());
    }
}