package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.IExp;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;

import java.util.Objects;

import static jdk.dynalink.linker.support.Guards.isInstance;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) {
    public PrgState execute(PrgState state) throws MyException {
        if(!(cond.eval(state.getSymTable()) instanceof BoolValue)){
            throw new StatementException("the condition is not a boolean");
        }
        if (cond.eval(state.getSymTable()).equals(new BoolValue(true))) {
            thenS.execute(state);
        }else{
            elseS.execute(state);

        }
        return state;
    }


}
