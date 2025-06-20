import java.util.List;

public class ASTFun implements ASTNode {
    private List<String> params;
    private ASTNode body;
    private List<ASTType> paramTypes;
    
    public ASTFun(List<String> params, List<ASTType> paramTypes, ASTNode body) {
        this.params = params;
        this.paramTypes = paramTypes;
        this.body = body;
    }

    public List<String> getParams() {
    return params;
    }

    public ASTNode getBody() {
    return body;
    }

    public void setBody(ASTNode body) {
        this.body = body;
    }

    public List<ASTType> getParamTypes() {
    return paramTypes; 
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VClos(params, body, e);
    }
}