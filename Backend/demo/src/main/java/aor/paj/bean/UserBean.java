package aor.paj.bean;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import aor.paj.dto.User;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

@ApplicationScoped
public class UserBean {

    final String filename = "users.json";
    private ArrayList<User> users;

    public UserBean() {

        File f = new File(filename);
        if (f.exists()) {
            try {
                FileReader filereader = new FileReader(f);
                users = JsonbBuilder.create().fromJson(filereader, new ArrayList<User>() {
                }.getClass().getGenericSuperclass());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            users = new ArrayList<User>();
        }
    }

    //Add a user to the list of users
    public void addUser(User u) {
        System.out.println("addUser " + u.getFirstname());
        System.out.println("addUser " + u.getLastname());
        users.add(u);
        writeIntoJsonFile();
    }

    //Check if the user already exists with the same username
    public boolean userExists(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //Check if the user already exists with the same email
    public boolean emailExists(String email) {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    //Checks if the user and password match
    public boolean userPasswordMatch(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    //function to generate a integer id for the user, checking if the id already exists
    public int generateId() {
        int id = 0;
        for (User user : users) {
            if (user.getId() == id) {
                id++;
            }
        }
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

    public ArrayList<User> getUsers() {
        return users;
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

    private void writeIntoJsonFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            JsonbConfig config = new JsonbConfig().withFormatting(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            jsonb.toJson(users, fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
