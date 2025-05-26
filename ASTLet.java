import java.util.List;

public class ASTLet implements ASTNode {
    List<Bind> decls;
    ASTNode body;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        Environment<IValue> en = e.beginScope();

        // Step 1: Pre-bind all variable names to null
        for (Bind b : decls) {
            en.assoc(b.getId(), null);
        }

        // Step 2: Evaluate each right-hand side and update the binding
        for (Bind b : decls) {
            IValue v = b.getExp().eval(en);  // Use 'en' here instead of 'e'
            en.reassoc(b.getId(), v);
        }

        // Step 3: Evaluate the body in the new environment
        IValue result = body.eval(en);
        en.endScope();
        return result;
    }

    public ASTLet(List<Bind> decls, ASTNode b) {
        this.decls = decls;
        this.body = b;
    }
}
