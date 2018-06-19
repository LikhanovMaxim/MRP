package mrp.bom.builder;

import mrp.active.record.ActiveRecord;
import mrp.active.record.ActiveRecordImpl;
import mrp.bom.Composite;
import mrp.bom.Material;
import org.json.JSONArray;
import org.json.JSONObject;

public class MaterialJSONBuilder implements Builder {
    private static final String ID = "id";
    private static final String AMOUNT = "amount";
    private static final String NAME = "name";
    private static final String MATERIALS = "materials";
    private static final String PREPARATION_TIME = "preparation_time";

    private String id;
    private Composite res;
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
    }

    @Override
    public void build() {
        JSONObject rawSelf = activeRecord.getTable().getJSONObject(id);

        String name = rawSelf.getString(NAME);
        int preparationTime = rawSelf.getInt(PREPARATION_TIME);

        res = new Composite(name, preparationTime);

        JSONArray materials = rawSelf.getJSONArray(MATERIALS);

        for (int i = 0; i < materials.length(); i++) {
            JSONObject item = materials.getJSONObject(i);
            if (isMaterial(item.opt(ID))) {
                Material material = MaterialFlyweight.find(item.getString(NAME));
                res.add(material, item.getInt(AMOUNT));
            } else {//Это композит
                MaterialJSONBuilder a = new MaterialJSONBuilder(item.getString(ID));
                a.build();
                Composite component = a.getResult();
                res.add(component, item.getInt(AMOUNT));
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
