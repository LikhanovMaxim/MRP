package mrp;

import mrp.bom.Component;
import mrp.bom.Material;
import mrp.bom.builder.Database;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppTesting {

    private String str = "{\"Component\":{\"count\":0}}";

    @Test
    public void name() {
        Database.setDefaultValue(str);

        Component room = new Component("Комната", 5);
        Material handle = new Material("Обои");
        room.addPart(handle, 10);

        Component door = new Component("Дверь", 5);
        Material glass = new Material("Стекло");
        Material wood = new Material("Дерево");

        door.addPart(glass, 2);
        door.addPart(wood, 3);

        room.addPart(door,4);

        room.save();

        Component roomResult = Component.find("0");

        System.out.println(room.stringify());
        System.out.println("Room result");
        System.out.println(roomResult.stringify());

//        room.removePart();
    }

    @Test
    public void test() {
        Database.setDefaultValue(str);
        Component room = new Component("Комната", 5);
        Material handle = new Material("Обои");
        room.addPart(handle, 10);

        room.save();
        Component roomR = Component.find("0");
        System.out.println(room.stringify());
    }
}