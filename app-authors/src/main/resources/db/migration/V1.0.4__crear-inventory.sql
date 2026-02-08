CREATE TABLE inventory
(
    book_isbn VARCHAR(255) NOT NULL,
    sold INTEGER,
    supplied INTEGER,
    version INTEGER,
    CONSTRAINT pk_inventory PRIMARY KEY (book_isbn),
    CONSTRAINT fk_inventory_book FOREIGN KEY (book_isbn) REFERENCES book (isbn)
);