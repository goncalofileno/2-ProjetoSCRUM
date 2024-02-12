package aor.paj.utils;

import aor.paj.dto.User;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static JsonbConfig config = new JsonbConfig().withFormatting(true);
    private static Jsonb jsonb = JsonbBuilder.create(config);
    private static final String filename = "users.json";
    public static String convertObjectToJson(Object object) {
        return jsonb.toJson(object);
    }

    public static ArrayList<User> getUsers() {
        ArrayList<User> users;
        File f = new File(filename);
        if (f.exists()) {
            try {
                FileReader filereader = new FileReader(f);
                users = jsonb.fromJson(filereader, new ArrayList<User>() {
                }.getClass().getGenericSuperclass());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            users = new ArrayList<>();
        }
        return users;
    }

    public static void writeIntoJsonFile(List<User> users) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            jsonb.toJson(users, fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
