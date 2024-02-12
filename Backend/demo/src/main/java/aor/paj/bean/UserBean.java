package aor.paj.bean;

import java.util.ArrayList;
import aor.paj.utils.JsonUtils;
import jakarta.enterprise.context.ApplicationScoped;
import aor.paj.dto.User;
import aor.paj.dto.Task;

@ApplicationScoped
public class UserBean {
    private ArrayList<User> users;

    public UserBean() {
        this.users = JsonUtils.getUsers();
    }

    //Add a user to the list of users
    public void addUser(User u) {

        System.out.println("addUser " + u.getFirstname());
        System.out.println("addUser " + u.getLastname());

        u.setId(generateId());
        u.setTasks(new ArrayList<>());
        u.setOldpassword("");
        u.setNewpassword("");
        u.setConfirmnewpassword("");

        users.add(u);
        JsonUtils.writeIntoJsonFile(users);
    }

    public int generateId() {
        // Inicializa o ID como 1
        int id = 1;
        boolean idAlreadyExists;
        // Verifica se o ID já existe na lista de usuários
        do {
            idAlreadyExists = false;
            for (User user : users) {
                if (user.getId() == id) {
                    // Se o ID já existe, incrementa o ID e redefine a flag para true
                    id++;
                    idAlreadyExists = true;
                    break;
                }
            }
        } while (idAlreadyExists); // Continua até encontrar um ID único

        return id;
    }

    public User getUser(int i) {
        for (User u : users) {
            if (u.getId() == i)
                return u;
        }
        return null;
    }

    //get the user by username
    public User getUser(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username))
                return u;
        }
        return null;
    }

   //Return the list of users in the json file
    public ArrayList<User> getUsers() {
        users = JsonUtils.getUsers();
        return users;
    }

    //Update a user to the list of users
    public void updateUser(User u) {
        System.out.println("Updating a user...");
        User found = getUser(u.getUsername());

        updateUserFields(found,u);

        JsonUtils.writeIntoJsonFile(users);
    }

    //Update the user Fields with the ones coming from the edit User Form, in case they aren't empty
    public void updateUserFields(User found,User u) {
        if (!u.getNewpassword().isEmpty())
            found.setPassword(u.getNewpassword());
        if (!u.getEmail().isEmpty())
            found.setEmail(u.getEmail());
        if (!u.getFirstname().isEmpty())
            found.setFirstname(u.getFirstname());
        if (!u.getLastname().isEmpty())
            found.setLastname(u.getLastname());
        if (!u.getPhone().isEmpty())
            found.setPhone(u.getPhone());
        if (!u.getPhotoURL().isEmpty())
            found.setPhotoURL(u.getPhotoURL());
    }

    public boolean removeUser(int id) {
        for (User u : users) {
            if (u.getId() == id) {
                users.remove(u);
                return true;
            }
        }
        return false;
    }
}
