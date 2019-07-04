package org.casadocodigo.loja.managedbeans.admin;

import org.casadocodigo.loja.dao.BookDAO;
import org.casadocodigo.loja.infra.FileSaver;
import org.casadocodigo.loja.infra.MessagesHelper;
import org.casadocodigo.loja.model.Book;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.servlet.http.Part;

@Model
public class AdminBooksBean {
    private Book product = new Book();

    private Part summary;

    @Inject
    private BookDAO bookDAO;

    @Inject
    private MessagesHelper messagesHelper;

    @Inject
    private FileSaver fileSaver;

    @Transactional
    public String save() {
        String summaryPath = fileSaver.write(summary);
        bookDAO.save(product);
        messagesHelper.addFlash(new FacesMessage("Livro gravado com sucesso!"));
        return "admin/livros/list?faces-redirect=true";
    }

    public Book getProduct() {
        return product;
    }

    public Part getSummary() {
        return summary;
    }

    public void setSummary(Part summary) {
        this.summary = summary;
    }
}