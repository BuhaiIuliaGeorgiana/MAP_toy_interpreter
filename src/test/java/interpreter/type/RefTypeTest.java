package interpreter.type;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RefTypeTest {

    @Test
    void testDefaultValue() {
        RefType r = new RefType(new IntType());

        assertEquals(new RefValue(0, new IntType()),
                r.defaultValue());
    }

    @Test
    void testNestedDefaultValue() {
        RefType r = new RefType(new RefType(new IntType()));

        assertEquals(new RefValue(0, new RefType(new IntType())),
                r.defaultValue());
    }

    @Test
    void testEquals() {
        assertEquals(
                new RefType(new IntType()),
                new RefType(new IntType()));
    }

    @Test
    void testNestedEquals() {
        assertEquals(
                new RefType(new RefType(new IntType())),
                new RefType(new RefType(new IntType())));
    }

    @Test
    void testDifferentTypes() {
        assertNotEquals(
                new RefType(new RefType(new IntType())),
                new RefType(new IntType()));
    }

    @Test
    void testToString() {
        RefType r = new RefType(new IntType());

        assertEquals("Ref(int)", r.toString());
    }
}