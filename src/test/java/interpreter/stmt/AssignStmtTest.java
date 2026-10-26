package interpreter.stmt;

import interpreter.PrgState;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssignStmtTest {
    @Test
    void testAssignInt() throws MyException {
        AssignStmt s = new AssignStmt("x", new ValueExp(new IntValue(5)));
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();
        state.getSymTable().put("x", new IntValue(0));

        assertSame(state, s.execute(state));
        assertEquals(new IntValue(5), state.getSymTable().lookup("x"));
    }

    @Test
    void testAssignBool() throws MyException {
        AssignStmt s = new AssignStmt("b", new ValueExp(new BoolValue(true)));
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();
        state.getSymTable().put("b", new BoolValue(false));

        s.execute(state);
        assertEquals(new BoolValue(true), state.getSymTable().lookup("b"));
    }

    @Test
    void testUpdateExistingValue() throws MyException {
        AssignStmt s = new AssignStmt("x", new ValueExp(new IntValue(8)));
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();
        state.getSymTable().put("x", new IntValue(3));

        s.execute(state);
        assertEquals(new IntValue(8), state.getSymTable().lookup("x"));
    }

    @Test
    void testUndeclaredVariableThrows() {
        AssignStmt s = new AssignStmt("x", new ValueExp(new IntValue(5)));
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);

        StatementException ex = assertThrows(StatementException.class, () -> s.execute(state));
        assertEquals("variable x was not declared", ex.getMessage());
    }

    @Test
    void testTypeMismatchThrows() throws MyException {
        AssignStmt s = new AssignStmt("x", new ValueExp(new BoolValue(true)));
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();
        state.getSymTable().put("x", new IntValue(0));

        assertThrows(StatementException.class, () -> s.execute(state));
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void testInvalidExpressionThrows() throws MyException {
        ArithExp exp = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(0)));
        AssignStmt s = new AssignStmt("x", exp);
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();
        state.getSymTable().put("x", new IntValue(0));

        assertThrows(ExpressionException.class, () -> s.execute(state));
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void testToString() {
        AssignStmt s = new AssignStmt("x", new ValueExp(new IntValue(5)));
        assertEquals("x = 5", s.toString());
    }
}
