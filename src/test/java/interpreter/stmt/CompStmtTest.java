package interpreter.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompStmtTest {
    @Test
    void testPushesStatementsInCorrectOrder() throws MyException {
        IStmt first = new NopStmt();
        IStmt second = new PrintStmt(new ValueExp(new IntValue(5)));
        CompStmt s = new CompStmt(first, second);
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        s.execute(state);

        assertSame(first, state.getExeStack().pop());
        assertSame(second, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testDoesNotExecuteStatementsImmediately() throws MyException {
        IStmt first = new PrintStmt(new ValueExp(new IntValue(2)));
        IStmt second = new PrintStmt(new ValueExp(new IntValue(3)));
        CompStmt s = new CompStmt(first, second);
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        assertSame(state, s.execute(state));
        assertEquals(0, state.getOut().size());
        assertFalse(state.getExeStack().isEmpty());
    }

    @Test
    void testToString() {
        CompStmt s = new CompStmt(new NopStmt(), new NopStmt());
        assertEquals("(nop; nop)", s.toString());
    }
}
