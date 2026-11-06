package interpreter.repository;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.RepositoryException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> prg;
    private final String logFilePath;

    public Repository(PrgState state, String logFilePath){
        this.logFilePath = logFilePath;
        this.prg = new ArrayList<>();
        this.prg.add(state);
    }
    @Override
    public PrgState getCrtPrg() {
        return this.prg.getLast();
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try (var logFile = new PrintWriter(
                new BufferedWriter(new FileWriter(logFilePath, true)))) { // append
            logFile.println(getCrtPrg().toLogString());
        } catch (IOException e) {
            throw new RepositoryException("cannot write the log file " + logFilePath
                    + ": " + e.getMessage());
        }
    }

    
}
