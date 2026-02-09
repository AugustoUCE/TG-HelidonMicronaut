package uce.edu.ec.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Introspected
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Serdeable
public class BookRecDto {

    private Integer id;
    private String titulo;
    private String isbn;
    private String editorial;
    private String descripcion;
}