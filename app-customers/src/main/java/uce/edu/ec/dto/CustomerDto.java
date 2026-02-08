package uce.edu.ec.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;
import uce.edu.ec.db.PurcharseOrder;

import java.util.List;

@Serdeable
@Introspected
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CustomerDto {
    private Integer id;
    private String name;
    private String email;
    private List<uce.edu.ec.dto.PurcharseOrderDto> purcharseOrders;
}