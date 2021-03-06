package mrp.bom.builder;

import mrp.active.record.ActiveRecord;
import mrp.active.record.ActiveRecordImpl;
import mrp.bom.Composite;
import org.json.JSONArray;
import org.json.JSONObject;

public class CompositeJSONBuilder implements Builder {
	private static final String ID_ = "id";
	private static final String AMOUNT = "amount";
	private static final String NAME = "name";
	private static final String MATERIALS = "materials";
	private static final String PREPARATION_TIME = "preparation_time";

	private String id;
	private Composite res;
	private ActiveRecord activeRecord;

	public CompositeJSONBuilder() {
	}

	public CompositeJSONBuilder(String id) {
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

		int preparationTime = rawSelf.getInt(PREPARATION_TIME);

		res = new Composite(rawSelf.getString(NAME), preparationTime);

		JSONArray materials = rawSelf.getJSONArray(MATERIALS);

		for (int i = 0; i < materials.length(); i++) {
			JSONObject item = materials.getJSONObject(i);
			if (isMaterial(item.opt(ID_))) {
				res.add(MaterialFlyweight.find(item.getString(NAME)), item.getInt(AMOUNT));
			} else {//Это композит
				res.add(CompositeFlyweight.find(getFindingName(item), item.getString(ID_)), item.getInt(AMOUNT));
			}
		}
	}

	private String getFindingName(JSONObject item) {
		return activeRecord.getTable().getJSONObject(item.getString(ID_)).getString(NAME);
	}

	private static boolean isMaterial(Object id) {
		return id == null;
	}

	public Composite getResult() {
		return res;
	}
}
