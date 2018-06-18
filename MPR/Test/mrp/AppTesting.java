package mrp;

import mrp.bom.Composite;
import mrp.bom.Material;
import mrp.bom.builder.Database;
import org.json.JSONException;
import org.junit.Test;

public class AppTesting {

	private String str = "{\"Composite\":{\"count\":0}}";

	@Test(expected = JSONException.class)
	public void test2() {
		Database.setDefaultValue(str);
		Composite door = new Composite().find("0");
		System.out.println(door.stringify());
	}

	private Composite getDoor() {
		Composite door = new Composite("Дверь", 5);

		Material hinge = new Material("Петля");
		Material handle = new Material("Ручка");
		Material veneer = new Material("Шпон");

		door.add(hinge, 2);
		door.add(handle, 1);
		door.add(veneer, 1);
		return door;
	}

	@Test
	public void test1() {
		Database.setDefaultValue(str);

		Composite door = getDoor();

		door.save();

		find("0");
		//		"Дверца ДСП", Время изготовления: 5, 2x[Петля] 1x[Ручка] 1x[Шпон]
	}

	@Test
	public void test3() {
		Database.setDefaultValue(str);

		Composite wall = new Composite("Стена", 7);
		Composite door = getDoor();
//		door.save();
		Material wallpaper = new Material("Обои");

		wall.add(door, 1);
		wall.add(wallpaper, 5);

		wall.save();


		System.out.println(wall.stringify());

		find("0");
		find("1");

	}

	private void find(String id) {
		Composite result = new Composite().find(id);
		System.out.println("Result " + id);
		System.out.println(result.stringify());
	}
}