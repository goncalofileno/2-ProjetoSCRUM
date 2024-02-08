package aor.paj.dto;

public class Application {
    private User userLogged;

    public Application() {
    }

    public Application(User userLogged) {
        this.userLogged = userLogged;
    }

    public void setUserLogged(User userLogged) {
        this.userLogged = userLogged;
    }

    public User getUserLogged() {
        return userLogged;
    }

}
