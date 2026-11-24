package interpreter.stmt;

import interpreter.PrgState;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.NewStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.stmt.WhileStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HeapStmtTest {
private PrgState state;

@BeforeEach
void setUp() throws MyException {
    state = PrgState.of(new NopStmt());
    new VarDeclStmt("v", new RefType(new IntType())).execute(state);   // Ref int v
}

@Test
void newAllocatesAtAddressOneAndUpdatesTheVariable() throws MyException {
    new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
    assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
    assertEquals(new IntValue(20), state.getHeap().get(1));
}

@Test
void newWithAValueOfTheWrongTypeThrows() {
    var exp = new ValueExp(new BoolValue(true));
    assertThrows(StatementException.class, () -> new NewStmt("v", exp).execute(state));
}

@Test
void whileWithANonBooleanConditionThrows() {
    var loop = new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());
    assertThrows(StatementException.class, () -> loop.execute(state));
}
}