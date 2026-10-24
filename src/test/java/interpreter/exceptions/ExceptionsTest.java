package interpreter.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionsTest {

    @Test
    void testADTException(){
        ADTException ex = new ADTException("Stack is empty.");

        assertEquals("Stack is empty.", ex.getMessage());
        assertInstanceOf(MyException.class,ex);

    }

    @Test
    void testExpressionException(){
        ExpressionException ex = new ExpressionException("Invalid expression.");

        assertEquals("Invalid expression.", ex.getMessage());
        assertInstanceOf(MyException.class,ex);

    }

    @Test
    void testStatementException(){
        StatementException ex = new StatementException("Variable x not declared.");

        assertEquals("Variable x not declared.", ex.getMessage());
        assertInstanceOf(MyException.class,ex);

    }
}
