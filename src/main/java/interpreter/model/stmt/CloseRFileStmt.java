package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName))
            throw new StatementException("closeRFile: " + exp + " is not a string");
        var fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName))
            throw new StatementException("closeRFile: " + fileName + " is already closed or wasn't opened");
        try{
            BufferedReader reader = fileTable.lookup(fileName);
            reader.close();
            fileTable.remove(fileName);
        }catch (IOException e){
            throw new StatementException("closeRFile: cannot close" + fileName
                    + ": " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}
