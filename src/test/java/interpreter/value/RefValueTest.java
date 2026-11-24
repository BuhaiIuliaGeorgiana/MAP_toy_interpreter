package interpreter.value;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RefValueTest {

    @Test
    void testGetType() {
        RefValue r = new RefValue(1, new IntType());

        assertEquals(new RefType(new IntType()), r.getType());
    }

    @Test
    void testAddress() {
        RefValue r = new RefValue(1, new IntType());

        assertEquals(1, r.address());
    }

    @Test
    void testLocationType() {
        RefValue r = new RefValue(1, new IntType());

        assertEquals(new IntType(), r.locationType());
    }

    @Test
    void testNestedGetType() {
        RefValue r = new RefValue(1, new RefType(new IntType()));

        assertEquals(
                new RefType(new RefType(new IntType())),
                r.getType());
    }

    @Test
    void testEquals() {
        assertEquals(
                new RefValue(1, new IntType()),
                new RefValue(1, new IntType()));
    }

    @Test
    void testDifferentAddresses() {
        assertNotEquals(
                new RefValue(1, new IntType()),
                new RefValue(2, new IntType()));
    }

    @Test
    void testToString() {
        RefValue r = new RefValue(1, new IntType());

        assertEquals("(1, int)", r.toString());
    }
}