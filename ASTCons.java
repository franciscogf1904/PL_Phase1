public class ASTCons implements ASTNode {
    private ASTNode left;
    private ASTNode right;

    public ASTCons(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue head = left.eval(env);
        IValue tail = right.eval(env);
        return new VList(head, tail);
    }
}