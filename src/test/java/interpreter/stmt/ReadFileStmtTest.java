
package interpreter.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.OpenRFileStmt;
import interpreter.model.stmt.ReadFileStmt;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ReadFileStmtTest {
    @TempDir
    Path dir;

    private PrgState state;
    private ValueExp file;
    private StringValue fileName;

    @BeforeEach
    void setUp() throws Exception {
        Path path = dir.resolve("numbers.in");
        Files.writeString(path, "7\n13\n");

        fileName = new StringValue(path.toString());
        file = new ValueExp(fileName);
        state = PrgState.of(new NopStmt());
        state.getSymTable().put("x", new IntValue(0));
    }

    @AfterEach
    void tearDown() {
        for (var reader : state.getFileTable().toMap().values()) {
            try {
                reader.close();
            } catch (IOException ignored) {
            }
        }
    }

    @Test
    void testReadNumbers() throws MyException {
        new OpenRFileStmt(file).execute(state);
        ReadFileStmt s = new ReadFileStmt(file, "x");

        assertSame(state, s.execute(state));
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));

        s.execute(state);
        assertEquals(new IntValue(13), state.getSymTable().lookup("x"));
    }

    @Test
    void testEndOfFile() throws MyException {
        new OpenRFileStmt(file).execute(state);
        ReadFileStmt s = new ReadFileStmt(file, "x");

        s.execute(state);
        s.execute(state);
        s.execute(state);

        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void testEmptyFile() throws Exception {
        Path path = dir.resolve("empty.in");
        Files.writeString(path, "");

        ValueExp emptyFile = new ValueExp(
                new StringValue(path.toString())
        );

        new OpenRFileStmt(emptyFile).execute(state);
        new ReadFileStmt(emptyFile, "x").execute(state);

        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void testUndeclaredVariable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        ReadFileStmt s = new ReadFileStmt(file, "missing");

        assertThrows(StatementException.class, () -> s.execute(state));
    }

    @Test
    void testVariableNotInt() throws MyException {
        state.getSymTable().put("flag", new BoolValue(true));
        new OpenRFileStmt(file).execute(state);

        ReadFileStmt s = new ReadFileStmt(file, "flag");

        assertThrows(StatementException.class, () -> s.execute(state));
    }

    @Test
    void testNonStringExpression() {
        ReadFileStmt s = new ReadFileStmt(
                new ValueExp(new IntValue(5)), "x"
        );

        assertThrows(StatementException.class, () -> s.execute(state));
    }

    @Test
    void testFileNotOpen() {
        ReadFileStmt s = new ReadFileStmt(file, "x");

        assertThrows(StatementException.class, () -> s.execute(state));
    }

    @Test
    void testInvalidInteger() throws Exception {
        Path path = dir.resolve("invalid.in");
        Files.writeString(path, "abc\n");

        ValueExp invalidFile = new ValueExp(
                new StringValue(path.toString())
        );

        new OpenRFileStmt(invalidFile).execute(state);

        ReadFileStmt s = new ReadFileStmt(invalidFile, "x");

        assertThrows(StatementException.class, () -> s.execute(state));
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void testIOException() throws Exception {
        new OpenRFileStmt(file).execute(state);

        // Close the reader without removing it from FileTable
        state.getFileTable().lookup(fileName).close();

        ReadFileStmt s = new ReadFileStmt(file, "x");

        assertThrows(StatementException.class, () -> s.execute(state));
    }

    @Test
    void testToString() {
        ReadFileStmt s = new ReadFileStmt(new VarExp("varf"), "x");

        assertEquals("readFile(varf, x)", s.toString());
    }
}
