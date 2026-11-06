package interpreter.type;

import interpreter.model.type.BoolType;
import interpreter.model.type.StringType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringTypeTest {
    StringType s;
    @BeforeEach
    void setUp(){
        s = new StringType();
    }

    @Test
    void testDefaultValue(){
        assertEquals(new StringValue(""),s.defaultValue());
    }

    @Test
    void testToString(){
        assertEquals("string", s.toString());
    }
}
