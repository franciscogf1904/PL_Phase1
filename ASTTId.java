public	class ASTTId implements ASTType	{	

    String id;	
    
    public ASTTId(String id)	{
        this.id = id;
    }
    public String toStr() {
        return id;
    }

}	
