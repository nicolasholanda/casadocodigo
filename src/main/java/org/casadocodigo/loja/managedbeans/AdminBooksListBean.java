package org.casadocodigo.loja.managedbeans;

import org.casadocodigo.loja.dao.BookDAO;
import org.casadocodigo.loja.model.Book;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model
public class AdminBooksListBean {
    @Inject
    private BookDAO bookDAO;
    private List<Book> books = new ArrayList<Book>();

    @PostConstruct
    private void loadObjects() {
        this.books = bookDAO.list();
    }

}
