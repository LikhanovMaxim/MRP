package mrp.bom.builder;

import mrp.bom.Material;

public interface MaterialBuilder {
	void reset();

	void setName();

	Material getResult();
}
