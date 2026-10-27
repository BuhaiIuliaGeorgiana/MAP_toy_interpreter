package interpreter.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.exp.VarExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VarExpTest {

    @Test
    void testVarExpInt() throws MyException {
        MyDictionary<String, IValue> dict = new MyDictionary<>();
        dict.put("x", new IntValue(10));

        VarExp exp = new VarExp("x");

        assertEquals(new IntValue(10), exp.eval(dict));
    }

    @Test
    void testVarExpBool() throws MyException {
        MyDictionary<String, IValue> dict = new MyDictionary<>();
        dict.put("a", new BoolValue(true));

        VarExp exp = new VarExp("a");

        assertEquals(new BoolValue(true), exp.eval(dict));
    }

    @Test
    void testUndefinedVariableThrows() {
        VarExp exp = new VarExp("x");

        ExpressionException ex = assertThrows(
                ExpressionException.class,
                () -> exp.eval(new MyDictionary<>()));

        assertEquals("variable x is not defined", ex.getMessage());
    }

    @Test
    void testToString() {
        VarExp exp = new VarExp("x");

        assertEquals("x", exp.toString());
    }
}