CREATE TABLE book
(
    isbn    VARCHAR(255) NOT NULL,
    title   VARCHAR(255),
    price   DOUBLE PRECISION,
    version INTEGER,
    CONSTRAINT pk_book PRIMARY KEY (isbn)
);

insert into book (isbn, title, price, version) values ('978-3-16-148410-0', 'Cien Años de Soledad', 19.99, 1);
insert into book (isbn, title, price, version) values ('978-0-14-303995-2', 'La Casa de los Espíritus', 15.99,  1);
insert into book (isbn, title, price, version) values ('978-0-14243703-2', 'Ficciones', 12.99, 1);

