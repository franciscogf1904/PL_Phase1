import java.util.HashMap;
import java.util.Map;

public class TypeEnv {
    private final Map<String, ASTType> env = new HashMap<>();

    public void add(String var, ASTType type) {
        env.put(var, type);
    }

    public ASTType lookup(String var) {
        if (!env.containsKey(var)) {
            throw new TypeError("Unbound variable: " + var);
        }
        return env.get(var);
    }

    // Useful for creating a new copy for scoped constructs like `let`
    public TypeEnv copy() {
        TypeEnv copy = new TypeEnv();
        copy.env.putAll(this.env);
        return copy;
    }

    public void reassoc(String var, ASTType type) {
    env.put(var, type);
    } 

    @Override
    public String toString() {
        return env.toString();
    }
}
