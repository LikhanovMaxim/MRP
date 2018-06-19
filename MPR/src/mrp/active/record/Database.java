package mrp.active.record;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Database {
	private static final String DATABASE_JSON = "database.json";
	private static JSONObject currentDatabase;

	public static JSONObject getCurrentDatabase() {
		if (currentDatabase == null) {
			Scanner sc = null;
			try {
				sc = new Scanner(new File(DATABASE_JSON));
				StringBuilder res = new StringBuilder();
				while (sc.hasNextLine()) {
					res.append(sc.nextLine());
				}
				sc.close();

				currentDatabase = new JSONObject(res.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				sc.close();
			}

		}
		return currentDatabase;
	}

	public static void writeCurrentDatabase()  {
		FileWriter file = null;
		try {
			file = new FileWriter(DATABASE_JSON);
			file.write(currentDatabase.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void setDefaultValue(String defaultValue)  {
		FileWriter file = null;
		try {
			file = new FileWriter(DATABASE_JSON);
			file.write(defaultValue);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
