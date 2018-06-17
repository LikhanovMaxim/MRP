package mrp.trash;

import org.json.JSONObject;

public interface ActiveRecord<T> {
    void save();
    JSONObject getTable();
}
