package interpreter.repository;

import interpreter.PrgState;
import interpreter.exceptions.MyException;

public interface IRepository {
    public PrgState getCrtPrg();
    void logPrgStateExec() throws MyException;
}
