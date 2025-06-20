public class L1PPint {

    public static void main(String args[]) {
        Parser parser = new Parser(System.in);
        ASTNode exp;

        System.out.println("L1++ interpreter PL MEIC 2024/25 (v0.0)\n");

        while (true) {
            try {
                System.out.print("# ");
                exp = parser.Start();  // parse an expression

                if (exp == null) System.exit(0);

                TypeBindList typedefs = new TypeBindList();
                TypeChecker checker = new TypeChecker(typedefs);
                TypeEnv env = new TypeEnv();

                try {
                    ASTType t = checker.typeOf(exp, env);
                } catch (TypeError err) {
                    System.err.println("Type Error: " + err.getMessage());
                    continue;
                }

                IValue v = exp.eval(new Environment<IValue>());
                System.out.println(v.toStr());

            } catch (ParseException e) {
                System.out.println("Syntax Error.");
                parser.ReInit(System.in);

            } catch (Exception e) {
                e.printStackTrace();
                parser.ReInit(System.in);
            }
        }
    }
}

