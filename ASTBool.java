public class ASTBool implements ASTNode {
    private boolean value;
    
    public ASTBool(boolean value) {
        this.value = value;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VBool(value);
    }
}