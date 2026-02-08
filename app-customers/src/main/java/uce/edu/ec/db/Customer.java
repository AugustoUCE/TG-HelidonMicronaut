package uce.edu.ec.db;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;

import java.util.ArrayList;
import java.util.List;

@MappedEntity(value = "customer")
@Serdeable
public class Customer {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "customer")
    @Nullable
    private List<PurcharseOrder> purcharseOrders = new ArrayList<>();

    public Customer() {
    }

    public Customer(Integer id, String name, String email, List<PurcharseOrder> purcharseOrders) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.purcharseOrders = purcharseOrders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PurcharseOrder> getPurcharseOrders() {
        return purcharseOrders;
    }

    public void setPurcharseOrders(List<PurcharseOrder> purcharseOrders) {
        this.purcharseOrders = purcharseOrders;
    }
}