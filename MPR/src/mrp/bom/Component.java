package mrp.bom;

import org.json.JSONObject;

public interface Component {
	String stringify();

	JSONObject toJSON();
}
