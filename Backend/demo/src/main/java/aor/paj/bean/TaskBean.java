package aor.paj.bean;

import aor.paj.dto.Task;
import aor.paj.dto.User;
import aor.paj.utils.JsonUtils;
import aor.paj.utils.State;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;

@ApplicationScoped
public class TaskBean {
    private ArrayList<User> users;

    public TaskBean() {
        this.users = JsonUtils.getUsers();
    }

    //Return the list of users in the json file
    public ArrayList<User> getUsers() {
        users = JsonUtils.getUsers();
        return users;
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

    //Receives the username and task id and removes the task from the user
    public boolean removeTask(String username, int taskId) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                for (Task t : u.getTasks()) {
                    if (t.getId() == taskId) {
                        u.getTasks().remove(t);
                        JsonUtils.writeIntoJsonFile(users);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Receives the username, task id and new status and updates the status of the task
    public boolean updateTaskStatus(String username, int taskId, int status) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                for (Task t : u.getTasks()) {
                    if (t.getId() == taskId) {
                        t.setStatus(status);
                        JsonUtils.writeIntoJsonFile(users);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Receives the username and task id and sees if task belongs to the user
    public boolean taskBelongsToUser(String username, int taskId) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                for (Task t : u.getTasks()) {
                    if (t.getId() == taskId) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Receives the username and task object and adds the task to the user
    public boolean addTask(String username, Task task) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                task.setId(generateTaskId());
                task.setStatus(State.TODO.getValue());
                u.getTasks().add(task);
                JsonUtils.writeIntoJsonFile(users);
                return true;
            }
        }
        return false;
    }

    //Function that returns the list of tasks of the user by username
    public ArrayList<Task> getTasks(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u.getTasks();
            }
        }
        return null;
    }


}
