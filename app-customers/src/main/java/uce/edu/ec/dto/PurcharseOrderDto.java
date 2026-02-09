package uce.edu.ec.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Serdeable
@Introspected
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurcharseOrderDto {
    private Long id;
    private LocalDate placedOn;
    private LocalDate deliveredOn;
    private BigDecimal total;
}