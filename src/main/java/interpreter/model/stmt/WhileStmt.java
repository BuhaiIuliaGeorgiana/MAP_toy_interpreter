package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record WhileStmt(IExp cond, IStmt body) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {

        IValue val = cond.eval(state.getSymTable(), state.getHeap());

        if (!(val instanceof BoolValue b)) {
            throw new StatementException("the condition is not boolean");
        }

        if (b.val()) {
            state.getExeStack().push(this);
            state.getExeStack().push(body);
        }

        return state;
    }

    @Override
    public String toString() {
        return "(while (" + cond + ") " + body + ")";
    }
}