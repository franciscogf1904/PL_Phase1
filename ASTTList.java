public class ASTTList implements ASTType {
    private ASTType elt;

    public ASTTList(ASTType elt) {
        this.elt = elt;
    }

    public ASTType getElt() {
        return elt;
    }

    @Override
    public String toStr() {
        return "List<" + elt.toString() + ">";
    }
}
