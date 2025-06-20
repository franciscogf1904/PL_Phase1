import java.util.Map;

public class ASTSubtype {

    public static boolean isSubtype(ASTType a, ASTType b) {
        // Reflexivity
        if (a.equals(b)) return true;

        // Resolve TVar if needed
        if (a instanceof ASTTVar || b instanceof ASTTVar) {
            // Do not resolve blindly here, assume deepResolve done before call
            return a.equals(b);
        }

        // Function types: contravariant in input, covariant in output
        if (a instanceof ASTTArrow f1 && b instanceof ASTTArrow f2) {
            return isSubtype(f2.from, f1.from) && isSubtype(f1.to, f2.to);
        }

        // ref A is invariant: ref A <: ref B iff A = B
        if (a instanceof ASTTRef ra && b instanceof ASTTRef rb) {
            return isSubtype(ra.getType(), rb.getType()) && isSubtype(rb.getType(), ra.getType());
        }

        // Records (structs): width & depth subtyping
        if (a instanceof ASTTStruct sa && b instanceof ASTTStruct sb) {
            Map<String, ASTType> saFields = sa.getFieldTypes();
            Map<String, ASTType> sbFields = sb.getFieldTypes();
            
            for (String field : sbFields.keySet()) {
                if (!saFields.containsKey(field)) return false;
                if (!isSubtype(saFields.get(field), sbFields.get(field))) return false;
            }
            return true;
        }

        // Variants (unions): width & depth subtyping
        if (a instanceof ASTTUnion ua && b instanceof ASTTUnion ub) {
            Map<String, ASTType> uaVariants = ua.getVariantTypes();
            Map<String, ASTType> ubVariants = ub.getVariantTypes();
            
            for (String label : uaVariants.keySet()) {
                if (!ubVariants.containsKey(label)) return false;
                if (!isSubtype(uaVariants.get(label), ubVariants.get(label))) return false;
            }
            return true;
        }
        return false;
    }
}
