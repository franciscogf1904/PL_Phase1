import java.util.*;

public class Environment <E>{
    Environment<E> anc;
    Map<String, E> bindings;

    Environment(){
        anc = null;
        bindings = new HashMap<String,E>();
    }
    
    Environment(Environment<E> ancestor){
        anc = ancestor;
        bindings = new HashMap<String,E>();
    }

    Environment<E> beginScope(){
        return new Environment<E>(this);
    }
    
    Environment<E> endScope(){
        return anc;
    }

    void assoc(String id, E bind) throws InterpreterError {
        bindings.put(id, bind);
    }


    E find(String id) throws InterpreterError {
        if (bindings.containsKey(id)) {
            E val = bindings.get(id);

            return val;
        } else if (anc != null) {
            return anc.find(id);
        } else {
            throw new InterpreterError("Unbound variable: " + id);
        }
    }

    public Environment<E> copy() {
        Environment<E> newEnv = new Environment<>();
        
        // Copy all bindings from this environment
        for (Map.Entry<String, E> entry : bindings.entrySet()) {
            newEnv.bindings.put(entry.getKey(), entry.getValue());
        }
        
        // Copy ancestor environment if it exists
        if (anc != null) {
            newEnv.anc = anc.copy();
        }
        
        return newEnv;
    }

    public void reassoc(String id, E value) throws InterpreterError {
        if (bindings.containsKey(id)) {
            bindings.put(id, value);
        } else if (anc != null) {
            anc.reassoc(id, value);
        } else {
            throw new InterpreterError("Unbound variable: " + id);
        }
    }
}
