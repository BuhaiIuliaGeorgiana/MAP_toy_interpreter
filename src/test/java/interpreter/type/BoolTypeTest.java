package interpreter.type;

import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoolTypeTest {
    BoolType b;
    @BeforeEach
    void setUp(){
        b = new BoolType();
    }

    @Test
    void testDefaultValue(){
        assertEquals(new BoolValue(false),b.defaultValue());
    }

    @Test
    void testToString(){
        assertEquals("bool", b.toString());
    }
}
