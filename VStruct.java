import java.util.Map;

public class VStruct implements IValue {
    private final Map<String, IValue> fields;

    public VStruct(Map<String, IValue> fields) {
        this.fields = fields;
    }

    public IValue get(String field) {
        if (!fields.containsKey(field)) {
            throw new RuntimeException("Field '" + field + "' not found in struct");
        }
        return fields.get(field);
    }

    @Override
    public String toStr() {
        return fields.toString();
    }
}