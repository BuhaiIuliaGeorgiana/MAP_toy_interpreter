package interpreter.exp;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WriteHeapStmtTest {

    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());

        new VarDeclStmt("v", new RefType(new IntType()))
                .execute(state);
    }

    @Test
    void testWriteHeap() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20)))
                .execute(state);

        new WriteHeapStmt("v", new ValueExp(new IntValue(30)))
                .execute(state);

        assertEquals(new IntValue(30), state.getHeap().get(1));

        assertEquals(new RefValue(1, new IntType()),
                state.getSymTable().lookup("v"));

        assertEquals(1, state.getHeap().getContent().size());
    }

    @Test
    void testUndeclaredVariableThrows() {
        WriteHeapStmt s = new WriteHeapStmt(
                "x", new ValueExp(new IntValue(30)));

        assertThrows(StatementException.class,
                () -> s.execute(state));
    }

    @Test
    void testNonReferenceThrows() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);

        WriteHeapStmt s = new WriteHeapStmt(
                "x", new ValueExp(new IntValue(30)));

        assertThrows(StatementException.class,
                () -> s.execute(state));
    }

    @Test
    void testInvalidAddressThrows() throws MyException {
        state.getSymTable().update("v",
                new RefValue(10, new IntType()));

        WriteHeapStmt s = new WriteHeapStmt(
                "v", new ValueExp(new IntValue(30)));

        assertThrows(StatementException.class,
                () -> s.execute(state));
    }

    @Test
    void testNullReferenceThrows() {
        WriteHeapStmt s = new WriteHeapStmt(
                "v", new ValueExp(new IntValue(30)));

        assertThrows(StatementException.class,
                () -> s.execute(state));
    }

    @Test
    void testWrongTypeThrows() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20)))
                .execute(state);

        WriteHeapStmt s = new WriteHeapStmt(
                "v", new ValueExp(new BoolValue(true)));

        assertThrows(StatementException.class,
                () -> s.execute(state));

        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    void testMultipleWrites() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20)))
                .execute(state);

        new WriteHeapStmt("v", new ValueExp(new IntValue(30)))
                .execute(state);

        new WriteHeapStmt("v", new ValueExp(new IntValue(40)))
                .execute(state);

        assertEquals(new IntValue(40), state.getHeap().get(1));
        assertEquals(1, state.getHeap().getContent().size());
    }

    @Test
    void testToString() {
        WriteHeapStmt s = new WriteHeapStmt(
                "v", new VarExp("x"));

        assertEquals("wH(v, x)", s.toString());
    }
}