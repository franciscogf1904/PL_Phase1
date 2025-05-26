public class ASTPrint implements ASTNode {
    private ASTNode expr;
    public ASTPrint(ASTNode expr) { this.expr = expr; }
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v = expr.eval(e);
        System.out.print(v.toStr());
        return v;
    }
}