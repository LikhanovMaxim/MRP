package mrp;

import mrp.bom.Component;
import mrp.bom.Material;

public class App {
    public static void main(String[] args) {
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

//        mrp.bom.Component room = mrp.bom.Component.find("0");
        System.out.println(room.stringify());
    }
}
