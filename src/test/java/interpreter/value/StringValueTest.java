package interpreter.value;

import interpreter.model.type.StringType;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class StringValueTest {
    StringValue s;

    @BeforeEach
    void setup(){
        s = new StringValue("hello");
    }

    @Test
    void testGetType(){
        assertInstanceOf(StringType.class,s.getType());
    }


    @Test
    void testToString(){

        assertEquals("hello",s.toString());
    }
}

