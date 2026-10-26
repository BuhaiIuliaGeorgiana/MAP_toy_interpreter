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
import interpreter.model.stmt.IfStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IfStmtTest {
    @Test
    void testTrueConditionPushesThenBranch() throws MyException {
        IStmt thenS = new PrintStmt(new ValueExp(new IntValue(2)));
        IStmt elseS = new PrintStmt(new ValueExp(new IntValue(3)));
        IfStmt s = new IfStmt(new ValueExp(new BoolValue(true)), thenS, elseS);
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        assertSame(state, s.execute(state));
        assertSame(thenS, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(0, state.getOut().size());
    }

    @Test
    void testFalseConditionPushesElseBranch() throws MyException {
        IStmt thenS = new PrintStmt(new ValueExp(new IntValue(2)));
        IStmt elseS = new PrintStmt(new ValueExp(new IntValue(3)));
        IfStmt s = new IfStmt(new ValueExp(new BoolValue(false)), thenS, elseS);
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        s.execute(state);
        assertSame(elseS, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(0, state.getOut().size());
    }

    @Test
    void testNonBooleanConditionThrows() throws MyException {
        IStmt thenS = new PrintStmt(new ValueExp(new IntValue(2)));
        IStmt elseS = new PrintStmt(new ValueExp(new IntValue(3)));
        IfStmt s = new IfStmt(new ValueExp(new IntValue(1)), thenS, elseS);
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        assertThrows(StatementException.class, () -> s.execute(state));
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testInvalidExpressionThrows() throws MyException {
        ArithExp exp = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(1)), new ValueExp(new IntValue(0)));
        IStmt thenS = new PrintStmt(new ValueExp(new IntValue(2)));
        IStmt elseS = new PrintStmt(new ValueExp(new IntValue(3)));
        IfStmt s = new IfStmt(exp, thenS, elseS);
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        assertThrows(ExpressionException.class, () -> s.execute(state));
        assertTrue(state.getExeStack().isEmpty());
    }
}
