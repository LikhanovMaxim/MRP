package mrp.bom.visitor;

/**
 * @author Maksim_Likhanov
 */
public interface Visitor {
	public void visitComposite();
	public void visitMaterial();
}
