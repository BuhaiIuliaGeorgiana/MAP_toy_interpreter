package interpreter.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.OpenRFileStmt;
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

public class OpenRFileStmtTest {
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
    void testOpenFile() throws MyException {
        OpenRFileStmt s = new OpenRFileStmt(file);

        assertSame(state, s.execute(state));
        assertTrue(state.getFileTable().isDefined(fileName));
    }

    @Test
    void testOpenSameFileTwice() throws MyException {
        OpenRFileStmt s = new OpenRFileStmt(file);
        s.execute(state);

        assertThrows(StatementException.class, () -> s.execute(state));
    }

    @Test
    void testNonStringExpression() {
        OpenRFileStmt s = new OpenRFileStmt(
                new ValueExp(new IntValue(5))
        );

        assertThrows(StatementException.class, () -> s.execute(state));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void testMissingFile() {
        StringValue missing = new StringValue(
                dir.resolve("missing.in").toString()
        );
        OpenRFileStmt s = new OpenRFileStmt(new ValueExp(missing));

        assertThrows(StatementException.class, () -> s.execute(state));
        assertFalse(state.getFileTable().isDefined(missing));
    }

    @Test
    void testToString() {
        OpenRFileStmt s = new OpenRFileStmt(new VarExp("varf"));

        assertEquals("openRFile(varf)", s.toString());
    }
}
