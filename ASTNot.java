public class ASTNot implements ASTNode {
    private ASTNode operand;
    
    public ASTNot(ASTNode operand) {
        this.operand = operand;
    }

    public ASTNode getOperand() {
        return operand;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue val = operand.eval(e);
        if (val instanceof VBool) {
            return new VBool(!((VBool) val).getval());
        } else {
            throw new InterpreterError("Not operator requires a boolean operand");
        }
    }
}