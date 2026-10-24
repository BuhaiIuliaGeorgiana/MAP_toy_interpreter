package interpreter.value;


import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoolValueTest {
    BoolValue e;

    @BeforeEach
    void setUp(){
        e = new BoolValue(true);
    }

    @Test
    void testGetType(){

        assertInstanceOf(BoolType.class,e.getType());
    }


    @Test
    void testToString(){

        assertEquals("true",e.toString());
    }
}
