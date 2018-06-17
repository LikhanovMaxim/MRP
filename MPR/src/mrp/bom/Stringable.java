package mrp.bom;

import org.json.JSONObject;

public interface Stringable {
    String stringify();
    JSONObject toJSON();
}
