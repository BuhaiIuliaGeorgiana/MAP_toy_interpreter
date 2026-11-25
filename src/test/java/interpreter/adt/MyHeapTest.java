package interpreter.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.IntValue;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MyHeapTest {

    private MyHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MyHeap();
    }

    @Test
    void testAllocate() {
        assertEquals(1, heap.allocate(new IntValue(20)));
        assertEquals(2, heap.allocate(new IntValue(30)));
        assertEquals(3, heap.allocate(new BoolValue(true)));
    }

    @Test
    void testGet() throws ADTException {
        int address = heap.allocate(new IntValue(20));

        assertEquals(new IntValue(20), heap.get(address));
    }

    @Test
    void testGetInvalidAddress() {
        assertThrows(ADTException.class, () -> heap.get(10));
    }

    @Test
    void testUpdate() throws ADTException {
        int address = heap.allocate(new IntValue(20));

        heap.update(address, new IntValue(30));

        assertEquals(new IntValue(30), heap.get(address));
    }

    @Test
    void testUpdateInvalidAddress() {
        assertThrows(ADTException.class,
                () -> heap.update(10, new IntValue(20)));
    }

    @Test
    void testIsDefined() {
        int address = heap.allocate(new IntValue(20));

        assertTrue(heap.isDefined(address));
        assertFalse(heap.isDefined(10));
    }

    @Test
    void testNullAddress() {
        assertFalse(heap.isDefined(0));
        assertThrows(ADTException.class, () -> heap.get(0));
    }

    @Test
    void testGetContent() {
        heap.allocate(new IntValue(20));
        heap.allocate(new IntValue(30));

        assertEquals(
                Map.of(1, new IntValue(20),
                        2, new IntValue(30)),
                heap.getContent()
        );
    }

    @Test
    void testGetContentReadOnly() {
        heap.allocate(new IntValue(20));

        Map<Integer, IValue> content = heap.getContent();

        assertThrows(UnsupportedOperationException.class,
                () -> content.put(2, new IntValue(30)));
    }

    @Test
    void testSetContent() {
        heap.allocate(new IntValue(20));
        heap.allocate(new IntValue(30));

        heap.setContent(Map.of(2, new IntValue(30)));

        assertFalse(heap.isDefined(1));
        assertTrue(heap.isDefined(2));
    }

    @Test
    void testAllocateAfterGarbageCollection() {
        heap.allocate(new IntValue(20));
        heap.allocate(new IntValue(30));

        heap.setContent(Map.of(2, new IntValue(30)));

        assertEquals(3, heap.allocate(new IntValue(40)));
    }

    @Test
    void testEmptyHeap() {
        assertTrue(heap.getContent().isEmpty());
    }
}