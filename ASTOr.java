public class ASTOr implements ASTNode {
    private ASTNode lhs;
    private ASTNode rhs;
    
    public ASTOr(ASTNode lhs, ASTNode rhs) {
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
        
        if (!(v1 instanceof VBool)) {
            throw new InterpreterError("OR operator requires boolean operands");
        }
        
        // Short-circuit evaluation
        if (((VBool) v1).getval()) {
            return new VBool(true);
        }
        
        IValue v2 = rhs.eval(e);
        if (!(v2 instanceof VBool)) {
            throw new InterpreterError("OR operator requires boolean operands");
        }
        
        return new VBool(((VBool) v1).getval() || ((VBool) v2).getval());
    }
}