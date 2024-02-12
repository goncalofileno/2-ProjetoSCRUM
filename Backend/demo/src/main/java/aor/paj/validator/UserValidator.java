package aor.paj.validator;

import aor.paj.bean.UserBean;
import aor.paj.dto.User;
import jakarta.inject.Inject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    // Regular expressions for email, phone number, and URL validation
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^\\d{9}$";
    private static final String URL_REGEX = "^(http|https)://.*$";

    public static boolean isValidUser(List<User> users, String username, String password) {
        return userExists(users, username) && userPasswordMatch(users, username, password);
    }

    //Check if the user already exists with the same username
    public static boolean userExists(List<User> users, String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //Checks if the user and password match
    public static boolean userPasswordMatch(List<User> users, String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    //Check if the user already exists with the same email
    public static boolean emailExists(List<User> users, String email) {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    // Helper method to validate email format using regex
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Helper method to validate phone number format using regex
    public static boolean isValidPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    // Helper method to validate URL format using regex
    public static boolean isValidURL(String url) {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    //caso 0a: all fields sent were empty
    public static boolean allFieldsEmpty(User u) {
        boolean allEmpty=true;
        if(!u.getOldpassword().isEmpty())
            allEmpty=false;
        if(!u.getNewpassword().isEmpty())
            allEmpty=false;
        if(!u.getConfirmnewpassword().isEmpty())
            allEmpty=false;
        if(!u.getEmail().isEmpty())
            allEmpty=false;
        if(!u.getFirstname().isEmpty())
            allEmpty=false;
        if(!u.getLastname().isEmpty())
            allEmpty=false;
        if(!u.getPhone().isEmpty())
            allEmpty=false;
        if(!u.getPhotoURL().isEmpty())
            allEmpty=false;
        return allEmpty;
    }
    //caso 0b: if none of those 3 fields was changed, there was no password change request
    public static boolean passwordIsBeingAltered(User u) {
        boolean altered=false;
        if (!u.getOldpassword().isEmpty())
            altered=true;
        if (!u.getNewpassword().isEmpty())
            altered=true;
        if (!u.getConfirmnewpassword().isEmpty())
            altered=true;
        return altered;
    }

    //caso 1: password antiga nao corresponde com a guardada
    public static boolean passwordMismatches(User u, User realUser) {

        User found = realUser;

        if (!found.getPassword().equals(u.getOldpassword()))
            return true;
        return false;
    }
    //caso2a: as duas novas passwords estão vazias
    public static boolean passwordBothNewEmpty(User u) {
        if (u.getNewpassword().isBlank() && u.getConfirmnewpassword().isBlank())
            return true;
        return false;
    }

    //caso2b: as duas novas passwords não correspondem
    public static boolean passwordBothNewMismatches(User u) {
        if (!u.getNewpassword().equals(u.getConfirmnewpassword()))
            return true;
        return false;
    }

    //caso 3: password nova igual a antiga
    public static boolean passwordOldEqualsNewMismatches(User u) {
        if (u.getOldpassword().equals(u.getNewpassword()))
            return true;
        return false;
    }

    //Checks if email is the same as before
    public static boolean emailUnchanged(User u, User realUser) {
        User found = realUser;
        if (found.getEmail().equals(u.getEmail()))
            return true;
        return false;
    }

//    //Checks if email Belongs to another user
//    public boolean emailBelongsToAnother(User u) {
//        User found = findUser(u);
//
//        for (User i : users)
//        {
//            if (i==found) // skip verification with self
//                continue;
//            if (i.getEmail().equals(u.getEmail())) // verifies if anyone else has this email already
//                return true;
//        }
//        return false;
//    }
    //Checks if first name is the same as before
    public static boolean firstnameUnchanged(User u, User realUser) {
        User found = realUser;
        if (found.getFirstname().equals(u.getFirstname()))
            return true;
        return false;
    }
    //Checks if last name is the same as before
    public static boolean lastnameUnchanged(User u, User realUser) {
        User found = realUser;
        if (found.getLastname().equals(u.getLastname()))
            return true;
        return false;
    }
    //Checks if phone is the same as before
    public static boolean phoneUnchanged(User u, User realUser) {
        User found = realUser;
        if (found.getPhone().equals(u.getPhone()))
            return true;
        return false;
    }
    //Checks if phone is the same as before
    public static boolean photourlUnchanged(User u, User realUser) {
        User found = realUser;
        if (found.getPhotoURL().equals(u.getPhotoURL()))
            return true;
        return false;
    }

}
