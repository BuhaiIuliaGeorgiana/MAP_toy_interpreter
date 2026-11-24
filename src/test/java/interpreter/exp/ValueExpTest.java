package interpreter.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValueExpTest {

    @Test
    void testIntValue() throws MyException {
        ValueExp exp = new ValueExp(new IntValue(10));
        assertEquals(new IntValue(10), exp.eval(new MyDictionary<>(), new MyHeap()));
    }

    @Test
    void testBoolValue() throws MyException {
        ValueExp exp = new ValueExp(new BoolValue(true));
        assertEquals(new BoolValue(true), exp.eval(new MyDictionary<>(), new MyHeap()));
    }
}