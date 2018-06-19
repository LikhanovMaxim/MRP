package mrp.bom;

import mrp.bom.visitor.Visitor;
import org.json.JSONObject;

public interface Component {
    String stringify();

    JSONObject toJSON();

    public void accept(Visitor visitor);
}
