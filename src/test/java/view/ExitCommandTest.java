package view;

import interpreter.view.ExitCommand;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ExitCommandTest {

    @Test
    void testInitialization() {
        ExitCommand command = new ExitCommand("0", "Exit");

        assertEquals("0", command.getKey());
        assertEquals("Exit", command.getDescription());
    }

    @Test
    void testExecute() throws Exception {
        String java = System.getProperty("java.home")
                + File.separator + "bin" + File.separator + "java";

        String classpath =
                ExitCommandTest.class.getProtectionDomain()
                        .getCodeSource().getLocation().getPath()
                        + File.pathSeparator
                        + ExitCommand.class.getProtectionDomain()
                        .getCodeSource().getLocation().getPath();

        Process process = new ProcessBuilder(
                java, "-cp", classpath, ExitRunner.class.getName()
        ).start();

        int exitCode = process.waitFor();

        assertEquals(0, exitCode);
    }

    public static class ExitRunner {
        public static void main(String[] args) {
            new ExitCommand("0", "Exit").execute();
            System.exit(1); // Reached only if execute() did not exit
        }
    }
}