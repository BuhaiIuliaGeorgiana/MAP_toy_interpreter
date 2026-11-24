package interpreter;

import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;
import java.util.TreeMap;
import java.io.BufferedReader;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private final IStmt originalProgram;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IHeap heap;
    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable,
                    IList<IValue> out,IDictionary<StringValue, BufferedReader> fileTable, IStmt program,IHeap heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = program;
        this.fileTable= fileTable;
        this.heap = heap;

        exeStack.push(program);
    }


    public static PrgState of(IStmt program) {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                new MyDictionary<>(), program,new MyHeap());
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public IHeap getHeap(){ return heap;}

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setExeStack(IStack<IStmt> newStack){
        this.exeStack = newStack;
    }

    public void setSymTable(IDictionary<String,IValue> newDict){
        this.symTable = newDict;
    }


    public void setOut(IList<IValue> newOut){
        this.out = newOut;
    }

    @Override
    public String toString(){
        return "Out:\n" + out.toString() + "\n SymTable: \n" + symTable.toString() + "\n ExeStack: \n" + exeStack.toString();
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {  return fileTable; }

    public String toLogString() {
        var sb = new StringBuilder();
        sb.append("ExeStack:\n");
        exeStack.toList().forEach(s -> sb.append(s).append('\n'));
        sb.append("SymTable:\n");
        symTable.toMap().forEach((k, v) ->
                sb.append(k).append(" --> ").append(v).append('\n'));
        sb.append("Out:\n");
        out.toList().forEach(v -> sb.append(v).append('\n'));
        sb.append("FileTable:\n");
        fileTable.toMap().keySet().forEach(f -> sb.append(f.val()).append('\n'));

        sb.append("Heap:\n");

        new TreeMap<>(heap.getContent()).forEach((k, v) ->
                sb.append(k).append(" --> ").append(v).append('\n'));

        return sb.toString();
    }
}
