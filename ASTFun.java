import java.util.List;

public class ASTFun implements ASTNode {
    private List<String> params;
    private ASTNode body;
    
    public ASTFun(List<String> params, ASTNode body) {
        this.params = params;
        this.body = body;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VClos(params, body, e);
    }
}