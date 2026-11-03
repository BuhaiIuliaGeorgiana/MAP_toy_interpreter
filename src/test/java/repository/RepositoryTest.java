package repository;

import interpreter.PrgState;
import interpreter.model.adt.*;
import interpreter.model.stmt.NopStmt;

import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {

    @Test
    void testGetCurrentProgram() {
        NopStmt s = new NopStmt();

       PrgState state = PrgState.of(s);

        Repository repo = new Repository(state);

        assertSame(state, repo.getCrtPrg());
    }
}