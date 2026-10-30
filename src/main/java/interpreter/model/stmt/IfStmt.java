package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue value = cond.eval(state.getSymTable());

        if (!(value instanceof BoolValue b))
            throw new StatementException("the condition is not a boolean");

        if (b.val())
            state.getExeStack().push(thenS);
        else
            state.getExeStack().push(elseS);

        return state;
    }

    @Override
    public String toString() {
        return "if " + cond + " then " + thenS + " else " + elseS;
    }
}