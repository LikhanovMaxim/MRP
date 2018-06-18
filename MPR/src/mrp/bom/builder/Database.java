package mrp.bom.builder;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Database {
    private static JSONObject currentDatabase;

    public static JSONObject getCurrentDatabase() {
        if (currentDatabase == null) {
            try {
                Scanner sc = new Scanner(new File("database.json"));
                StringBuilder res = new StringBuilder();
                while (sc.hasNextLine()) {
                    res.append(sc.nextLine());
                }
                sc.close();

                currentDatabase = new JSONObject(res.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return currentDatabase;
    }

    public static void writeCurrentDatabase() {
        try {
            FileWriter file = new FileWriter("database.json");
            file.write(currentDatabase.toString());
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setDefaultValue(String defaultValue) {
        try {
            FileWriter file = new FileWriter("database.json");
            file.write(defaultValue);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
