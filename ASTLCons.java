public class ASTLCons implements ASTNode {
    private final ASTNode head;
    private final ASTNode tail;

    public ASTLCons(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        VThunk headThunk = new VThunk(head, env);
        VThunk tailThunk = new VThunk(tail, env);
        return new VLazyList(headThunk, tailThunk);
    }
}