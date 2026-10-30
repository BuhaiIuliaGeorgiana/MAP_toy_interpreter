package interpreter.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.BoolType;
import interpreter.model.type.IType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VarDeclStmtTest {
    @Test
    void testDeclareInt() throws MyException {
        VarDeclStmt s = new VarDeclStmt("x", new IntType());
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        assertSame(state, s.execute(state));
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void testDeclareBool() throws MyException {
        VarDeclStmt s = new VarDeclStmt("ok", new BoolType());
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        s.execute(state);
        assertEquals(new BoolValue(false), state.getSymTable().lookup("ok"));
    }

    @Test
    void testDuplicateDeclarationThrows() throws MyException {
        VarDeclStmt s = new VarDeclStmt("x", new IntType());
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();
        s.execute(state);

        StatementException ex = assertThrows(StatementException.class, () -> s.execute(state));
        assertEquals("variable x was already declared", ex.getMessage());
    }

    @Test
    void testInvalidTypeThrows() throws MyException {
        IType invalidType = new IType() {
            @Override
            public IValue defaultValue() { return new IntValue(0); }

            @Override
            public String toString() { return "string"; }
        };
        VarDeclStmt s = new VarDeclStmt("x", invalidType);
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);
        state.getExeStack().pop();

        assertThrows(StatementException.class, () -> s.execute(state));
        assertFalse(state.getSymTable().isDefined("x"));
    }

    @Test
    void testToString() {
        VarDeclStmt s = new VarDeclStmt("x", new IntType());
        assertEquals("int x", s.toString());
    }
}
