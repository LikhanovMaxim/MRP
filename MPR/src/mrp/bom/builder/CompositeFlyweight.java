package mrp.bom.builder;

import mrp.bom.Composite;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maksim_Likhanov
 */
public class CompositeFlyweight {

	private static Map<String, Composite> composite;

	static {
		composite = new HashMap<>();
	}

	public static Composite find(String name, String id) {
		Composite result = composite.get(name);
		if (result == null) {
			CompositeJSONBuilder builder = new CompositeJSONBuilder(id);
			builder.build();
			result = builder.getResult();
			composite.put(result.getName(), result);
		}
		return result;
	}
}
