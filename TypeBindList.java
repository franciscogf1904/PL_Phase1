import java.util.*;

public class TypeBindList {
    private List<TypeBind> bindings = new ArrayList<>();
    
    public void addBinding(TypeBind binding) {
        bindings.add(binding);
    }
    
    // Convert bindings to a field map
    public Map<String, ASTType> toMap() {
        Map<String, ASTType> map = new HashMap<>();
        for (TypeBind binding : bindings) {
            map.put(binding.getId(), binding.getType());
        }
        return map;
    }

    
    public static TypeBindList fromMap(Map<String, ASTType> map) {
        TypeBindList list = new TypeBindList();
        for (Map.Entry<String, ASTType> entry : map.entrySet()) {
            list.addBinding(new TypeBind(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    public ASTType resolve(String typeName) {
        for (TypeBind binding : bindings) {
            if (binding.getId().equals(typeName)) {
                return binding.getType();
            }
        }
        return null;
    }
    
    public boolean isRecursive(String typeName) {
        return isRecursive(typeName, new HashSet<>());
    }
    
    private boolean isRecursive(String typeName, Set<String> seen) {
        if (seen.contains(typeName)) return true;
        seen.add(typeName);
        
        ASTType type = resolve(typeName);
        if (type == null) return false;
        
        // Check if type contains references to typeName
        return containsType(type, typeName, new HashSet<>());
    }

    private boolean containsType(ASTType type, String targetTypeName, Set<String> seen) {
        if (type instanceof ASTTVar) {
                return ((ASTTVar) type).name.equals(targetTypeName);
        }
        if (type instanceof ASTTId) {
                String typeName = ((ASTTId) type).id;
                if (typeName.equals(targetTypeName)) return true;
                if (seen.contains(typeName)) return false;
                seen.add(typeName);
                ASTType resolved = resolve(typeName);
                return resolved != null && containsType(resolved, targetTypeName, seen);
        }
        if (type instanceof ASTTList) {
                return containsType(((ASTTList) type).getElt(), targetTypeName, seen);
        }
        if (type instanceof ASTTRef) {
                return containsType(((ASTTRef) type).getType(), targetTypeName, seen);
        }
        if (type instanceof ASTTArrow) {
                ASTTArrow arrow = (ASTTArrow) type;
                return containsType(arrow.from, targetTypeName, seen) || 
                containsType(arrow.to, targetTypeName, seen);
        }
        return false;
    }
}