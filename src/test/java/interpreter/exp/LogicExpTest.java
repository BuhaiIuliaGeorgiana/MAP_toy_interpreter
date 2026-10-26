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
    void TestAndTrueTrue() throws MyException {
        LogicExp l = new LogicExp(LogicOP.AND,new ValueExp(new BoolValue(true)),new ValueExp(new BoolValue(true)));
        assertEquals(new BoolValue(true),l.eval(new MyDictionary<>()));
    }
    @Test
    void TestAndTrueFalse() throws MyException {
        LogicExp l = new LogicExp(LogicOP.AND,new ValueExp(new BoolValue(true)),new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(false),l.eval(new MyDictionary<>()));
    }

    @Test
    void TestAndFalseTrue() throws MyException {
        LogicExp l = new LogicExp(LogicOP.AND,new ValueExp(new BoolValue(false)),new ValueExp(new BoolValue(true)));
        assertEquals(new BoolValue(false),l.eval(new MyDictionary<>()));
    }

    @Test
    void TestAndFalseFalse() throws MyException {
        LogicExp l = new LogicExp(LogicOP.AND,new ValueExp(new BoolValue(false)),new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(false),l.eval(new MyDictionary<>()));
    }



    @Test
    void TestOrTrueFalse() throws MyException {
        LogicExp l = new LogicExp(LogicOP.OR,new ValueExp(new BoolValue(true)),new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(true),l.eval(new MyDictionary<>()));
    }

    @Test
    void TestOrFalseTrue() throws MyException {
        LogicExp l = new LogicExp(LogicOP.OR,new ValueExp(new BoolValue(false)),new ValueExp(new BoolValue(true)));
        assertEquals(new BoolValue(true),l.eval(new MyDictionary<>()));
    }


    @Test
    void TestOrTrueTrue() throws MyException {
        LogicExp l = new LogicExp(LogicOP.OR,new ValueExp(new BoolValue(true)),new ValueExp(new BoolValue(true)));
        assertEquals(new BoolValue(true),l.eval(new MyDictionary<>()));
    }
    @Test
    void TestOrFalseFalse() throws MyException {
        LogicExp l = new LogicExp(LogicOP.OR,new ValueExp(new BoolValue(false)),new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(false),l.eval(new MyDictionary<>()));
    }

    @Test
    void firstOperandNotBoolean() throws MyException {
        LogicExp l = new LogicExp(LogicOP.OR,new ValueExp(new IntValue(5)),new ValueExp(new BoolValue(false)));
        ExpressionException ex = assertThrows(ExpressionException.class, ()->l .eval(new MyDictionary<>()));

    }

    @Test
    void secondOperandNotBoolean() throws MyException {
        LogicExp l = new LogicExp(LogicOP.OR,new ValueExp(new BoolValue(false)),new ValueExp(new IntValue(5)));
        ExpressionException ex = assertThrows(ExpressionException.class, ()->l .eval(new MyDictionary<>()));

    }

    void BothOperandsNotBoolean() throws MyException {
        LogicExp l = new LogicExp(LogicOP.OR,new ValueExp(new IntValue(5)),new ValueExp(new IntValue(5)));
        ExpressionException ex = assertThrows(ExpressionException.class, ()->l .eval(new MyDictionary<>()));

    }

    @Test
    void andToString(){
            LogicExp l = new LogicExp(LogicOP.AND,new ValueExp(new BoolValue(true)),new ValueExp(new BoolValue(true)));
            assertEquals("(true & true)", l.toString());
    }

    @Test
    void ortToString(){
        LogicExp l = new LogicExp(LogicOP.OR,new ValueExp(new BoolValue(true)),new ValueExp(new BoolValue(true)));
        assertEquals("(true / true)", l.toString());
    }


}
