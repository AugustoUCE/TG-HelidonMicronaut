package uce.edu.ec.db;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="book_author")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthor {

    @EmbeddedId
    private BookAuthorId id;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name="authors_id",nullable=false)
    private Author author;


}
