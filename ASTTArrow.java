public class ASTTArrow implements ASTType {
    public ASTType from;
    public ASTType to;

    public ASTTArrow(ASTType from, ASTType to) {
        this.from = from;
        this.to = to;
    }

    public ASTType getFrom() {
        return from;
    }

    public ASTType getTo() {
        return to;
    }

    public String toStr() {
        return from.toStr() + " -> " + to.toStr();
    }
}


