package uce.edu.ec.db;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedEntity("purcharse_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Serdeable
public class PurcharseOrder {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    private Long id;

    @Nullable
    private LocalDate placedOn;

    @Nullable
    private LocalDate deliveredOn;

    @Nullable
    private BigDecimal total;

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    @Nullable
    private Customer customer;
}