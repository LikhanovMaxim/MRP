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

	public static Composite find(String name, int preparationTime) {
		Composite result = composite.get(name);
		if (result == null) {
			result = new Composite(name, preparationTime);
			composite.put(name, result);
		}
		return result;
	}
}
