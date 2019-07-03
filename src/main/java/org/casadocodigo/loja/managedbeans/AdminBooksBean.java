package org.casadocodigo.loja.managedbeans;

import org.casadocodigo.loja.dao.AuthorDAO;
import org.casadocodigo.loja.dao.BookDAO;
import org.casadocodigo.loja.infra.FileSaver;
import org.casadocodigo.loja.infra.MessagesHelper;
import org.casadocodigo.loja.model.Author;
import org.casadocodigo.loja.model.Book;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Part;

@Model
public class AdminBooksBean {
    private Book product = new Book();

    private List<Integer> selectedAuthorsIds = new ArrayList<>();

    private List<Author> authors = new ArrayList<Author>();

    private Part summary;

    @Inject
    private BookDAO bookDAO;

    @Inject
    private AuthorDAO authorDAO;

    @Inject
    private MessagesHelper messagesHelper;

    @Inject
    private FileSaver fileSaver;

    @PostConstruct
    public void loadObjects() {
        this.authors = authorDAO.list();
    }

    @Transactional
    public String save() {
        String summaryPath = fileSaver.write("summaries", summary);
        bookDAO.save(product);
        clearObjects();
        messagesHelper.addFlash(new FacesMessage("Livro gravado com sucesso!"));
        return "/livros/list?faces-redirect=true";
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

    public Part getSummary() {
        return summary;
    }

    public void setSummary(Part summary) {
        this.summary = summary;
    }
}