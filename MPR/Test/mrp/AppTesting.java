package mrp;

import mrp.active.record.ActiveRecord;
import mrp.active.record.ActiveRecordImpl;
import mrp.active.record.Database;
import mrp.bom.Composite;
import mrp.bom.Material;
import mrp.bom.builder.Builder;
import mrp.bom.builder.CompositeJSONBuilder;
import org.json.JSONException;
import org.junit.Test;

public class AppTesting {

    private String str = "{\"Composite\":{\"count\":0}}";
    private ActiveRecord activeRecord = new ActiveRecordImpl();

    @Test(expected = JSONException.class)
    public void test2() {
        Database.setNullToJSONFile(str);
        Builder builder = new CompositeJSONBuilder("0");
        builder.build();
        ((CompositeJSONBuilder) builder).getResult();
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

    private Composite buildFromJSON(String id) {
        Builder builder = new CompositeJSONBuilder(id);
        builder.build();
        return ((CompositeJSONBuilder) builder).getResult();
    }

    private void buildAndPrint(String id) {
        Composite res = buildFromJSON(id);
        System.out.println("Result " + id);
        System.out.println(res.stringify());
    }

    @Test
    public void test1() {
        // prepare JSON file
        Database.setNullToJSONFile(str);
        Composite door = getDoor();
        activeRecord.save(door);
        // build by json file
        String id = "0";
        Composite res = buildFromJSON(id);
        System.out.println("Result " + id);
        System.out.println(res.stringify());
        //		"Дверца ДСП", Время изготовления: 5, 2x[Петля] 1x[Ручка] 1x[Шпон]
    }

    @Test
    public void test3() {
        // prepare JSON file
        Database.setNullToJSONFile(str);

        Composite wall = new Composite("Стена", 7);
        Composite door = getDoor();
        activeRecord.save(door);
        Material wallpaper = new Material("Обои");

        wall.add(door, 1);
        wall.add(wallpaper, 5);
        activeRecord.save(wall);

        // build by json file
        String id = "1";
        Composite res = buildFromJSON(id);
        System.out.println("Result " + id);
        System.out.println(res.stringify());
    }

    @Test
    public void testFlyweight() {
        // prepare JSON file
        Database.setNullToJSONFile(str);
        Composite room = new Composite("Комната", 10);
        for (int i = 0; i < 3; i++) {
            Composite door = getDoor();
            room.add(door, 4);
        }
        activeRecord.save(room);

        // build by json file
        String id = "0";
        Composite res = buildFromJSON(id);
        System.out.println("Result " + id);
        System.out.println(res.stringify());
    }
}