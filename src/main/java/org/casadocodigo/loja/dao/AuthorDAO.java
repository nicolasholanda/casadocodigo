package org.casadocodigo.loja.dao;

import org.casadocodigo.loja.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class AuthorDAO {
    @PersistenceContext
    private EntityManager manager;

    public List<Author> list() {
        return manager.createQuery("SELECT a FROM Author a ORDER BY a.name ASC", Author.class).getResultList();
    }

    public void save(Author author) {
        manager.persist(author);
    }
}
