public class ASTFieldAccess implements ASTNode {
    public final ASTNode recordExpr;
    public final String field;

    public ASTFieldAccess(ASTNode recordExpr, String field) {
        this.recordExpr = recordExpr;
        this.field = field;
    }

    public IValue eval(Environment<IValue> env) throws InterpreterError {
    IValue recordValue = recordExpr.eval(env);

    if (!(recordValue instanceof VStruct)) {
        throw new InterpreterError("Expected a struct, got: " + recordValue);
    }

    VStruct struct = (VStruct) recordValue;
    return struct.get(field);  // This will throw if field is missing
    }

    public String toStr() {
        return recordExpr.toString() + "." + field;
    }

}

