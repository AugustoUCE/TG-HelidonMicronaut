package uce.edu.ec.repo;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import uce.edu.ec.db.Author;

import java.util.List;

@ApplicationScoped
@Transactional
public class AuthorRepository {

    @PersistenceContext
    private EntityManager em;


    public List<Author> findAll() {
        return this.em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    public Author findById(Integer id) {
        return this.em.find(Author.class, id);
    }

    public List<Author> findByBook(String isbn) {
        return this.em.createQuery(
                        "SELECT ba.author FROM BookAuthor ba WHERE ba.id.bookIsbn = :isbn", Author.class)
                .setParameter("isbn", isbn)
                .getResultList();
    }

    public Author save(Author author) {
        em.persist(author);
        return author;
    }

}