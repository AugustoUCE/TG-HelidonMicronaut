package uce.edu.ec.repo;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uce.edu.ec.db.Customer;

import java.util.Optional;

@Repository
public interface CustomersRepo extends CrudRepository<Customer, Integer> {

    Optional<Customer> findByEmail(String email);
}