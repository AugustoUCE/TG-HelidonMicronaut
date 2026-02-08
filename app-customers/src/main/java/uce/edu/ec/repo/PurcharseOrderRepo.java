package uce.edu.ec.repo;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uce.edu.ec.db.PurcharseOrder;

import java.util.List;

@Repository
public interface PurcharseOrderRepo extends CrudRepository<PurcharseOrder, Integer> {

    List<PurcharseOrder> findByCustomerId(Integer customerId);
}