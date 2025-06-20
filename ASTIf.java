public class ASTIf implements ASTNode {
    public ASTNode cond, thenBranch, elseBranch;
    public ASTIf(ASTNode cond, ASTNode thenBranch, ASTNode elseBranch) {
        this.cond = cond;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v = cond.eval(e);
        if (!(v instanceof VBool)) throw new InterpreterError("Condition must be boolean");
        return ((VBool)v).getval() ? thenBranch.eval(e) : elseBranch.eval(e);
    }
}