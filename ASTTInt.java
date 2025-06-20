public class ASTTInt implements ASTType {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ASTTInt;
    }
    
    public String toStr() {
        return "int";
    }
}


