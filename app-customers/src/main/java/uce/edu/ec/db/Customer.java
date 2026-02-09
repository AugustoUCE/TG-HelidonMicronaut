package uce.edu.ec.db;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@MappedEntity(value = "customer")
@Serdeable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    @AutoPopulated
    private Long id;
    @MappedProperty("first_name")
    private String first_name;
    @MappedProperty("last_name")
    private String last_Name;

    private String email;

    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "customer")
    @Nullable
    private List<PurcharseOrder> purcharseOrders = new ArrayList<>();


}