
package interpreter.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.CloseRFileStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.OpenRFileStmt;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class CloseRFileStmtTest {
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
    void testCloseFile() throws MyException {
        new OpenRFileStmt(file).execute(state);
        CloseRFileStmt s = new CloseRFileStmt(file);

        assertSame(state, s.execute(state));
        assertFalse(state.getFileTable().isDefined(fileName));
    }

    @Test
    void testCloseFileNotOpen() {
        CloseRFileStmt s = new CloseRFileStmt(file);

        assertThrows(StatementException.class, () -> s.execute(state));
    }

    @Test
    void testCloseTwice() throws MyException {
        new OpenRFileStmt(file).execute(state);
        CloseRFileStmt s = new CloseRFileStmt(file);

        s.execute(state);

        assertThrows(StatementException.class, () -> s.execute(state));
    }

    @Test
    void testNonStringExpression() {
        CloseRFileStmt s = new CloseRFileStmt(
                new ValueExp(new IntValue(5))
        );

        assertThrows(StatementException.class, () -> s.execute(state));
    }

    @Test
    void testIOException() throws MyException {
        BufferedReader reader = new BufferedReader(new StringReader("7")) {
            @Override
            public void close() throws IOException {
                throw new IOException("simulated close error");
            }
        };

        state.getFileTable().put(fileName, reader);

        CloseRFileStmt s = new CloseRFileStmt(file);

        assertThrows(StatementException.class, () -> s.execute(state));
        assertTrue(state.getFileTable().isDefined(fileName));
    }

    @Test
    void testToString() {
        CloseRFileStmt s = new CloseRFileStmt(new VarExp("varf"));

        assertEquals("closeRFile(varf)", s.toString());
    }
}
