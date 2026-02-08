CREATE TABLE book_author
(
    books_isbn VARCHAR(255) NOT NULL,
    authors_id INTEGER NOT NULL,
    CONSTRAINT pk_book_author PRIMARY KEY (authors_id, books_isbn),
    CONSTRAINT fk_book_author_author
        FOREIGN KEY (authors_id) REFERENCES author (id),
    CONSTRAINT fk_book_author_book
        FOREIGN KEY (books_isbn) REFERENCES book (isbn)
);

INSERT INTO book_author (books_isbn, authors_id) VALUES
    ('978-3-16-148410-0', 1),
    ('978-0-14-303995-2', 2),
    ('978-0-14243703-2', 3);
