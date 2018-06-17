package mrp.bom.builder;

import mrp.bom.Material;
import org.json.JSONObject;

public class MaterialJSONBuilder implements MaterialBuilder {

    private Material material;
    private JSONObject rawMaterial;

    public MaterialJSONBuilder(JSONObject rawMaterial){
        this.rawMaterial = rawMaterial;
        reset();
    }

    @Override
    public void reset() {
        material = new Material("");
    }

    @Override
    public void setName() {
        material.name = rawMaterial.getString("name");
    }

    @Override
    public Material getResult() {
        return material;
    }
}
