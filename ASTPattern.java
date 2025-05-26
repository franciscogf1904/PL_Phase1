public interface ASTPattern {
    boolean match(IValue value, Environment<IValue> env) throws InterpreterError;
}

// Nil Pattern
class ASTNilPattern implements ASTPattern {
    @Override
    public boolean match(IValue value, Environment<IValue> env) {
        return value == VList.NIL;
    }
}

// Cons Pattern (x::y)
class ASTConsPattern implements ASTPattern {
    private String headVar;
    private String tailVar;

    public ASTConsPattern(String headVar, String tailVar) {
        this.headVar = headVar;
        this.tailVar = tailVar;
    }

    @Override
    public boolean match(IValue value, Environment<IValue> env) throws InterpreterError {
        if (value instanceof VList) {
            VList list = (VList) value;
            if (list.isNil()) return false;
            env.assoc(headVar, list.getHead());
            env.assoc(tailVar, list.getTail());
            return true;
        }
        if (value instanceof VLazyList) {
            VLazyList cons = (VLazyList) value;
            env.assoc(headVar, cons.head());
            env.assoc(tailVar, cons.tail());
            return true;
        }
        return false;
    }
}

// Lazy Cons PAttern (x:?y)
class ASTLazyConsPattern implements ASTPattern {
    private String headVar;
    private String tailVar;

    public ASTLazyConsPattern(String headVar, String tailVar) {
        this.headVar = headVar;
        this.tailVar = tailVar;
    }

    @Override
    public boolean match(IValue value, Environment<IValue> env) throws InterpreterError {
        if (value instanceof VLazyList) {
            VLazyList cons = (VLazyList) value;
            env.assoc(headVar, cons.head());
            env.assoc(tailVar, cons.tail());
            return true;
        }
        return false;
    }
}

// Variable Pattern (x)
class ASTVarPattern implements ASTPattern {
    private String var;

    public ASTVarPattern(String var) {
        this.var = var;
    }

    @Override
    public boolean match(IValue value, Environment<IValue> env) throws InterpreterError {
        env.assoc(var, value);
        return true;
    }
}