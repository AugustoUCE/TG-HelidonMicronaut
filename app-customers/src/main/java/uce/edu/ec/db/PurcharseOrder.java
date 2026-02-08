package uce.edu.ec.db;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;
import java.time.LocalDate;

@MappedEntity(value = "purcharse_order")
@Serdeable
public class PurcharseOrder {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    private Integer id;

    @Nullable
    private LocalDate placedOn;

    @Nullable
    private LocalDate deliveredOn;

    @Nullable
    private BigDecimal total;

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    @Nullable
    private Customer customer;

    public PurcharseOrder() {
    }

    public PurcharseOrder(Integer id, LocalDate placedOn, LocalDate deliveredOn, BigDecimal total, Customer customer) {
        this.id = id;
        this.placedOn = placedOn;
        this.deliveredOn = deliveredOn;
        this.total = total;
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getPlacedOn() {
        return placedOn;
    }

    public void setPlacedOn(LocalDate placedOn) {
        this.placedOn = placedOn;
    }

    public LocalDate getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(LocalDate deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}