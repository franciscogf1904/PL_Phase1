import java.util.*;

public class ASTTypeDef implements ASTNode {
HashMap<String,ASTType> ltd;
ASTNode body;

    public ASTTypeDef(HashMap<String,ASTType>  ltdp, ASTNode b) {
	ltd = ltdp;
    body = b;
    }
    
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        return body.eval(env);
    }
}
