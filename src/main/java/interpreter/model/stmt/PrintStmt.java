package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.model.exp.IExp;

public record PrintStmt(IExp ex) implements IStmt{
    public PrgState execute(PrgState state) throws MyException{
        state.getOut().add(ex.eval(state.getSymTable()));
        return state;
    }

    @Override
    public String toString() {
        return "print(" + ex + ")";
    }

}
