package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.type.BoolType;
import interpreter.model.type.IType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record VarDeclStmt(String name, IType type) implements IStmt {
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if (symTable.isDefined(name))
            throw new StatementException("variable " + name + " was already declared");
        if (!type.toString().equals("bool") && !type.toString().equals("int")){
            throw new StatementException("invalid type (must be bool or int)");
        }

        symTable.put(name, type.defaultValue());


        return state;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
