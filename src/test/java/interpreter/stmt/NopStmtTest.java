package interpreter.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NopStmtTest {
    @Test
    void testDoesNotChangeProgramState() throws MyException {
        NopStmt s = new NopStmt();
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getSymTable().put("x", new IntValue(5));
        state.getOut().add(new IntValue(2));

        assertSame(state, s.execute(state));
        assertEquals(new IntValue(5), state.getSymTable().lookup("x"));
        assertEquals(new IntValue(2), state.getOut().get(0));
        assertEquals(1, state.getOut().size());
        assertSame(s, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testToString() {
        NopStmt s = new NopStmt();
        assertEquals("nop", s.toString());
    }
}
