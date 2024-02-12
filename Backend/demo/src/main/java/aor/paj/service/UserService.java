package aor.paj.service;

import java.util.List;

import aor.paj.bean.UserBean;
import aor.paj.dto.User;
import aor.paj.responses.ResponseMessage;
import aor.paj.utils.JsonUtils;
import aor.paj.validator.UserValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userBean.getUsers();
    }

    //Serviço que possivelmente não será utilizado nesta fase 2
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(@QueryParam("id") int id) {
        userBean.removeUser(id);
        return Response.status(200).entity(JsonUtils.convertObjectToJson(new ResponseMessage("User is deleted"))).build();
    }

    //Service that receives a user object and adds it to the list of users
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User u) {
        // Check if any parameter is null or blank
        if (isNullOrBlank(u.getUsername()) || isNullOrBlank(u.getPassword()) || isNullOrBlank(u.getEmail())
                || isNullOrBlank(u.getFirstname()) || isNullOrBlank(u.getLastname())
                || isNullOrBlank(u.getPhone()) || isNullOrBlank(u.getPhotoURL())) {
            return Response.status(400).entity(JsonUtils.convertObjectToJson(new ResponseMessage("One or more parameters are null or blank"))).build();
        }

        // Validate email format
        if (!UserValidator.isValidEmail(u.getEmail())) {
            return Response.status(400).entity(JsonUtils.convertObjectToJson(new ResponseMessage("Invalid email format"))).build();
        }

        // Validate phone number format
        if (!UserValidator.isValidPhoneNumber(u.getPhone())) {
            return Response.status(400).entity(JsonUtils.convertObjectToJson(new ResponseMessage("Invalid phone number format"))).build();
        }

        // Validate URL format
        if (!UserValidator.isValidURL(u.getPhotoURL())) {
            return Response.status(400).entity(JsonUtils.convertObjectToJson(new ResponseMessage("Invalid URL format"))).build();
        }

        // Check if username or email already exists
        if (UserValidator.userExists(userBean.getUsers(), u.getUsername())) {
            return Response.status(409).entity(JsonUtils.convertObjectToJson(new ResponseMessage("Invalid Username"))).build();
        }
        if (UserValidator.emailExists(userBean.getUsers(), u.getEmail())) {
            return Response.status(409).entity(JsonUtils.convertObjectToJson(new ResponseMessage("Email already exists"))).build();
        }
        // If all checks pass, add the user
        userBean.addUser(u);
        return Response.status(200).entity(JsonUtils.convertObjectToJson(new ResponseMessage("A new user is created"))).build();
    }

    //Service that manages the login of the user
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@HeaderParam("username") String username, @HeaderParam("password") String password) {
        if (UserValidator.isValidUser(userBean.getUsers(), username, password)) {
            return Response.status(200).entity(JsonUtils.convertObjectToJson(new ResponseMessage("Valid Login"))).build();
        }
        return Response.status(401).entity(JsonUtils.convertObjectToJson(new ResponseMessage("Invalid Login"))).build();
    }

    //Service that receives username and password and sends the user object
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@HeaderParam("username") String username, @HeaderParam("password") String password) {
        if (UserValidator.isValidUser(userBean.getUsers(), username, password)) {
            return Response.status(200).entity(userBean.getUser(username)).build();
        }
        return Response.status(401).entity(JsonUtils.convertObjectToJson(new ResponseMessage("Unauthorized"))).build();
    }

    // UPDATE FUNCTION
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@HeaderParam("username") String username, @HeaderParam("password") String password, User u) {
        if (UserValidator.isValidUser(userBean.getUsers(), username, password)) {
            if (UserValidator.allFieldsEmpty(u))
                return Response.status(406).entity("no changes were introduced").build();
            else {
                if (UserValidator.passwordIsBeingAltered(u)) { // checks if at least 1 of those 3 pw fields was inputed
                    if (UserValidator.passwordMismatches(u, userBean.getUser(username))) {
                        return Response.status(406).entity("password is wrong").build();
                    }
                    if (UserValidator.passwordBothNewEmpty(u)) {
                        return Response.status(406).entity("new and confirmation password are empty").build();
                    }
                    if (UserValidator.passwordBothNewMismatches(u)) {
                        return Response.status(406).entity("new and confirmation password do not match").build();
                    }
                    if (UserValidator.passwordOldEqualsNewMismatches(u)) {
                        return Response.status(406).entity("old and new password are same, unchanged").build();
                    }
                }
                // email verifications
                if (UserValidator.emailUnchanged(u, userBean.getUser(username))) {
                    return Response.status(406).entity("email unchanged").build();
                } else if (UserValidator.emailExists(userBean.getUsers(), u.getEmail())) {
                    return Response.status(406).entity("email already in use").build();
                }
                // first and last name
                if (UserValidator.firstnameUnchanged(u, userBean.getUser(username))) {
                    return Response.status(406).entity("first name unchanged").build();
                }
                if (UserValidator.lastnameUnchanged(u, userBean.getUser(username))) {
                    return Response.status(406).entity("last name unchanged").build();
                }
                // phone and photoURL --- refazer com iteração generalista pelos fields, e response para cada field
                if (UserValidator.phoneUnchanged(u, userBean.getUser(username))) {
                    return Response.status(406).entity("phone unchanged").build();
                }
                if (UserValidator.photourlUnchanged(u, userBean.getUser(username))) {
                    return Response.status(406).entity("photo url unchanged").build();
                }
            }

        }

        userBean.updateUser(u);
        return Response.status(200).entity("the user was edited successfully").build();
    }


    // Helper method to check if a string is null or blank
    private boolean isNullOrBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

}


