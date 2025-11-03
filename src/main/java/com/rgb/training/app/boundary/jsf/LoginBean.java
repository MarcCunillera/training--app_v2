package com.rgb.training.app.boundary.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private String message;
    private boolean loggedIn = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String login() {
        if ("admin".equals(username) && "1234".equals(password)) {
            loggedIn = true;
            message = null;
            return "restClient.xhtml?faces-redirect=true";
        } else {
            message = "Usuari o contrasenya incorrectes!";
            return null;
        }
    }

    public String logout() {
        username = null;
        password = null;
        message = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }

    // Redirección si ya está logueado (login.xhtml)
    public void redirectIfLoggedIn() throws IOException {
        if (loggedIn) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("restClient.xhtml");
        }
    }

    public void handle404() throws IOException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (loggedIn) {
            ctx.getExternalContext().redirect("restClient.xhtml");
        } else {
            ctx.getExternalContext().redirect("index.xhtml");
        }
    }

}
