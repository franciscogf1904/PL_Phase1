import java.util.Map;

public class ASTTUnion implements ASTType {
    public final Map<String, ASTType> variants;

    public ASTTUnion(Map<String, ASTType> variants) {
        this.variants = variants;
    }

    public Map<String, ASTType> getVariantTypes() {
        return variants;
    }

    // Add this method to satisfy the ASTType interface
    @Override
    public String toStr() {
        return "union" + variants.toString();
    }

    // Optional: Keep toString() for debugging
    @Override
    public String toString() {
        return toStr(); // Reuse toStr() implementation
    }
}
