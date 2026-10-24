package interpreter.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestValueExp {

    @Test
    void testEvalForIntegerValues() throws MyException {
        ValueExp e = new ValueExp(new IntValue(5));
        assertEquals(new IntValue(5),e.eval(new MyDictionary<>()));
    }

    @Test
    void testEvalForBooleanValues() throws MyException{
        ValueExp e = new ValueExp(new BoolValue(true));
        assertEquals(new BoolValue(true),e.eval(new MyDictionary<>()));
    }

    @Test
    void testToStringForBooleans(){
        ValueExp e = new ValueExp(new BoolValue(true));
        assertEquals(new BoolValue(true).toString(),e.toString());
    }

    @Test
    void testToStringForIntegers(){
        ValueExp e = new ValueExp(new IntValue(6));
        assertEquals(new IntValue(6).toString(),e.toString());
    }
}
