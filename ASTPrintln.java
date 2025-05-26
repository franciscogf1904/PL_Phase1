public class ASTPrintln implements ASTNode {
    private ASTNode expr;
    public ASTPrintln(ASTNode expr) { this.expr = expr; }
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v = expr.eval(e);
        System.out.println(v.toStr());
        return v;
    }
}