package org.casadocodigo.loja.managedbeans;

import org.casadocodigo.loja.dao.AuthorDAO;
import org.casadocodigo.loja.dao.BookDAO;
import org.casadocodigo.loja.model.Author;
import org.casadocodigo.loja.model.Book;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Model
public class AdminBooksBean {
    private Book product = new Book();
    private List<Integer> selectedAuthorsIds = new ArrayList<>();
    private List<Author> authors = new ArrayList<Author>();

    @Inject
    private BookDAO bookDAO;
    @Inject
    private AuthorDAO authorDAO;

    @PostConstruct
    public void loadObjects() {
        this.authors = authorDAO.list();
    }

    @Transactional
    public void save() {
        populateBookAuthor();
        bookDAO.save(product);
        clearObjects();
    }

    private void clearObjects() {
        this.product = new Book();
        this.selectedAuthorsIds.clear();
    }

    public List<Author> getAuthors() {
        return authors;
    }

    private void populateBookAuthor() {
        selectedAuthorsIds.stream().map(id -> new Author(id)).forEach(a -> product.add(a));
    }

    public Book getProduct() {
        return product;
    }

    public List<Integer> getSelectedAuthorsIds() {
        return selectedAuthorsIds;
    }

    public void setSelectedAuthorsIds(List<Integer> selectedAuthors) {
        this.selectedAuthorsIds = selectedAuthors;
    }
}