package view;

import interpreter.PrgState;
import interpreter.controller.Controller;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.RunExampleCommand;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class RunExampleCommandTest {

    @Test
    void testInitialization() {
        PrintStmt stmt = new PrintStmt(new ValueExp(new IntValue(5)));
        PrgState state = PrgState.of(stmt);
        Controller controller = new Controller(new Repository(state, "tests.txt"), false);

        RunExampleCommand command = new RunExampleCommand("1", "Run example", controller);

        assertEquals("1", command.getKey());
        assertEquals("Run example", command.getDescription());
    }

    @Test
    void testExecute() {
        PrintStmt stmt = new PrintStmt(new ValueExp(new IntValue(5)));
        PrgState state = PrgState.of(stmt);
        Controller controller = new Controller(new Repository(state, "tests.txt"), false);

        RunExampleCommand command = new RunExampleCommand("1", "Run example", controller);

        assertDoesNotThrow(command::execute);
    }

    @Test
    void testExecuteOnlyOnce() {
        PrintStmt stmt = new PrintStmt(new ValueExp(new IntValue(5)));
        PrgState state = PrgState.of(stmt);
        Controller controller = new Controller(new Repository(state, "tests.txt"), false);

        RunExampleCommand command = new RunExampleCommand("1", "Run example", controller);

        command.execute();

        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output));

            command.execute();

            assertTrue(output.toString().contains(
                    "This example has already been executed."
            ));
        } finally {
            System.setOut(originalOut);
        }
    }
}