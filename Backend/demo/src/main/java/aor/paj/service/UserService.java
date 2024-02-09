package aor.paj.service;

import java.util.List;

import aor.paj.bean.SessionBean;
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

    @Inject
    SessionBean sessionBean;

    //Service that sends the list of all users
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userBean.getUsers();
    }

    //Service that receives a user object and adds it to the list of users
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User u) {
        if (userBean.userExists(u.getUsername())) {
            return Response.status(406).entity("username").build();
        }
        if (userBean.emailExists(u.getEmail())) {
            return Response.status(406).entity("mail").build();
        }
        System.out.println("pedido " + u.getUsername());
        System.out.println(" ");
        System.out.println("pedido " + u.getFirstname());
        System.out.println("pedido " + u.getLastname());
        userBean.addUser(u);
        return Response.status(200).entity("A new user is created").build();
    }

    //Service that receives a task object and adds it to the list of tasks of the user that is logged
    @PUT
    @Path("/addTask")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserTask(Task t) {
        System.out.println("pedido addtask titulo - " + t.getTitle());
        System.out.println("pedido addtask descricao - " + t.getDescription());
        System.out.println("pedido addtask prioridade - " + t.getPriority());
        System.out.println("pedido addtask data inicial - " + t.getInitialDate());
        System.out.println("pedido addtask data final - " + t.getFinalDate());

        userBean.addUserTask(t);
        return Response.status(200).entity("Task is added").build();
    }

    //Service that receives a task id and status number and updates the task status
    @PUT
    @Path("/updateTaskStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(@QueryParam("id") int id, @QueryParam("status") int status) {
        if(sessionBean.getUserLogged() == null){
            return Response.status(406).entity("User is not logged").build();
        } else if(userBean.updateTaskStatus(sessionBean.getUserLogged().getId(), id, status)){
            return Response.status(200).entity("Task is updated").build();
        } else {
            return Response.status(406).entity("Task is not found").build();
        }
    }

    //Service that manages the login of the user
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@HeaderParam("username") String username, @HeaderParam("password") String password) {
        if (!userBean.userExists(username)) {
            return Response.status(406).entity("username").build();
        } else if (userBean.userPasswordMatch(username, password)) {

            sessionBean.setUserLogged(userBean.getUser(username));
            return Response.status(200).entity("done").build();
        } else {
            return Response.status(406).entity("password").build();
        }
    }

    //Service that sends the list of tasks of the user that is logged
    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks() {
        if (sessionBean.getUserLogged() == null) {
            return Response.status(406).entity("User is not logged").build();
        } else {
            return Response.status(200).entity(sessionBean.getUserLogged().getTasks()).build();
        }
    }

    //Service 
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        User user = userBean.getUser(id);

        if (user == null)
            return Response.status(200).entity("User with this idea is not found").build();

        return Response.status(200).entity(user).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(@QueryParam("id") int id) {
        userBean.removeUser(id);
        return Response.status(200).entity("User is deleted").build();
    }

    //Service that receives a task id and deletes the whole task from the user that is logged
    @DELETE
    @Path("/deleteTask")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTask(@QueryParam("id") int id) {
        if(sessionBean.getUserLogged() == null){
            return Response.status(406).entity("User is not logged").build();
        } else if(userBean.removeTask(sessionBean.getUserLogged().getId(), id)){
            return Response.status(200).entity("Task is deleted").build();
        } else {
            return Response.status(406).entity("Task is not found").build();
        }
    }

}
