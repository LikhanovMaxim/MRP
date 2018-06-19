package mrp.bom;

import mrp.active.record.ActiveRecordImpl;
import mrp.active.record.ActiveRecord;
import mrp.bom.visitor.Visitor;
import org.json.JSONObject;

import java.util.HashMap;

public class Composite implements Component {
    private String name;
    private int preparationTime;
    private Integer id;
    private HashMap<Component, Integer> parts;
    private ActiveRecord activeRecord;

    public Composite(String name, int preparationTime) {
        this.name = name;
        this.preparationTime = preparationTime;
        parts = new HashMap<Component, Integer>();
        activeRecord = new ActiveRecordImpl();
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
            _id = activeRecord.save(this);
        } else {
            _id = this.id;
        }
        JSONObject res = new JSONObject();
        res.put("id", _id.toString());

        return res;
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
    public void accept(Visitor visitor) {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HashMap<Component, Integer> getParts() {
        return parts;
    }

    public void setParts(HashMap<Component, Integer> parts) {
        this.parts = parts;
    }

}
