package aor.paj.dto;

import aor.paj.bean.UserBean;
import jakarta.inject.Inject;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String photoURL;
    private ArrayList<Task> tasks;

    @Inject
    UserBean UserBean;

    public User() {
    }

    public User(int id, String username, String password, String email, String firstname, String lastname, String phone, String photoURL) {
        this.id = UserBean.generateId();
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.photoURL = photoURL;
        this.tasks = new ArrayList<Task>();
    }

    @XmlElement
    public int getId() {
        return id;
    }
    @XmlElement
    public String getUsername() {
        return username;
    }
    @XmlElement
    public String getPassword() {
        return password;
    }
    @XmlElement
    public String getEmail() {
        return email;
    }
    @XmlElement
    public String getFirstname() {
        return firstname;
    }
    @XmlElement
    public String getLastname() {
        return lastname;
    }
    @XmlElement
    public String getPhone() {
        return phone;
    }
    @XmlElement
    public String getPhotoURL() {
        return photoURL;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @XmlElement
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void addTask(Task t) {
        tasks.add(t);
    }

    public void removeTask(Task t) {
        tasks.remove(t);
    }

    public void removeTask(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                tasks.remove(t);
                return;
            }
        }
    }

    public Task getTask(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }


}
