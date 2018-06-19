package mrp.bom.builder.trash;

import mrp.bom.builder.Builder;

@Deprecated
public class Director {

    private Builder builder;

    public Director() {
        this.builder = null;
    }

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void make() {
        builder.build();
    }

    public void changeBuilder(Builder newBuilder) {
        builder = newBuilder;
    }

}
