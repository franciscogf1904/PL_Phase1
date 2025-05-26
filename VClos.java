import java.util.List;

public class VClos implements IValue {
    private List<String> params;
    private ASTNode body;
    private Environment<IValue> env;
    
    public VClos(List<String> params, ASTNode body, Environment<IValue> env) {
        this.params = params;
        this.body = body;
        this.env = env;
    }
    
    public List<String> getParams() {
        return params;
    }
    
    public ASTNode getBody() {
        return body;
    }
    
    public Environment<IValue> getEnv() {
        return env;
    }
    
    @Override
    public String toStr() {
        return "<function>";
    }
}