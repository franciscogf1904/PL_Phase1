public class ASTWhile implements ASTNode {
    private ASTNode cond, body;
    public ASTWhile(ASTNode cond, ASTNode body) { this.cond = cond; this.body = body; }
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v = cond.eval(e);
        while (v instanceof VBool && ((VBool)v).getval()) {
            body.eval(e);
            v = cond.eval(e);
        }
        return new VNil();
    }
}