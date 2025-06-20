public class ASTTVar implements ASTType {
    public final String name;

    public ASTTVar(String name) {
        this.name = name;
    }

    // Add this method to satisfy the ASTType interface
    @Override
    public String toStr() {
        return name;
    }

    // Optional: Keep toString() for debugging
    @Override
    public String toString() {
        return toStr(); // Reuse toStr() implementation
    }
}