package mrp;

import mrp.bom.Component;
import mrp.bom.Material;
import mrp.bom.builder.Database;
import org.json.JSONException;
import org.junit.Test;

public class AppTesting {

	private String str = "{\"Component\":{\"count\":0}}";

	@Test(expected = JSONException.class)
	public void test2() {
		Database.setDefaultValue(str);
		Component door = Component.find("0");
		System.out.println(door.stringify());
	}

	private Component getDoor() {
		Component door = new Component("Дверь", 5);

		Material hinge = new Material("Петля");
		Material handle = new Material("Ручка");
		Material veneer = new Material("Шпон");

		door.addPart(hinge, 2);
		door.addPart(handle, 1);
		door.addPart(veneer, 1);
		return door;
	}

	@Test
	public void test1() {
		Database.setDefaultValue(str);

		Component door = getDoor();

		door.save();

		find("0");
		//		"Дверца ДСП", Время изготовления: 5, 2x[Петля] 1x[Ручка] 1x[Шпон]
	}

	@Test
	public void test3() {
		Database.setDefaultValue(str);

		Component wall = new Component("Стена", 7);
		Component door = getDoor();
//		door.save();
		Material wallpaper = new Material("Обои");

		wall.addPart(door, 1);
		wall.addPart(wallpaper, 5);

		wall.save();


		System.out.println(wall.stringify());

		find("0");
		find("1");

	}

	private void find(String id) {
		Component result = Component.find(id);
		System.out.println("Result " + id);
		System.out.println(result.stringify());
	}
}