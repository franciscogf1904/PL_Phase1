import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Set;

public class TypeResolver {

    public static ASTType resolve(ASTType astType, TypeBindList typedefs) {
        // Add cycle detection for recursive types
        return resolve(astType, typedefs, new HashSet<>());
    }

    private static ASTType resolve(ASTType astType, TypeBindList typedefs, Set<String> seen) {
        if (astType instanceof ASTTId idType) {
            // Check for cycles
            if (seen.contains(idType.id)) {
                return new ASTTVar(idType.id);  // Return variable to break cycle
            }
            
            seen.add(idType.id);
            ASTType resolved = typedefs.resolve(idType.id);
            if (resolved == null) {
                throw new TypeError("Undefined type: " + idType.id);
            }
            return resolve(resolved, typedefs, seen);
        }
        if (astType instanceof ASTTInt) {
            return new ASTTInt();
        }

        if (astType instanceof ASTTBool) {
            return new ASTTBool();
        }

        if (astType instanceof ASTTString) {
            return new ASTTString();
        }

        if (astType instanceof ASTTUnit) {
            return new ASTTUnit();
        }

        if (astType instanceof ASTTList listType) {
            return new ASTTList(resolve(listType.getElt(), typedefs));
        }

        if (astType instanceof ASTTRef refType) {
            return new ASTTRef(resolve(refType.getType(), typedefs));
        }

        if (astType instanceof ASTTArrow arrowType) {
            ASTType from = resolve(arrowType.getFrom(), typedefs);
            ASTType to = resolve(arrowType.getTo(), typedefs);
            return new ASTTArrow(from, to);
        }

        if (astType instanceof ASTTId idType) {
            // Named type (e.g., Tree) - return a placeholder, resolve later during typechecking
            return new ASTTVar(idType.id);
        }

        if (astType instanceof ASTTStruct structType) {
            Map<String, ASTType> fields = new LinkedHashMap<>();
            for (Map.Entry<String, ASTType> entry : structType.getFieldTypes().entrySet()) {
                fields.put(entry.getKey(), resolve(entry.getValue(), typedefs));
            }
            return new ASTTStruct(TypeBindList.fromMap(fields));
        }

        if (astType instanceof ASTTUnion unionType) {
            Map<String, ASTType> variants = new LinkedHashMap<>();
            for (Map.Entry<String, ASTType> entry : unionType.variants.entrySet()) {
                variants.put(entry.getKey(), resolve(entry.getValue(), typedefs));
            }
            return new ASTTUnion(variants);
        }

        throw new RuntimeException("Unknown ASTType node: " + astType);
    }

    public static ASTType deepResolve(ASTType type, TypeBindList typedefs) {
        // Handle type variables (recursive types)
        if (type instanceof ASTTVar) {
            ASTType resolved = typedefs.resolve(((ASTTVar) type).name);
            if (resolved == null) return type;  // Unresolved type variable
            return deepResolve(resolved, typedefs);  // Recursively resolve
        }
        
        // For compound types, recursively resolve their components
        if (type instanceof ASTTList) {
            ASTTList listType = (ASTTList) type;
            return new ASTTList(deepResolve(listType.getElt(), typedefs));
        }
        
        if (type instanceof ASTTRef) {
            ASTTRef refType = (ASTTRef) type;
            return new ASTTRef(deepResolve(refType.getType(), typedefs));
        }
        
        if (type instanceof ASTTArrow) {
            ASTTArrow arrowType = (ASTTArrow) type;
            return new ASTTArrow(
                deepResolve(arrowType.from, typedefs),
                deepResolve(arrowType.to, typedefs)
            );
        }
        
        // Base cases (don't need resolution)
        if (type instanceof ASTTInt || type instanceof ASTTBool || 
            type instanceof ASTTString || type instanceof ASTTUnit) {
            return type;
        }
        
        throw new RuntimeException("Unresolvable type: " + type);
    }
}












