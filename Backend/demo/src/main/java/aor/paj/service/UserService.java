package aor.paj.service;

import java.util.List;

import aor.paj.dto.Task;

import aor.paj.bean.UserBean;
import aor.paj.dto.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Path;


@Path("/user")
public class UserService {

    @Inject
    UserBean userBean;

    //Service that sends the list of all users
    //Serviço que possivelmente não será utilizado nesta fase 2
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userBean.getUsers();
    }

    //Serviço que possivelmente não será utilizado nesta fase 2
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(@QueryParam("id") int id) {
        userBean.removeUser(id);
        return Response.status(200).entity("User is deleted").build();
    }

    //Service that receives a user object and adds it to the list of users
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User u) {
        if (userBean.userExists(u.getUsername())) {
            return Response.status(409).entity("username").build();
        }
        if (userBean.emailExists(u.getEmail())) {
            return Response.status(409).entity("mail").build();
        }
        userBean.addUser(u);
        return Response.status(200).entity("A new user is created").build();
    }

    //Service that receives a task object and adds it to the list of tasks of the user that is logged
    @POST
    @Path("/addTask")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserTask(@HeaderParam("username") String username, @HeaderParam("password") String password, Task t) {
        if (isValidUser(username, password)) {
            if (isValidTask(t)) {
                userBean.addTask(username, t);
                return Response.status(200).entity("Task is added").build();
            } else {
                return Response.status(400).entity("Invalid task").build();
            }
        } else {
            if (username == null || password == null) {
                return Response.status(401).entity("Unauthorized").build();
            } else {
                return Response.status(403).entity("Invalid Credentials").build();
            }
        }
    }

    //Service that receives a task id and status number and updates the task status
    @PUT
    @Path("/updateTaskStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(@HeaderParam("username") String username, @HeaderParam("password") String password, @QueryParam("id") int id, @QueryParam("status") int status) {
        if (isValidUser(username, password) && userBean.taskBelongsToUser(username, id)) {
            userBean.updateTaskStatus(username, id, status);
            return Response.status(200).entity("Task is updated").build();
        } else {
            return getResponse(username, password, id);
        }
    }

    //Service that manages the login of the user
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@HeaderParam("username") String username, @HeaderParam("password") String password) {
        if (isValidUser(username, password)) {
            return Response.status(200).entity("done").build();
        }
        return Response.status(401).entity("invalid data").build();
    }

    //Service that sends the list of tasks of the user that is logged
    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks(@HeaderParam("username") String username, @HeaderParam("password") String password) {
        if (isValidUser(username, password)) {
            return Response.status(200).entity(userBean.getUser(username).getTasks()).build();
        } else {
            return Response.status(401).entity("Unauthorized").build();
        }
    }

    //Service 
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        if (userBean.getUser(id) == null) {
            return Response.status(404).entity("User not found").build();
        } else {
            return Response.status(200).entity(userBean.getUser(id)).build();
        }
    }

    //Service that receives a task id and deletes the whole task from the user that is logged
    @DELETE
    @Path("/deleteTask")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTask(@HeaderParam("username") String username, @HeaderParam("password") String password, @QueryParam("id") int id) {
        if (isValidUser(username, password) && userBean.taskBelongsToUser(username, id)) {
            userBean.removeTask(username, id);
            return Response.status(200).entity("Task is deleted").build();
        } else {
            return getResponse(username, password, id);
        }
    }

    public Response getResponse(@HeaderParam("username") String username, @HeaderParam("password") String password, @QueryParam("id") int id) {
        if (username == null || password == null) {
            return Response.status(401).entity("Unauthorized").build();
        } else if (!userBean.taskBelongsToUser(username, id)) {
            return Response.status(403).entity("Forbidden").build();
        } else {
            return Response.status(401).entity("Invalid Credentials").build();
        }
    }


    public boolean isValidUser(String username, String password) {
        return userBean.userExists(username) && userBean.userPasswordMatch(username, password);
    }

    //function that verifys if atributes of a new task are not null or empty, then verifys if initial date is before final date
    public boolean isValidTask(Task t) {
        return t != null && t.getTitle() != null && !(t.getTitle().isBlank()) && t.getDescription() != null && !(t.getDescription().isBlank()) && t.getPriority() != 0 && (t.getPriority() == 100 || t.getPriority() == 200 || t.getPriority() == 300) && t.getInitialDate() != null && t.getFinalDate() != null && t.getInitialDate().isBefore(t.getFinalDate());

    }
}


