package mrp.bom;

import org.json.JSONObject;

public interface Transformer {
    String stringify();
    JSONObject toJSON();
}
