package uce.edu.ec.repo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import uce.edu.ec.db.Book;
import java.util.List;

@ApplicationScoped
@Transactional
public class BookRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Book> findAll() {
        return this.em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public Book findByIsbn(String isbn) {
        return this.em.find(Book.class, isbn);
    }

    public Book save(Book book) {
        em.persist(book);
        return book;
    }
}