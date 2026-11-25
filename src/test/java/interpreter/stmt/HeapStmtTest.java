package interpreter.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.NewStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HeapStmtTest {
private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());

        new VarDeclStmt("v",
                new RefType(new IntType())).execute(state);
    }

    @Test
    void newAllocatesAtAddressOneAndUpdatesTheVariable()
            throws MyException {

        new NewStmt("v",
                new ValueExp(new IntValue(20))).execute(state);

        assertEquals(new RefValue(1, new IntType()),
                state.getSymTable().lookup("v"));

        assertEquals(new IntValue(20),
                state.getHeap().get(1));
    }

    @Test
    void newWithAValueOfTheWrongTypeThrows() {
        var exp = new ValueExp(new BoolValue(true));

        assertThrows(StatementException.class,
                () -> new NewStmt("v", exp).execute(state));
    }

    @Test
    void newWithUndeclaredVariableThrows() {
        var exp = new ValueExp(new IntValue(20));

        assertThrows(StatementException.class,
                () -> new NewStmt("x", exp).execute(state));
    }

    @Test
    void newWithNonReferenceVariableThrows() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);

        var exp = new ValueExp(new IntValue(20));

        assertThrows(StatementException.class,
                () -> new NewStmt("x", exp).execute(state));
    }

    @Test
    void newAllocatesAnotherAddress() throws MyException {
        new NewStmt("v",
                new ValueExp(new IntValue(20))).execute(state);

        new NewStmt("v",
                new ValueExp(new IntValue(30))).execute(state);

        assertEquals(new RefValue(2, new IntType()),
                state.getSymTable().lookup("v"));

        assertEquals(new IntValue(20), state.getHeap().get(1));
        assertEquals(new IntValue(30), state.getHeap().get(2));
    }

    @Test
    void newWithNestedReference() throws MyException {
        new NewStmt("v",
                new ValueExp(new IntValue(20))).execute(state);

        new VarDeclStmt("a",
                new RefType(new RefType(new IntType())))
                .execute(state);

        new NewStmt("a", new VarExp("v")).execute(state);

        assertEquals(
                new RefValue(2, new RefType(new IntType())),
                state.getSymTable().lookup("a"));

        assertEquals(new RefValue(1, new IntType()),
                state.getHeap().get(2));
    }

}