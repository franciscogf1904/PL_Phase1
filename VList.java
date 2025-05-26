public class VList implements IValue {
    public static final VList NIL = new VList(null, null);
    private final IValue head;
    private final IValue tail;

    public VList(IValue head, IValue tail) {
        this.head = head;
        this.tail = tail;
    }

    public boolean isNil() {
        return this == NIL;
    }

    public IValue getHead() {
        return head;
    }

    public IValue getTail() {
        return tail;
    }

    @Override
    public String toStr() {
        if (isNil()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(head.toStr());
        IValue currentTail = tail;
        while (currentTail instanceof VList) {
            VList list = (VList) currentTail;
            if (list.isNil()) break;
            sb.append("::").append(list.getHead().toStr());
            currentTail = list.getTail();
        }
        if (!(currentTail instanceof VList) || !((VList) currentTail).isNil()) {
            sb.append("::").append(currentTail.toStr());
        }
        return sb.toString();
    }
}
