package mrp.bom.builder;

import mrp.bom.Material;
import org.json.JSONObject;

public class MaterialDirector {
	public Material constructMaterialFromJSON(JSONObject item) {
		MaterialJSONBuilder builder = new MaterialJSONBuilder(item);
		builder.setName();
		return builder.getResult();
	}
}
