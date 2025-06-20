import java.util.Map;

public class ASTTStruct implements ASTType {
    private TypeBindList ll;  // This contains the field definitions

    public ASTTStruct(TypeBindList llp) {
        ll = llp;
    }
    
    // Add this method to get the field types
    public Map<String, ASTType> getFieldTypes() {
        return ll.toMap();  // You'll need to implement toMap() in TypeBindList
    }
    
    public String toStr() {
        return "struct { ... }";
    }
}