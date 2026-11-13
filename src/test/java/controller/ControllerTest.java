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
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
public class ControllerTest {

    @TempDir
    Path dir;

    private PrgState runProgram(IStmt program, String logName) throws MyException {
        PrgState state = PrgState.of(program);
        Repository repo = new Repository(state, dir.resolve(logName).toString());
        Controller c = new Controller(repo, false);

        c.allStep();
        return state;
    }

    @Test
    void testExample4() throws Exception {
        Path file = dir.resolve("numbers.in");
        Files.writeString(file, "15\n50\n");

        PrgState state = runProgram(
                Examples.fileExample(file.toString()), "log4.txt"
        );

        assertEquals(new IntValue(15), state.getOut().get(0));
        assertEquals(new IntValue(50), state.getOut().get(1));
        assertEquals(2, state.getOut().size());
        assertTrue(state.getFileTable().toMap().isEmpty());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testExample4EmptyFile() throws Exception {
        Path file = dir.resolve("empty.in");
        Files.writeString(file, "");

        PrgState state = runProgram(
                Examples.fileExample(file.toString()), "logEmpty.txt"
        );

        assertEquals(new IntValue(0), state.getOut().get(0));
        assertEquals(new IntValue(0), state.getOut().get(1));
        assertEquals(2, state.getOut().size());
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void testExample5() throws MyException {
        PrgState state = runProgram(Examples.ex5(), "log5.txt");

        assertEquals(new IntValue(5), state.getOut().get(0));
        assertEquals(1, state.getOut().size());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testLogEveryStep() throws Exception {
        IStmt program = new CompStmt(
                new PrintStmt(new ValueExp(new IntValue(1))),
                new PrintStmt(new ValueExp(new IntValue(2)))
        );

        runProgram(program, "steps.txt");

        String log = Files.readString(dir.resolve("steps.txt"));

        long count = log.lines()
                .filter(line -> line.equals("ExeStack:"))
                .count();

        // Initial state + CompStmt + first print + second print
        assertEquals(4, count);
    }


    @Test
    void testOneStep() throws MyException {
        PrintStmt s = new PrintStmt(new ValueExp(new IntValue(5)));
        PrgState state = PrgState.of(s);

        Controller c = new Controller(new Repository(state,dir.resolve("testLog.txt").toString()), false);

        assertSame(state, c.oneStep(state));
        assertEquals(new IntValue(5), state.getOut().get(0));
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testOneStepEmptyStackThrows() throws MyException {
        NopStmt s = new NopStmt();
        PrgState state = PrgState.of(s);

        state.getExeStack().pop();

        Controller c = new Controller(new Repository(state,dir.resolve("testLog.txt").toString()), false);

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
        PrgState state = PrgState.of(Examples.ex2());

        Controller c = new Controller(new Repository(state,dir.resolve("testLog.txt").toString()), false);
        c.allStep();

        assertEquals(new IntValue(18), state.getOut().get(0));
        assertEquals(1, state.getOut().size());
    }

    @Test
    void testExample3() throws MyException {
        PrgState state = PrgState.of(Examples.ex3());
        Controller c = new Controller(new Repository(state,dir.resolve("testLog.txt").toString()), false);
        c.allStep();

        assertEquals(new IntValue(2), state.getOut().get(0));
        assertEquals(1, state.getOut().size());
    }
}