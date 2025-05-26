public class VLazyList implements IValue {
    private final VThunk headThunk;
    private final VThunk tailThunk;
    private IValue headCache = null;
    private IValue tailCache = null;

    public VLazyList(VThunk headThunk, VThunk tailThunk) {
        this.headThunk = headThunk;
        this.tailThunk = tailThunk;
    }

    public IValue head() throws InterpreterError {
        if (headCache == null) {
            headCache = headThunk.force();
        }
        return headCache;
    }

    public IValue tail() throws InterpreterError {
        if (tailCache == null) {
            tailCache = tailThunk.force();
        }
        return tailCache;
    }

    @Override
    public String toStr() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(head().toStr());
            IValue t = tail();
            while (t instanceof VLazyList) {
                VLazyList cons = (VLazyList) t;
                sb.append(" :? ").append(cons.head().toStr());
                t = cons.tail();
            }
            if (!(t instanceof VList && ((VList)t).isNil())) {
                sb.append(" :? ").append(t.toStr());
            }
        } catch (InterpreterError e) {
            sb.append(" :? <error>");
        }
        return sb.toString();
    }
}