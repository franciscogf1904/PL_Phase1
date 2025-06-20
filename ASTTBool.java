class ASTTBool implements ASTType {
        
    @Override
    public boolean equals(Object obj) {
        return obj instanceof ASTTBool;
    }
    public String toStr() {
        return "bool";
    }
}