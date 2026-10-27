package interpreter.repository;

import interpreter.PrgState;
import interpreter.model.adt.*;
import interpreter.model.stmt.NopStmt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {

    @Test
    void testGetCurrentProgram() {
        NopStmt s = new NopStmt();

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                s
        );

        Repository repo = new Repository(state);

        assertSame(state, repo.getCrtPrg());
    }
}