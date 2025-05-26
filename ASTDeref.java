public class ASTDeref implements ASTNode {
    private ASTNode expr;
    public ASTDeref(ASTNode expr) { this.expr = expr; }
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v = expr.eval(e);
        if (v instanceof VCell) return ((VCell)v).get();
        throw new InterpreterError("Cannot dereference non-box value");
    }
}