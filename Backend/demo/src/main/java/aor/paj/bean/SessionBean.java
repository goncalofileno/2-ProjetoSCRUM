package aor.paj.bean;

import aor.paj.dto.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SessionBean {
    private User userLogged = null;

    public SessionBean() {
    }

    public SessionBean(User userLogged) {
        this.userLogged = userLogged;
    }

    public User getUserLogged() {
        return userLogged;
    }

    public void setUserLogged(User userLogged) {
        this.userLogged = userLogged;
    }

}
