package mrp.bom;

import mrp.active.record.ActiveRecord;
import mrp.active.record.ActiveRecordImpl;
import mrp.bom.visitor.Visitor;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Composite implements Component {
    private String name;
    private int preparationTime;
    private Integer id;
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    private List<Component> parts;
    private ActiveRecord activeRecord;

    public Composite(String name, int preparationTime) {
        this.name = name;
        this.preparationTime = preparationTime;
        parts = new LinkedList<>();
        activeRecord = new ActiveRecordImpl();
    }

    public Composite() {
    }

    @Override
    public String stringify() {
        StringBuilder res = new StringBuilder(String.format("\"%s\", Время изготовления: %s, ", name, preparationTime));
        for (Component part : parts) {
            res.append(String.format("%dx[%s] ", part.getAmount(), part.stringify()));
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
        item.setAmount(amount);
        parts.add(item);
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

    public List<Component> getParts() {
        return parts;
    }

    public void setParts(List<Component> parts) {
        this.parts = parts;
    }

}
