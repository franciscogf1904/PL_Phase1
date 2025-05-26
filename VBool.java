public class VBool implements IValue {
    private boolean val;
    
    public VBool(boolean v) {
        val = v;
    }
    
    public boolean getval() {
        return val;
    }
    
    @Override
    public String toStr() {
        return Boolean.toString(val);
    }
}