package mrp.bom.builder;

import mrp.active.record.ActiveRecord;
import mrp.active.record.ActiveRecordImpl;
import mrp.bom.Composite;
import mrp.bom.Material;
import org.json.JSONArray;
import org.json.JSONObject;

public class MaterialJSONBuilder implements Builder {

    private Composite res;
    private String id;

    private ActiveRecord activeRecord;

    public MaterialJSONBuilder() {
    }

    public MaterialJSONBuilder(String id) {
        this.id = id;
        activeRecord = new ActiveRecordImpl();
        reset();
    }

    @Override
    public void reset() {
        res = new Composite();
//        material = new Material("");
    }

    @Override
    public void build() {
        JSONObject rawSelf = activeRecord.getTable().getJSONObject(id);

        String name = rawSelf.getString("name");
        int preparationTime = rawSelf.getInt("preparation_time");

        res = new Composite(name, preparationTime);

        JSONArray materials = rawSelf.getJSONArray("materials");

        for (int i = 0; i < materials.length(); i++) {
            JSONObject item = materials.getJSONObject(i);
            if (isMaterial(item.opt("id"))) {//Это материал
                Material material = MaterialFlyweight.find(item.getString("name"));
//                Material material = new Material(item.getString("name"));
                res.add(material, item.getInt("amount"));
            } else {//Это компонент
                MaterialJSONBuilder a = new MaterialJSONBuilder(item.getString("id"));
                a.build();
                Composite component = a.getResult();
                res.add(component, item.getInt("amount"));
            }
        }
    }

    private static boolean isMaterial(Object id) {
        return id == null;
    }

    public Composite getResult() {
        return res;
    }
}
