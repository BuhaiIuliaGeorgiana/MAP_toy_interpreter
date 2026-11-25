package interpreter.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.value.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WhileStmtTest {

    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void testFalseCondition() throws MyException {
        WhileStmt s = new WhileStmt(
                new ValueExp(new BoolValue(false)),
                new NopStmt());

        s.execute(state);

        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testTrueCondition() throws MyException {
        IStmt body = new PrintStmt(
                new ValueExp(new IntValue(20)));

        WhileStmt s = new WhileStmt(
                new ValueExp(new BoolValue(true)), body);

        s.execute(state);

        assertEquals(body, state.getExeStack().pop());
        assertEquals(s, state.getExeStack().pop());

        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testNonBooleanConditionThrows() {
        WhileStmt s = new WhileStmt(
                new ValueExp(new IntValue(1)),
                new NopStmt());

        assertThrows(StatementException.class,
                () -> s.execute(state));
    }

    @Test
    void testZeroIterations() throws MyException {
        testLoop(0);
    }

    @Test
    void testOneIteration() throws MyException {
        testLoop(1);
    }

    @Test
    void testMultipleIterations() throws MyException {
        testLoop(4);
    }

    private void testLoop(int initialValue) throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        new VarDeclStmt("count", new IntType()).execute(state);

        new AssignStmt("v",
                new ValueExp(new IntValue(initialValue)))
                .execute(state);

        WhileStmt s = new WhileStmt(
                new RelationalExp(
                        RelOp.GT,
                        new VarExp("v"),
                        new ValueExp(new IntValue(0))),
                new CompStmt(
                        new AssignStmt("v",
                                new ArithExp(
                                        ArithOp.SUB,
                                        new VarExp("v"),
                                        new ValueExp(new IntValue(1)))),
                        new AssignStmt("count",
                                new ArithExp(
                                        ArithOp.ADD,
                                        new VarExp("count"),
                                        new ValueExp(new IntValue(1))))
                )
        );

        state.getExeStack().push(s);

        int steps = 0;

        while (!state.getExeStack().isEmpty()) {
            assertTrue(steps++ < 100, "Possible infinite loop");

            state.getExeStack().pop().execute(state);
        }

        assertEquals(new IntValue(0),
                state.getSymTable().lookup("v"));

        assertEquals(new IntValue(initialValue),
                state.getSymTable().lookup("count"));
    }

    @Test
    void testToString() {
        WhileStmt s = new WhileStmt(
                new ValueExp(new BoolValue(true)),
                new NopStmt());

        assertEquals("(while (true) " + s.body() + ")",
                s.toString());
    }
}