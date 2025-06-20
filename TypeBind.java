public class TypeBind {
    private String id;
    private ASTType type;
    
    public TypeBind(String id, ASTType type) {
        this.id = id;
        this.type = type;
    }
    
    public String getId() {
        return id;
    }
    
    public ASTType getType() {
        return type;
    }
}