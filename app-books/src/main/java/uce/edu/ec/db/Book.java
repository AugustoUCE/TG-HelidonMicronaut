package uce.edu.ec.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "book")
@Getter
@Setter
@ToString(exclude = {"inventory"})
public class Book {

    @Id
    private String isbn;

    private String title;

    private Double price;

    @OneToOne(mappedBy = "book")
    private Inventory inventory;

    private Integer version;
}