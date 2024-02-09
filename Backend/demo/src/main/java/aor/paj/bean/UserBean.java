package aor.paj.bean;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import aor.paj.dto.User;
import aor.paj.dto.Task;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

@ApplicationScoped
public class UserBean {

    @Inject
    SessionBean sessionBean;

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

        u.setId(generateId());
        u.setTasks(new ArrayList<>());


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

    //add task to the first user in the list of users
    public void addUserTask(Task t) {

        t.setId(generateTaskId());

        t.setStatus(100);

        sessionBean.getUserLogged().getTasks().add(t);

        writeIntoJsonFile();
    }

    //generate a unique id for tasks checking if the id already exists
    public int generateTaskId() {
        int id = 1;
        boolean idAlreadyExists;
        do {
            idAlreadyExists = false;
            for (User user : users) {
                for (Task task : user.getTasks()) {
                    if (task.getId() == id) {
                        id++;
                        idAlreadyExists = true;
                        break;
                    }
                }
            }
        } while (idAlreadyExists);
        return id;
    }

    //Receives the id of the user and the id of the task and removes the task from the user
    public boolean removeTask(int userId, int taskId) {
        for (User u : users) {
            if (u.getId() == userId) {
                for (Task t : u.getTasks()) {
                    if (t.getId() == taskId) {
                        u.getTasks().remove(t);
                        writeIntoJsonFile();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean updateTaskStatus(int userId, int taskId, int status) {
        for (User u : users) {
            if (u.getId() == userId) {
                for (Task t : u.getTasks()) {
                    if (t.getId() == taskId) {
                        t.setStatus(status);
                        writeIntoJsonFile();
                        return true;
                    }
                }
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
