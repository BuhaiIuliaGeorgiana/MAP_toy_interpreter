
package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.IExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {

        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName))
            throw new StatementException("readFile: " + exp + " is not a string");

        var symTable = state.getSymTable();

        if (!symTable.isDefined(varName))
            throw new StatementException("variable " + varName + " is not declared");


        if (!(symTable.lookup(varName) instanceof IntValue))
            throw new StatementException("variable " + varName + " must be an integer");

        var fileTable = state.getFileTable();

        if (!fileTable.isDefined(fileName))
            throw new StatementException("readFile: " + fileName + " is not open");


        try {
            BufferedReader reader = fileTable.lookup(fileName);
            String line = reader.readLine();

            int value;

            if (line == null)
                value = 0;
            else
                value = Integer.parseInt(line.strip());


            symTable.update(varName, new IntValue(value));

        } catch (IOException | NumberFormatException e) {
            throw new StatementException("readFile: " + e.getMessage());
        }

        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}
