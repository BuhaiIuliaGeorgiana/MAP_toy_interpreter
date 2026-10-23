package interpreter.adt;

import interpreter.model.adt.IStack;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class MyStackTest {


    private MyStack<String> stack;

    @BeforeEach
    void setUp(){
        stack = new MyStack<>();
        stack.push("hello");
    }

    @Test
    void testPop() throws ADTException {

        String res =stack.pop();
        assertEquals("hello", res);
    }

    @Test
    void testIsEmpty() throws ADTException {
        stack.pop();
        assertEquals(true, stack.isEmpty());
    }

    @Test
    void testPush() throws ADTException {
        stack.push("world");
        assertEquals("world", stack.pop());
        assertEquals("hello", stack.pop());

    }

    @Test
    void testEmptyListPop() throws ADTException {
        stack.pop();

        ADTException ex = assertThrows(ADTException.class, ()-> stack.pop());
        assertEquals("pop: the stack is empty",ex.getMessage());

    }

    @Test
    void testInstance(){
        assertInstanceOf(IStack.class, stack);
    }

    @Test
    void testToStringMethod(){
        assertEquals("[hello]", stack.toString());
    }
}
