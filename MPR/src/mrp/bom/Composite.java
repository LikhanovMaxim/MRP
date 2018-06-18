package mrp.bom;

import mrp.bom.builder.Database;
import mrp.bom.builder.MaterialDirector;
import mrp.active.record.ActiveRecord;
import mrp.bom.visitor.Visitor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Composite implements Component, ActiveRecord {
	private String name;
	private int preparationTime;
	private Integer id;
	private HashMap<Component, Integer> parts;

	public Composite(String name, int preparationTime) {
		this.name = name;
		this.preparationTime = preparationTime;
		parts = new HashMap<Component, Integer>();
	}

	public Composite() {
	}

	@Override
	public String stringify() {
		StringBuilder res = new StringBuilder(String.format("\"%s\", Время изготовления: %s, ", name, preparationTime));
		for (Component part : parts.keySet()) {
			res.append(String.format("%dx[%s] ", parts.get(part), part.stringify()));
		}
		return res.toString();
	}

	@Override
	public JSONObject toJSON() {
		Integer _id;
		if (this.id == null) {
			_id = this.save();
		} else {
			_id = this.id;
		}
		JSONObject res = new JSONObject();
		res.put("id", _id.toString());

		return res;
	}

	@Override
	public void accept(Visitor visitor) {

	}

	public void add(Component item, Integer amount) {
		if (parts.get(item) != null) {
			parts.put(item, parts.get(item) + amount);
		} else {
			parts.put(item, amount);
		}
	}

	public void remove(Component part) {
		parts.remove(part);
	}

	@Override
	public int save() {
		boolean newly = false;
		if (id == null) {
			id = getTable().getInt("count");
			newly = true;
		}

		if (newly) {
			getTable().put("count", getTable().getInt("count") + 1);
		}

		JSONObject rawSelf = new JSONObject();
		rawSelf.put("name", this.name);
		rawSelf.put("preparation_time", this.preparationTime);

		JSONArray materials = new JSONArray();

		for (Component material : parts.keySet()) {
			JSONObject rawMaterial = material.toJSON();
			rawMaterial.put("amount", parts.get(material));
			materials.put(rawMaterial);
		}

		rawSelf.put("materials", materials);

		getTable().put(id.toString(), rawSelf);

		Database.writeCurrentDatabase();

		return id;
	}

	public JSONObject getTable() {
		return Database.getCurrentDatabase().getJSONObject("Composite");
	}

	public Composite find(String id) {
		JSONObject rawSelf = getTable().getJSONObject(id);

		String name = rawSelf.getString("name");
		int preparationTime = rawSelf.getInt("preparation_time");

		Composite res = new Composite(name, preparationTime);

		JSONArray materials = rawSelf.getJSONArray("materials");

		for (int i = 0; i < materials.length(); i++) {
			JSONObject item = materials.getJSONObject(i);
			if (isMaterial(item.opt("id"))) {//Это материал
				MaterialDirector director = new MaterialDirector();
				Material material = director.constructMaterialFromJSON(item);

				res.add(material, item.getInt("amount"));
			} else {//Это компонент
				Composite component = new Composite().find(item.getString("id"));
				res.add(component, item.getInt("amount"));
			}
		}

		return res;
	}

	private static boolean isMaterial(Object id) {
		return id == null;
	}
}
