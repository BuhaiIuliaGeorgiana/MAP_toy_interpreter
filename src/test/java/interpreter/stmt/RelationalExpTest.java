package interpreter.stmt;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private void check(RelOp op, int left, int right, boolean expected) throws MyException {
        RelationalExp exp = new RelationalExp(
                op,
                new ValueExp(new IntValue(left)),
                new ValueExp(new IntValue(right))
        );

        IValue result = exp.eval(symTable);

        assertEquals(new BoolValue(expected), result);
        assertEquals(new BoolType(), result.getType());
    }

    @Test
    void testLTTrue() throws MyException {
        check(RelOp.LT, 2, 5, true);
    }

    @Test
    void testLTFalse() throws MyException {
        check(RelOp.LT, 5, 2, false);
    }

    @Test
    void testLETrue() throws MyException {
        check(RelOp.LE, 5, 5, true);
    }

    @Test
    void testLEFalse() throws MyException {
        check(RelOp.LE, 7, 5, false);
    }

    @Test
    void testEQTrue() throws MyException {
        check(RelOp.EQ, 4, 4, true);
    }

    @Test
    void testEQFalse() throws MyException {
        check(RelOp.EQ, 4, 6, false);
    }

    @Test
    void testNETrue() throws MyException {
        check(RelOp.NE, 4, 6, true);
    }

    @Test
    void testNEFalse() throws MyException {
        check(RelOp.NE, 3, 3, false);
    }

    @Test
    void testGTTrue() throws MyException {
        check(RelOp.GT, 8, 2, true);
    }

    @Test
    void testGTFalse() throws MyException {
        check(RelOp.GT, 2, 8, false);
    }

    @Test
    void testGETrue() throws MyException {
        check(RelOp.GE, 5, 5, true);
    }

    @Test
    void testGEFalse() throws MyException {
        check(RelOp.GE, 2, 5, false);
    }

    @Test
    void testFirstOperandNotInt() {
        RelationalExp exp = new RelationalExp(
                RelOp.LT,
                new ValueExp(new StringValue("abc")),
                new ValueExp(new IntValue(5))
        );

        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }

    @Test
    void testSecondOperandNotInt() {
        RelationalExp exp = new RelationalExp(
                RelOp.GT,
                new ValueExp(new IntValue(5)),
                new ValueExp(new StringValue("abc"))
        );

        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }
}