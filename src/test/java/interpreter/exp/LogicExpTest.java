package interpreter.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.exp.LogicExp;
import interpreter.model.exp.LogicOP;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class LogicExpTest {
    @Test
    void TestAdd() throws MyException {
        LogicExp l = new LogicExp(LogicOP.AND,new ValueExp(new BoolValue(true)),new ValueExp(new BoolValue(true)));
        assertEquals(new BoolValue(true),l.eval(new MyDictionary<>()));
    }

    @Test
    void TestOr() throws MyException {
        LogicExp l = new LogicExp(LogicOP.OR,new ValueExp(new BoolValue(true)),new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(true),l.eval(new MyDictionary<>()));
    }

    @Test
    void operandNotBoolean() throws MyException {
        LogicExp l = new LogicExp(LogicOP.OR,new ValueExp(new IntValue(5)),new ValueExp(new BoolValue(false)));
        ExpressionException ex = assertThrows(ExpressionException.class, ()->l .eval(new MyDictionary<>()));

    }

}
