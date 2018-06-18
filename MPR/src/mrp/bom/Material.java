package mrp.bom;

import org.json.JSONObject;

public class Material implements Transformer {
	public String name;

	public Material(String name) {
		this.name = name;
	}

	@Override
	public String stringify() {
		return name;
	}

	@Override
	public JSONObject toJSON() {
		JSONObject res = new JSONObject();
		res.put("name", this.name);

		return res;
	}
}
