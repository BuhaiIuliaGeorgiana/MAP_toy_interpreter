package controller;

import interpreter.PrgState;
import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.*;
import interpreter.model.stmt.*;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    @Test
    void testOneStep() throws MyException {
        PrintStmt s = new PrintStmt(new ValueExp(new IntValue(5)));
        PrgState state = new PrgState(
                new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);

        Controller c = new Controller(new Repository(state), false);

        assertSame(state, c.oneStep(state));
        assertEquals(new IntValue(5), state.getOut().get(0));
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testOneStepEmptyStackThrows() throws MyException {
        NopStmt s = new NopStmt();
        PrgState state = new PrgState(
                new MyStack<>(), new MyDictionary<>(), new MyList<>(), s);

        state.getExeStack().pop();

        Controller c = new Controller(new Repository(state), false);

        assertThrows(StatementException.class, () -> c.oneStep(state));
    }

    /*
    @Test
    void testExample1() throws MyException {
        PrgState state = new PrgState(
                new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), Examples.ex1());

        Controller c = new Controller(new Repository(state), false);
        c.allStep();

        assertEquals(new IntValue(2), state.getOut().get(0));
        assertEquals(1, state.getOut().size());
    }
    */


    @Test
    void testExample2() throws MyException {
        PrgState state = new PrgState(
                new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), Examples.ex2());

        Controller c = new Controller(new Repository(state), false);
        c.allStep();

        assertEquals(new IntValue(18), state.getOut().get(0));
        assertEquals(1, state.getOut().size());
    }

    @Test
    void testExample3() throws MyException {
        PrgState state = new PrgState(
                new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), Examples.ex3());

        Controller c = new Controller(new Repository(state), false);
        c.allStep();

        assertEquals(new IntValue(2), state.getOut().get(0));
        assertEquals(1, state.getOut().size());
    }
}