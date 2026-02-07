package uce.edu.ec.db;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import uce.edu.ec.db.Book;


@Entity
@Table(name = "inventory")
@Getter
@Setter
@ToString(exclude = "book")
public class Inventory {
    @Id
    @OneToOne
    @JoinColumn(name = "book_isbn")
    private Book book;
    private Integer sold;
    private Integer supplied;

    private Integer version;

}