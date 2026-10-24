package interpreter.type;

import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntTypeTest {
    IntType i;
    @BeforeEach
    void setUp(){
        i = new IntType();
    }

    @Test
    void testDefaultValue(){
        assertEquals(new IntValue(0),i.defaultValue());
    }

    @Test
    void testToString(){
        assertEquals("int", i.toString());
    }

}
