public class ASTNil implements ASTNode {
    @Override
    public IValue eval(Environment<IValue> env) {
        return VList.NIL; // Singleton nil value
    }
}