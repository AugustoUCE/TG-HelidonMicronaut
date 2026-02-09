package uce.edu.ec.db;

import java.util.ArrayList;
import java.util.List;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedEntity(value = "customer")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Serdeable
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