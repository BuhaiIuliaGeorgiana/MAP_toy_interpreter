package interpreter.model.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record WriteHeapStmt(String varName, IExp exp)
        implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {

        var symTable = state.getSymTable();
        var heap = state.getHeap();

        if (!symTable.isDefined(varName)) {
            throw new StatementException(
                    "wH: variable is not declared");
        }

        if (!(symTable.lookup(varName) instanceof RefValue ref)) {
            throw new StatementException(
                    "wH: variable is not a reference");
        }

        if (!heap.isDefined(ref.address())) {
            throw new StatementException(
                    "wH: address is not in the heap");
        }

        IValue val = exp.eval(symTable, heap);

        if (!val.getType().equals(ref.locationType())) {
            throw new StatementException(
                    "wH: incorrect value type");
        }

        heap.update(ref.address(), val);

        return state;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp + ")";
    }
}