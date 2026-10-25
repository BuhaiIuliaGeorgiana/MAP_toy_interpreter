package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.model.exp.IExp;

public record PrintStmt(IExp ex) implements IStmt{
    public PrgState execute(PrgState state) throws MyException{
        System.out.println(ex.eval(state.getSymTable()).toString());
        return state;
    }

}
