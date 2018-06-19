package mrp.bom.builder;


import mrp.bom.Material;

import java.util.HashMap;
import java.util.Map;

public class MaterialFlyweight {
    private static Map<String, Material> materials;

    static {
        materials = new HashMap<>();
    }

    public static Material find(String name) {
        Material result = materials.get(name);
        if (result == null) {
            result = new Material(name);
            materials.put(name, result);
        }
        return result;
    }
}



