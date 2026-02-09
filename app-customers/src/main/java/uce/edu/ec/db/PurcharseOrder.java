package uce.edu.ec.db;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@MappedEntity(value = "purcharse_order")
@Serdeable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PurcharseOrder {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    @AutoPopulated
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