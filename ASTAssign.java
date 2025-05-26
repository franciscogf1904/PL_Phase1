public class ASTAssign implements ASTNode {
    private String id;
    private ASTNode expr;

    public ASTAssign(String id, ASTNode expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue box = env.find(id);
        if (!(box instanceof VCell)) {
            throw new InterpreterError("Cannot assign to non-box value");
        }
        IValue value = expr.eval(env);
        ((VCell) box).set(value);
        return value;
    }
}