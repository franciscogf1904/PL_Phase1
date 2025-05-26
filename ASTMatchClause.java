public class ASTMatchClause {
    private ASTPattern pattern;
    private ASTNode body;

    public ASTMatchClause(ASTPattern pattern, ASTNode body) {
        this.pattern = pattern;
        this.body = body;
    }

    public boolean matches(IValue value, Environment<IValue> env) throws InterpreterError {
        return pattern.match(value, env);
    }

    public ASTNode getBody() {
        return body;
    }
}