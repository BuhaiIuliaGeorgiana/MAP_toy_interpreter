package interpreter.value;

import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class IntValueTest {
    IntValue e;

    @BeforeEach
    void setUp(){
        e = new IntValue(3);
    }

    @Test
    void testGetType(){

        assertInstanceOf(IntType.class,e.getType());
    }


    @Test
    void testToString(){

        assertEquals("3",e.toString());
    }
}
