package controller;

import interpreter.PrgState;
import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.adt.*;
import interpreter.model.stmt.*;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

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
        assertEquals(7, count);
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



    @Test
    void testExample6() throws MyException {
        PrgState state = runProgram(Examples.ex6(), "log6.txt");

        assertEquals(new RefValue(1, new IntType()),
                state.getOut().get(0));

        assertEquals(new RefValue(2, new RefType(new IntType())),
                state.getOut().get(1));

        assertEquals(2, state.getOut().size());

        assertEquals(Map.of(
                1, new IntValue(20),
                2, new RefValue(1, new IntType())
        ), state.getHeap().getContent());

        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testExample7() throws MyException {
        PrgState state = runProgram(Examples.ex7(), "log7.txt");

        assertEquals(List.of(
                new IntValue(20),
                new IntValue(25)
        ), state.getOut().toList());

        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testExample8() throws MyException {
        PrgState state = runProgram(Examples.ex8(), "log8.txt");

        assertEquals(List.of(
                new IntValue(20),
                new IntValue(35)
        ), state.getOut().toList());

        assertEquals(new IntValue(30), state.getHeap().get(1));

        assertEquals(new RefValue(1, new IntType()),
                state.getSymTable().lookup("v"));

        assertEquals(1, state.getHeap().getContent().size());

        assertTrue(state.getExeStack().isEmpty());
    }


    @Test
    void testExample9() throws MyException {
        PrgState state = runProgram(Examples.ex9(), "log9.txt");

        assertEquals(List.of(new IntValue(20)),
                state.getOut().toList());

        assertEquals(Map.of(
                1, new IntValue(20),
                2, new RefValue(1, new IntType()),
                3, new IntValue(30)
        ), state.getHeap().getContent());

        assertEquals(new RefValue(3, new IntType()),
                state.getSymTable().lookup("v"));

        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testExample10() throws MyException {
        PrgState state = runProgram(Examples.ex10(), "log10.txt");

        assertEquals(List.of(
                new IntValue(4),
                new IntValue(3),
                new IntValue(2),
                new IntValue(1),
                new IntValue(0)
        ), state.getOut().toList());

        assertEquals(new IntValue(0),
                state.getSymTable().lookup("v"));

        assertTrue(state.getExeStack().isEmpty());
    }


    @Test
    void testGarbageCollectionRemovesUnreachableAddresses()
            throws MyException {

        IStmt program = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v",
                                new ValueExp(new IntValue(20))),
                        new NewStmt("v",
                                new ValueExp(new IntValue(30)))
                )
        );

        PrgState state = runProgram(program, "logGC.txt");

        assertFalse(state.getHeap().isDefined(1));

        assertEquals(Map.of(2, new IntValue(30)),
                state.getHeap().getContent());

        assertEquals(new RefValue(2, new IntType()),
                state.getSymTable().lookup("v"));
    }


}