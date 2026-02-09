package uce.edu.ec.rest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import uce.edu.ec.db.Customer;
import uce.edu.ec.dto.CustomerDto;
import uce.edu.ec.repo.CustomersRepo;

import java.util.ArrayList;
import java.util.List;

@Controller("/customers")
public class CustomerRest {

    private final CustomersRepo customersRepo;

    public CustomerRest(CustomersRepo customersRepo) {
        this.customersRepo = customersRepo;
    }

    // ----------------- GET ALL CUSTOMERS -----------------
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customersRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
        return HttpResponse.ok(customers);
    }

    // ----------------- SEARCH BY FIRST NAME -----------------
    @Get(value = "/search", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<CustomerDto>> searchByFirstName(@QueryValue("firstName") String firstName) {
        List<CustomerDto> customers = customersRepo.findByFirstName(firstName)
                .stream()
                .map(this::mapToDto)
                .toList();
        return HttpResponse.ok(customers);
    }

    // ----------------- HELPER METHOD -----------------
    private CustomerDto mapToDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .first_name(customer.getFirst_name())
                .last_name(customer.getLast_Name())
                .email(customer.getEmail())
                .purcharseOrders(new ArrayList<>())
                .build();
    }
}
