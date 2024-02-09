package aor.paj.service;

import java.util.List;
import aor.paj.dto.Task;

import aor.paj.bean.UserBean;
import aor.paj.dto.Application;
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


    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userBean.getUsers();
    }

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

    //ADD TASK TO USER
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

    //LOGIN FUNCTION
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@HeaderParam("username") String username, @HeaderParam("password") String password) {
        if (!userBean.userExists(username)) {
            return Response.status(406).entity("username").build();
        } else if (userBean.userPasswordMatch(username, password)) {

            return Response.status(200).entity("done").build();
        } else {
            return Response.status(406).entity("password").build();
        }
    }


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

}
