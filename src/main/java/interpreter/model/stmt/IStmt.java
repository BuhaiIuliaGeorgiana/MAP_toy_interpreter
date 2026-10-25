package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;

public interface IStmt {
    giPrgState execute(PrgState state) throws MyException;
}
