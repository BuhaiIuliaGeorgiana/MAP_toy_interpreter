package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;

public record NopStmt() implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return state;
    }
}
