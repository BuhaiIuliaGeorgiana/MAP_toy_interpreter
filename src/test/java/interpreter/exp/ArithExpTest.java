package interpreter.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArithExpTest {
    @Test
    void testAddition() throws MyException {
        ArithExp a;
        a = new ArithExp(ArithOp.ADD,new ValueExp(new IntValue(5)),new ValueExp(new IntValue(5)));
        assertEquals(new IntValue(10),a.eval(new MyDictionary<>()));
    }
    @Test
    void testMultiplication() throws MyException {
        ArithExp a;
        a = new ArithExp(ArithOp.MUL,new ValueExp(new IntValue(5)),new ValueExp(new IntValue(5)));
        assertEquals(new IntValue(25),a.eval(new MyDictionary<>()));

    }
    @Test
    void testDivision() throws MyException {
        ArithExp a;
        a = new ArithExp(ArithOp.DIV,new ValueExp(new IntValue(50)),new ValueExp(new IntValue(5)));
        assertEquals(new IntValue(10),a.eval(new MyDictionary<>()));

    }
    @Test
    void testSubstraction() throws MyException {
        ArithExp a;
        a = new ArithExp(ArithOp.SUB,new ValueExp(new IntValue(5)),new ValueExp(new IntValue(5)));
        assertEquals(new IntValue(0),a.eval(new MyDictionary<>()));

    }
    @Test
    void testAdditionToString() throws MyException {
        ArithExp a;
        a = new ArithExp(ArithOp.ADD,new ValueExp(new IntValue(5)),new ValueExp(new IntValue(5)));
        assertEquals("(5 + 5)",a.toString());
    }
    @Test
    void testMultiplicationToString() throws MyException {
        ArithExp a;
        a = new ArithExp(ArithOp.MUL,new ValueExp(new IntValue(5)),new ValueExp(new IntValue(5)));
        assertEquals("(5 * 5)",a.toString());

    }
    @Test
    void testDivisionToString() throws MyException {
        ArithExp a;
        a = new ArithExp(ArithOp.DIV,new ValueExp(new IntValue(50)),new ValueExp(new IntValue(5)));
        assertEquals("(50 / 5)", a.toString());

    }
    @Test
    void testSubstractionToString() throws MyException {
        ArithExp a;
        a = new ArithExp(ArithOp.SUB,new ValueExp(new IntValue(5)),new ValueExp(new IntValue(5)));
        assertEquals("(5 - 5)",a.toString());

    }

    @Test
    void divisionByZero(){
        ArithExp a;
        a = new ArithExp(ArithOp.DIV,new ValueExp(new IntValue(5)),new ValueExp(new IntValue(0)));
        ExpressionException ex = assertThrows(ExpressionException.class, () -> a.eval(new MyDictionary<>()));
        assertEquals("division by zero",ex.getMessage());
    }

    @Test
    void secondOperandNotInteger(){
        ArithExp a;
        a = new ArithExp(ArithOp.DIV,new ValueExp(new IntValue(5)),new ValueExp(new BoolValue(true)));
        ExpressionException ex = assertThrows(ExpressionException.class, () -> a.eval(new MyDictionary<>()));
        assertEquals("second operand is not an integer",ex.getMessage());
    }

        @Test
    void firstOperandNotInteger(){
        ArithExp a;
        a = new ArithExp(ArithOp.DIV,new ValueExp(new BoolValue(true)),new ValueExp(new IntValue(5)));
        ExpressionException ex = assertThrows(ExpressionException.class, () -> a.eval(new MyDictionary<>()));
        assertEquals("first operand is not an integer",ex.getMessage());
    }

}
