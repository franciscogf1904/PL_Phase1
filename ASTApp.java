import java.util.ArrayList;
import java.util.List;

public class ASTApp implements ASTNode {
    private ASTNode fun;
    private List<ASTNode> args;
    
    public ASTApp(ASTNode fun, List<ASTNode> args) {
        this.fun = fun;
        this.args = args;
    }

    public ASTNode getFun() {
        return fun;
    }

    public List<ASTNode> getArgs() {
        return args;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue funVal = fun.eval(e);
        if (!(funVal instanceof VClos)) {
            throw new InterpreterError("Cannot apply non-function value");
        }
        VClos function = (VClos) funVal;
        List<String> params = function.getParams();

        // Partial application: fewer arguments than parameters
        if (args.size() < params.size()) {
            // Evaluate provided arguments
            Environment<IValue> partialEnv = function.getEnv().copy();
            for (int i = 0; i < args.size(); i++) {
                IValue argVal = args.get(i).eval(e);
                partialEnv.assoc(params.get(i), argVal);
            }
            // Return a new function expecting the remaining parameters
            List<String> remainingParams = params.subList(args.size(), params.size());
            return new VClos(remainingParams, function.getBody(), partialEnv);
        }

        // Normal case: all arguments provided
        if (params.size() != args.size()) {
            throw new InterpreterError("Function called with wrong number of arguments");
        }
        Environment<IValue> funEnv = function.getEnv().copy();
        for (int i = 0; i < args.size(); i++) {
            IValue argVal = args.get(i).eval(e);
            funEnv.assoc(params.get(i), argVal);
        }
        return function.getBody().eval(funEnv);
    }
}