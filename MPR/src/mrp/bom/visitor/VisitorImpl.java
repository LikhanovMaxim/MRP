package mrp.bom.visitor;

import mrp.bom.Component;

/**
 * @author Maksim_Likhanov
 */
public class VisitorImpl implements Visitor {

    public void visit(Component... components) {
        for (Component component : components) {
            component.accept(this);
        }
    }

    @Override
    public void visitComposite() {

    }

    @Override
    public void visitMaterial() {

    }
}
