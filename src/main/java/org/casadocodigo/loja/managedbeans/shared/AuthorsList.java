package org.casadocodigo.loja.managedbeans.shared;

import org.casadocodigo.loja.dao.AuthorDAO;
import org.casadocodigo.loja.model.Author;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model
public class AuthorsList {
    @Inject
    private AuthorDAO authorDAO;
    private List<Author> authorList = new ArrayList<>();

    @PostConstruct
    private void loadObjects() {
        authorList = authorDAO.list();
    }

    public List<Author> get() {
        return authorList;
    }
}
