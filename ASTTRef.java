public class ASTTRef implements ASTType {

    private ASTType type;

    public ASTTRef(ASTType type) {
        this.type = type;
    }
    
    public ASTType getType() {
        return type;
    }
    public String toStr() {
        return "ref<"+type.toStr()+">";
    }

}