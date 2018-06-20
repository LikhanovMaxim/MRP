package mrp.bom;

import mrp.bom.visitor.Visitor;
import org.json.JSONObject;

public class Material implements Component { //Leaf
    public String name;
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

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

    @Override
    public void accept(Visitor visitor) {

    }
}
