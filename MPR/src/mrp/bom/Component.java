package mrp.bom;

import mrp.bom.builder.Database;
import mrp.bom.builder.MaterialDirector;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Component implements Transformer {
    public String name;
    public int preparationTime;
    public Integer id;

    public HashMap<Transformer, Integer> parts;

    public Component(String name, int preparationTime) {
        this.name = name;
        this.preparationTime = preparationTime;
        parts = new HashMap<Transformer, Integer>();
    }

    @Override
    public String stringify() {
        StringBuilder res = new StringBuilder(String.format("\"%s\", Время изготовления: %s, ", name, preparationTime));
        for (Transformer part : parts.keySet()){
            res.append(String.format("%dx[%s] ", parts.get(part), part.stringify()));
        }
        return res.toString();
    }

    @Override
    public JSONObject toJSON() {
        Integer _id;
        if(this.id == null){
            _id = this.save();
        }else{
            _id = this.id;
        }
        JSONObject res = new JSONObject();
        res.put("id",_id.toString());

        return res;
    }

    public void addPart(Transformer part, Integer amount){
        if(parts.get(part) != null) {
            parts.put(part, parts.get(part) + amount);
        }
        else{
            parts.put(part, amount);
        }
    }

    public void removePart(Transformer part){
        parts.remove(part);
    }

    public int save() {
        boolean newly = false;
        if(id == null){
            id = getTable().getInt("count");
            newly = true;
        }

        if (newly){
            getTable().put("count", getTable().getInt("count") + 1);
        }

        JSONObject rawSelf = new JSONObject();
        rawSelf.put("name", this.name);
        rawSelf.put("preparation_time", this.preparationTime);

        JSONArray materials = new JSONArray();

        for (Transformer material : parts.keySet()){
            JSONObject rawMaterial = material.toJSON();
            rawMaterial.put("amount", parts.get(material));
            materials.put(rawMaterial);
        }

        rawSelf.put("materials", materials);

        getTable().put(id.toString(), rawSelf);

        Database.writeCurrentDatabase();

        return id;
    }

    private static JSONObject getTable() {
        return Database.getCurrentDatabase().getJSONObject("Component");
    }

    public static Component find(String id){
        JSONObject rawSelf = getTable().getJSONObject(id);

        String name = rawSelf.getString("name");
        int preparationTime = rawSelf.getInt("preparation_time");

        Component res = new Component(name, preparationTime);

        JSONArray materials = rawSelf.getJSONArray("materials");

        for (int i = 0; i < materials.length(); i++){
            JSONObject item = materials.getJSONObject(i);
            if (item.opt("id") == null){//Это материал
                MaterialDirector director = new MaterialDirector();
                Material material = director.constructMaterialFromJSON(item);

                res.addPart(material, item.getInt("amount"));

            }else{//Это компонент
                Component component = Component.find(item.getString("id"));
                res.addPart(component, item.getInt("amount"));
            }
        }

        return res;
    }
}
