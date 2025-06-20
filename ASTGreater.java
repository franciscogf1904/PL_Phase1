public class ASTGreater implements ASTNode {
    private ASTNode lhs;
    private ASTNode rhs;
    
    public ASTGreater(ASTNode lhs, ASTNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public ASTNode getLhs() {
        return lhs;
    }

    public ASTNode getRhs() {
        return rhs;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = lhs.eval(e);
        IValue v2 = rhs.eval(e);
        
        if (v1 instanceof VInt && v2 instanceof VInt) {
            return new VBool(((VInt) v1).getval() > ((VInt) v2).getval());
        } else {
            throw new InterpreterError("Greater than operator requires integer operands");
        }
    }
}