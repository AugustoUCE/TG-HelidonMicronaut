package uce.edu.ec.repo;

import java.util.List;
import java.util.Optional;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;
import uce.edu.ec.db.Customer;
@JdbcRepository(dialect = Dialect.POSTGRES)
@Repository
public interface CustomersRepo extends GenericRepository<Customer, Long> {

    @Query("SELECT * FROM customer")
    List<Customer> findAll();
    
    @Query("SELECT * FROM customer WHERE first_name = :firstName")
    List<Customer> findByFirstName(String firstName);
}