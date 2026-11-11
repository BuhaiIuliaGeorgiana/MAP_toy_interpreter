package repository;

import interpreter.PrgState;
import interpreter.model.adt.*;
import interpreter.model.stmt.NopStmt;

import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.RepositoryException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
public class RepositoryTest {

    @TempDir
    Path dir;

    @Test
    void testLogPrgState() throws Exception {
        PrgState state = PrgState.of(new NopStmt());
        state.getSymTable().put("x", new IntValue(5));
        state.getOut().add(new IntValue(5));

        Path logFile = dir.resolve("log.txt");
        Repository repo = new Repository(state, logFile.toString());

        repo.logPrgStateExec();

        String log = Files.readString(logFile);

        int a = log.indexOf("ExeStack:");
        int b = log.indexOf("SymTable:");
        int c = log.indexOf("Out:");
        int d = log.indexOf("FileTable:");

        assertTrue(0 <= a && a < b && b < c && c < d);
        assertTrue(log.contains("x --> 5"));
        assertTrue(log.contains("Out:\n5\n"));
    }

    @Test
    void testLogAppend() throws Exception {
        PrgState state = PrgState.of(new NopStmt());
        Path logFile = dir.resolve("log.txt");
        Repository repo = new Repository(state, logFile.toString());

        repo.logPrgStateExec();
        repo.logPrgStateExec();

        String log = Files.readString(logFile);

        long count = log.lines()
                .filter(line -> line.equals("ExeStack:"))
                .count();

        assertEquals(2, count);
    }

    @Test
    void testInvalidLogFile() {
        PrgState state = PrgState.of(new NopStmt());
        Path logFile = dir.resolve("missing").resolve("log.txt");
        Repository repo = new Repository(state, logFile.toString());

        assertThrows(RepositoryException.class, repo::logPrgStateExec);
    }

    @Test
    void testGetCurrentProgram() {
        NopStmt s = new NopStmt();

       PrgState state = PrgState.of(s);

        Repository repo = new Repository(state, "tests.txt");

        assertSame(state, repo.getCrtPrg());
    }
}