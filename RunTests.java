import java.util.*;

public class RunTests {
    public static void main(String[] args) {
        try {
            // Create a fresh environment for each test group
            runL1Step1Tests();
            // Uncomment when you're ready to test step 2 features
            // runL1Step2Tests();
        } catch (InterpreterError e) {
            System.err.println("Interpreter error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runL1Step1Tests() throws InterpreterError {
        System.out.println("=== Running L1 Step 1 Tests ===");
        
        // Test 1: let x = 1; (x + 1);;
        Environment<IValue> env = new Environment<>();
        List<Bind> binds1 = new ArrayList<>();
        binds1.add(new Bind("x", new ASTInt(1)));
        ASTNode body1 = new ASTPlus(new ASTId("x"), new ASTInt(1));
        ASTNode prog1 = new ASTLet(binds1, body1);
        IValue result1 = prog1.eval(env);
        System.out.println("Test 1: let x = 1; (x + 1);;\nResult: " + result1.toStr() + "\n");
        
        // Test 2: (let x = 1; (x + 1)) * (let x = 2; (x + 3))
        env = new Environment<>();
        List<Bind> bindsLeft = new ArrayList<>();
        bindsLeft.add(new Bind("x", new ASTInt(1)));
        ASTNode bodyLeft = new ASTPlus(new ASTId("x"), new ASTInt(1));
        ASTNode letLeft = new ASTLet(bindsLeft, bodyLeft);
        
        List<Bind> bindsRight = new ArrayList<>();
        bindsRight.add(new Bind("x", new ASTInt(2)));
        ASTNode bodyRight = new ASTPlus(new ASTId("x"), new ASTInt(3));
        ASTNode letRight = new ASTLet(bindsRight, bodyRight);
        
        ASTNode prog2 = new ASTMult(letLeft, letRight);
        IValue result2 = prog2.eval(env);
        System.out.println("Test 2: (let x = 1; (x + 1)) * (let x = 2; (x + 3))\nResult: " + result2.toStr() + "\n");
        
        // Test 3: let x = 1; let y = 2; (x + y);;
        env = new Environment<>();
        List<Bind> bindsX = new ArrayList<>();
        bindsX.add(new Bind("x", new ASTInt(1)));
        
        List<Bind> bindsY = new ArrayList<>();
        bindsY.add(new Bind("y", new ASTInt(2)));
        
        ASTNode innerBody = new ASTPlus(new ASTId("x"), new ASTId("y"));
        ASTNode innerLet = new ASTLet(bindsY, innerBody);
        ASTNode prog3 = new ASTLet(bindsX, innerLet);
        IValue result3 = prog3.eval(env);
        System.out.println("Test 3: let x = 1; let y = 2; (x + y);;\nResult: " + result3.toStr() + "\n");

        // Test 4: let x = 2; let z = x+2; let k = (let x = z+2; x*x); k+k;;
        env = new Environment<>();
        List<Bind> binds4a = new ArrayList<>();
        binds4a.add(new Bind("x", new ASTInt(2)));
        
        List<Bind> binds4b = new ArrayList<>();
        binds4b.add(new Bind("z", new ASTPlus(new ASTId("x"), new ASTInt(2))));
        
        List<Bind> binds4c_inner = new ArrayList<>();
        binds4c_inner.add(new Bind("x", new ASTPlus(new ASTId("z"), new ASTInt(2))));
        ASTNode body4c_inner = new ASTMult(new ASTId("x"), new ASTId("x"));
        ASTNode let4c_inner = new ASTLet(binds4c_inner, body4c_inner);
        
        List<Bind> binds4c = new ArrayList<>();
        binds4c.add(new Bind("k", let4c_inner));
        
        ASTNode body4 = new ASTPlus(new ASTId("k"), new ASTId("k"));
        
        ASTNode let4b = new ASTLet(binds4c, body4);
        ASTNode let4a = new ASTLet(binds4b, let4b);
        ASTNode prog4 = new ASTLet(binds4a, let4a);
        
        IValue result4 = prog4.eval(env);
        System.out.println("Test 4: let x = 2; let z = x+2; let k = (let x = z+2; x*x); k+k;;\nResult: " + result4.toStr() + "\n");

        // Test 5: let y = 1; let b = (y > 0) && (y <= 20); let z = (let z = 2*y; z*z); b || ~ (z < 0);;
        env = new Environment<>();
        List<Bind> binds5a = new ArrayList<>();
        binds5a.add(new Bind("y", new ASTInt(1)));
        
        List<Bind> binds5b = new ArrayList<>();
        ASTNode condition1 = new ASTGreater(new ASTId("y"), new ASTInt(0));
        ASTNode condition2 = new ASTLessEq(new ASTId("y"), new ASTInt(20));
        ASTNode andResult = new ASTAnd(condition1, condition2);
        binds5b.add(new Bind("b", andResult));
        
        List<Bind> binds5c_inner = new ArrayList<>();
        binds5c_inner.add(new Bind("z", new ASTMult(new ASTInt(2), new ASTId("y"))));
        ASTNode body5c_inner = new ASTMult(new ASTId("z"), new ASTId("z"));
        ASTNode let5c_inner = new ASTLet(binds5c_inner, body5c_inner);
        
        List<Bind> binds5c = new ArrayList<>();
        binds5c.add(new Bind("z", let5c_inner));
        
        ASTNode zLessThan0 = new ASTLess(new ASTId("z"), new ASTInt(0));
        ASTNode notZLessThan0 = new ASTNot(zLessThan0);
        ASTNode body5 = new ASTOr(new ASTId("b"), notZLessThan0);
        
        ASTNode let5b = new ASTLet(binds5c, body5);
        ASTNode let5a = new ASTLet(binds5b, let5b);
        ASTNode prog5 = new ASTLet(binds5a, let5a);
        
        IValue result5 = prog5.eval(env);
        System.out.println("Test 5: let y = 1; let b = (y > 0) && (y <= 20); let z = (let z = 2*y; z*z); b || ~ (z < 0);;\nResult: " + result5.toStr() + "\n");

        // Additional test: let y = 1; let b = (y > 0) && (y <= 20); let z = (let b = 2*y; b-y); b || ~ (z < 0);;
        env = new Environment<>();
        List<Bind> binds6a = new ArrayList<>();
        binds6a.add(new Bind("y", new ASTInt(1)));
        
        List<Bind> binds6b = new ArrayList<>();
        ASTNode condition6_1 = new ASTGreater(new ASTId("y"), new ASTInt(0));
        ASTNode condition6_2 = new ASTLessEq(new ASTId("y"), new ASTInt(20));
        ASTNode andResult6 = new ASTAnd(condition6_1, condition6_2);
        binds6b.add(new Bind("b", andResult6));
        
        List<Bind> binds6c_inner = new ArrayList<>();
        binds6c_inner.add(new Bind("b", new ASTMult(new ASTInt(2), new ASTId("y"))));
        ASTNode body6c_inner = new ASTSub(new ASTId("b"), new ASTId("y"));
        ASTNode let6c_inner = new ASTLet(binds6c_inner, body6c_inner);
        
        List<Bind> binds6c = new ArrayList<>();
        binds6c.add(new Bind("z", let6c_inner));
        
        ASTNode zLessThan0_6 = new ASTLess(new ASTId("z"), new ASTInt(0));
        ASTNode notZLessThan0_6 = new ASTNot(zLessThan0_6);
        ASTNode body6 = new ASTOr(new ASTId("b"), notZLessThan0_6);
        
        ASTNode let6b = new ASTLet(binds6c, body6);
        ASTNode let6a = new ASTLet(binds6b, let6b);
        ASTNode prog6 = new ASTLet(binds6a, let6a);
        
        IValue result6 = prog6.eval(env);
        System.out.println("Test 6: let y = 1; let b = (y > 0) && (y <= 20); let z = (let b = 2*y; b-y); b || ~ (z < 0);;\nResult: " + result6.toStr() + "\n");
    }

    // Implement this method when ready for step 2
    private static void runL1Step2Tests() throws InterpreterError {
        System.out.println("=== Running L1 Step 2 Tests ===");
        // Step 2 tests for functions
        // Add implementation when ready
    }
}
