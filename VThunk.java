public class VThunk implements IValue {
    private final ASTNode expr;
    private final Environment<IValue> env;
    private IValue cache = null;
    private boolean forced = false;

    public VThunk(ASTNode expr, Environment<IValue> env) {
        this.expr = expr;
        this.env = env;
    }

    public IValue force() throws InterpreterError {
        if (!forced) {
            cache = expr.eval(env);
            forced = true;
        }
        return cache;
    }

    @Override
    public String toStr() {
        try {
            return force().toStr();
        } catch (InterpreterError e) {
            return "<thunk error>";
        }
    }
}