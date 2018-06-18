package mrp.active.record;

import org.json.JSONObject;

public interface ActiveRecord<T> {
	int save();

	JSONObject getTable();
}
