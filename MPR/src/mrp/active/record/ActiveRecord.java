package mrp.active.record;

import mrp.bom.Composite;
import org.json.JSONObject;

public interface ActiveRecord<T> {
    int save(Composite composite);

    JSONObject getTable();
}
