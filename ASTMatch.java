import java.util.List;

public class ASTMatch implements ASTNode {
    private ASTNode target;
    private List<ASTMatchClause> clauses;

    public ASTMatch(ASTNode target, List<ASTMatchClause> clauses) {
        this.target = target;
        this.clauses = clauses;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue value = target.eval(env);
        for (ASTMatchClause clause : clauses) {
            Environment<IValue> clauseEnv = env.beginScope();
            if (clause.matches(value, clauseEnv)) {
                return clause.getBody().eval(clauseEnv);
            }
        }
        throw new InterpreterError("No matching pattern");
    }
}