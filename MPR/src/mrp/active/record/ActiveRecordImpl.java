package mrp.active.record;

import mrp.bom.Component;
import mrp.bom.Composite;
import org.json.JSONArray;
import org.json.JSONObject;

public class ActiveRecordImpl implements ActiveRecord {

    @Override
    public int save(Composite composite) {
        boolean newly = false;
        if (composite.getId() == null) {
            composite.setId(getTable().getInt("count"));
            newly = true;
        }

        if (newly) {
            getTable().put("count", getTable().getInt("count") + 1);
        }

        JSONObject rawSelf = new JSONObject();
        rawSelf.put("name", composite.getName());
        rawSelf.put("preparation_time", composite.getPreparationTime());

        JSONArray materials = new JSONArray();

        for (Component material : composite.getParts()) {
            JSONObject rawMaterial = material.toJSON();
            rawMaterial.put("amount", material.getAmount());
            materials.put(rawMaterial);
        }

        rawSelf.put("materials", materials);

        getTable().put(composite.getId().toString(), rawSelf);

        Database.writeCurrentDatabase();

        return composite.getId();
    }

    @Override
    public JSONObject getTable() {
        return Database.getCurrentDatabase().getJSONObject("Composite");
    }
}
