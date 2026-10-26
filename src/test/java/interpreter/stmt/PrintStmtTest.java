package interpreter.stmt;

import interpreter.PrgState;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrintStmtTest {
    @Test
    void testPrintInt() throws MyException {
        PrintStmt s = new PrintStmt(new ValueExp(new IntValue(5)));
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        assertSame(state, s.execute(state));
        assertEquals(new IntValue(5), state.getOut().get(0));
        assertEquals(1, state.getOut().size());
    }

    @Test
    void testPrintBool() throws MyException {
        PrintStmt s = new PrintStmt(new ValueExp(new BoolValue(true)));
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        s.execute(state);
        assertEquals(new BoolValue(true), state.getOut().get(0));
    }

    @Test
    void testPrintPreservesOrder() throws MyException {
        PrintStmt s1 = new PrintStmt(new ValueExp(new IntValue(2)));
        PrintStmt s2 = new PrintStmt(new ValueExp(new IntValue(3)));
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s1);
        state.getExeStack().pop();

        s1.execute(state);
        s2.execute(state);
        assertEquals(new IntValue(2), state.getOut().get(0));
        assertEquals(new IntValue(3), state.getOut().get(1));
        assertEquals(2, state.getOut().size());
    }

    @Test
    void testInvalidExpressionThrows() throws MyException {
        ArithExp exp = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(0)));
        PrintStmt s = new PrintStmt(exp);
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        assertThrows(ExpressionException.class, () -> s.execute(state));
        assertEquals(0, state.getOut().size());
    }
}
