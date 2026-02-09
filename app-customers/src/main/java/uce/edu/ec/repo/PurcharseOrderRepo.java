package uce.edu.ec.repo;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;
import uce.edu.ec.db.PurcharseOrder;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
@Repository
public interface PurcharseOrderRepo extends GenericRepository<PurcharseOrder, Long> {

    @Query("SELECT * FROM purcharse_order WHERE customer_id = :customerId")
    List<PurcharseOrder> findByCustomerId(Long customerId);
}