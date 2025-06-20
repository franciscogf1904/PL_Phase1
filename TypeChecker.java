public class TypeChecker {

    private final TypeBindList typedefs;

    public TypeChecker(TypeBindList typedefs) {
        this.typedefs = typedefs;
    }

    public ASTType typeOf(ASTNode node, TypeEnv env) {
        if (node instanceof ASTInt) {
            return new ASTTInt();
        }

        if (node instanceof ASTBool) {
            return new ASTTBool();
        }

        if (node instanceof ASTIf ifNode) {
            ASTType condType = typeOf(ifNode.cond, env);
            if (!(TypeResolver.deepResolve(condType, typedefs) instanceof ASTTBool)) {
                throw new TypeError("Condition in if must be bool");
            }

            ASTType t1 = typeOf(ifNode.thenBranch, env);
            ASTType t2 = typeOf(ifNode.elseBranch, env);

            if (ASTSubtype.isSubtype(t1, t2)) return t2;
            if (ASTSubtype.isSubtype(t2, t1)) return t1;

            throw new TypeError("Branches of if have incompatible types: " + t1 + " and " + t2);
        }

        if (node instanceof ASTId idNode) {
            return env.lookup(idNode.id);
        }

        if (node instanceof ASTLet letNode) {
            TypeEnv extended = env.copy();
            for (Bind b : letNode.decls) {
                extended.add(b.getId(), null);
            }
            for (Bind b : letNode.decls) {
                ASTType valueType = typeOf(b.getExp(), extended);
                extended.reassoc(b.getId(), valueType);
            }
            return typeOf(letNode.body, extended);
        }

        if (node instanceof ASTFun funNode) {
            TypeEnv extended = env.copy();
            for (String param : funNode.getParams()) {
                extended.add(param, null);
            }
            ASTType bodyType = typeOf(funNode.getBody(), extended);
            return bodyType; 
        }

        if (node instanceof ASTApp appNode) {
            ASTType funType = TypeResolver.deepResolve(typeOf(appNode.getFun(), env), typedefs);
            ASTType argType = typeOf(appNode.getArgs().get(0), env);

            if (!(funType instanceof ASTTArrow)) {
                throw new TypeError("Attempting to apply a non-function value");
            }
            ASTTArrow arrow = (ASTTArrow) funType;

            if (!arrow.from.equals(argType)) {
                throw new TypeError("Function argument type mismatch: expected " +
                    arrow.from + ", got " + argType);
            }

            return arrow.to;
        }

        if (node instanceof ASTIf i) {
            ASTType condType = TypeResolver.deepResolve(typeOf(i.cond, env), typedefs);
            if (!(condType instanceof ASTTBool)) {
                throw new TypeError("Condition in 'if' must be bool. Got: " + condType);
            }

            ASTType thenType = typeOf(i.thenBranch, env);
            ASTType elseType = typeOf(i.elseBranch, env);

            if (ASTSubtype.isSubtype(thenType, elseType)) return elseType;
            if (ASTSubtype.isSubtype(elseType, thenType)) return thenType;

            throw new TypeError("Branches of 'if' have incompatible types: " + thenType + " and " + elseType);
        }

        if (node instanceof ASTLet let) {
            TypeEnv extended = env.copy();

            for (Bind b : let.decls) {
                String id = b.getId();
                ASTNode expr = b.getExp();

                ASTType exprType = typeOf(expr, extended);
                extended.add(id, exprType);
            }

            return typeOf(let.body, extended);
        }
        // Binary operations
        if (node instanceof ASTPlus p) return checkIntBinary(p.lhs, p.rhs, env, "+");
        if (node instanceof ASTSub p)  return checkIntBinary(p.lhs, p.rhs, env, "-");
        if (node instanceof ASTMult p) return checkIntBinary(p.lhs, p.rhs, env, "*");
        if (node instanceof ASTDiv p)  return checkIntBinary(p.lhs, p.rhs, env, "/");
        // Boolean operations
        if (node instanceof ASTEq p)         return checkIntComparison(p.getLhs(), p.getRhs(), env, "==");
        if (node instanceof ASTGreater p)    return checkIntComparison(p.getLhs(), p.getRhs(), env, ">");
        if (node instanceof ASTGreaterEq p)  return checkIntComparison(p.getLhs(), p.getRhs(), env, ">=");
        if (node instanceof ASTLess p)       return checkIntComparison(p.getLhs(), p.getRhs(), env, "<");
        if (node instanceof ASTLessEq p)     return checkIntComparison(p.getLhs(), p.getRhs(), env, "<=");
        // And/Or operations
        if (node instanceof ASTAnd p)        return checkBoolBinary(p.getLhs(), p.getRhs(), env, "&&");
        if (node instanceof ASTOr p)         return checkBoolBinary(p.getLhs(), p.getRhs(), env, "||");
        // Not operation
        if (node instanceof ASTNot p)        return checkBoolUnary(p.getOperand(), env, "~");

        throw new TypeError("Unhandled node in type checker: " + node.getClass());
    }

    private ASTType checkIntBinary(ASTNode lhs, ASTNode rhs, TypeEnv env, String opName) {
        ASTType t1 = TypeResolver.deepResolve(typeOf(lhs, env), typedefs);
        ASTType t2 = TypeResolver.deepResolve(typeOf(rhs, env), typedefs);

        if (!(t1 instanceof ASTTInt) || !(t2 instanceof ASTTInt)) {
            throw new TypeError("Both sides of '" + opName + "' must be int. Got: " + t1 + " and " + t2);
        }

        return new ASTTInt();
    }

    private ASTType checkIntComparison(ASTNode lhs, ASTNode rhs, TypeEnv env, String opName) {
        ASTType t1 = TypeResolver.deepResolve(typeOf(lhs, env), typedefs);
        ASTType t2 = TypeResolver.deepResolve(typeOf(rhs, env), typedefs);

        if (!(t1 instanceof ASTTInt) || !(t2 instanceof ASTTInt)) {
            throw new TypeError("Both sides of '" + opName + "' must be int. Got: " + t1 + " and " + t2);
        }

        return new ASTTBool();
    }

    private ASTType checkBoolBinary(ASTNode lhs, ASTNode rhs, TypeEnv env, String opName) {
        ASTType t1 = TypeResolver.deepResolve(typeOf(lhs, env), typedefs);
        ASTType t2 = TypeResolver.deepResolve(typeOf(rhs, env), typedefs);

        if (!(t1 instanceof ASTTBool) || !(t2 instanceof ASTTBool)) {
            throw new TypeError("Both sides of '" + opName + "' must be bool. Got: " + t1 + " and " + t2);
        }

        return new ASTTBool();
    }

    private ASTType checkBoolUnary(ASTNode operand, TypeEnv env, String opName) {
    ASTType t = TypeResolver.deepResolve(typeOf(operand, env), typedefs);
    if (!(t instanceof ASTTBool)) {
        throw new TypeError("Operand of '" + opName + "' must be bool. Got: " + t);
    }
    return new ASTTBool();
    }

    
}
