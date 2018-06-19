package mrp.bom.builder.trash;

import mrp.bom.Composite;
import mrp.bom.builder.Builder;

@Deprecated
public class CompositeJSONBuilder implements Builder {
    private Composite composite;

    public CompositeJSONBuilder(Composite composite) {
        this.composite = composite;
    }

    @Override
    public void reset() {

    }

    @Override
    public void build() {

    }

//    @Override
//    public Composite getResult() {
//        return null;
//    }
}
