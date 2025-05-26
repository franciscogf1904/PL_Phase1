public class ASTBox implements ASTNode {
    private ASTNode expr;
    public ASTBox(ASTNode expr) { this.expr = expr; }
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VCell(expr.eval(e));
    }
}