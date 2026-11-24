package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.type.*;

public record VarDeclStmt(String name, IType type)
        implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();

        if (symTable.isDefined(name)) {
            throw new StatementException(
                    "variable " + name + " was already declared");
        }

        if (!isValidType(type)) {
            throw new StatementException("invalid type");
        }

        symTable.put(name, type.defaultValue());

        return state;
    }
    @Override
    public String toString() {
        return type + " " + name;
    }

    private boolean isValidType(IType type) {
        if (type instanceof IntType ||
                type instanceof BoolType ||
                type instanceof StringType) {
            return true;
        }

        if (type instanceof RefType ref) {
            return isValidType(ref.inner());
        }

        return false;
    }
}