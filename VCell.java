public class VCell implements IValue {
    private IValue value;
    public VCell(IValue value) { this.value = value; }
    public IValue get() { return value; }
    public void set(IValue value) { this.value = value; }
    @Override
    public String toStr() { return value.toStr(); }
}