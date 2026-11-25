package controller;

import interpreter.controller.GarbageCollector;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GarbageCollectorTest {

    private final List<IValue> symTable = List.of(
            new RefValue(3, new IntType()),
            new RefValue(2, new RefType(new IntType()))
    );

    private final Map<Integer, IValue> heap = Map.of(
            1, new IntValue(20),
            2, new RefValue(1, new IntType()),
            3, new IntValue(30),
            4, new IntValue(7)
    );

    @Test
    void testGetAddrFromSymTable() {
        assertEquals(Set.of(2, 3),
                GarbageCollector.getAddrFromSymTable(symTable));
    }

    @Test
    void testGetAddrFromSymTableMixedValues() {
        List<IValue> values = List.of(
                new IntValue(20),
                new BoolValue(true),
                new RefValue(1, new IntType()),
                new RefValue(2, new IntType()),
                new RefValue(1, new IntType())
        );

        assertEquals(Set.of(1, 2),
                GarbageCollector.getAddrFromSymTable(values));
    }

    @Test
    void testUnsafeGarbageCollector() {
        Set<Integer> roots =
                GarbageCollector.getAddrFromSymTable(symTable);

        Map<Integer, IValue> result =
                GarbageCollector.unsafeGarbageCollector(roots, heap);

        assertEquals(Set.of(2, 3), result.keySet());

        assertFalse(result.containsKey(1));
        assertFalse(result.containsKey(4));
    }

    @Test
    void testReachableAddresses() {
        Set<Integer> roots =
                GarbageCollector.getAddrFromSymTable(symTable);

        assertEquals(Set.of(1, 2, 3),
                GarbageCollector.reachableAddresses(roots, heap));
    }

    @Test
    void testSafeGarbageCollector() {
        Set<Integer> roots =
                GarbageCollector.getAddrFromSymTable(symTable);

        Map<Integer, IValue> result =
                GarbageCollector.safeGarbageCollector(roots, heap);

        assertEquals(Map.of(
                1, new IntValue(20),
                2, new RefValue(1, new IntType()),
                3, new IntValue(30)
        ), result);
    }

    @Test
    void testNestedReferences() {
        Map<Integer, IValue> h = Map.of(
                1, new IntValue(20),
                2, new RefValue(1, new IntType()),
                3, new RefValue(2, new RefType(new IntType())),
                4, new IntValue(100)
        );

        assertEquals(Set.of(1, 2, 3),
                GarbageCollector.reachableAddresses(Set.of(3), h));
    }

    @Test
    void testNullReference() {
        assertTrue(
                GarbageCollector.reachableAddresses(
                        Set.of(0), heap).isEmpty());
    }

    @Test
    void testInvalidAddress() {
        assertTrue(
                GarbageCollector.reachableAddresses(
                        Set.of(100), heap).isEmpty());
    }

    @Test
    void testEmptySymTable() {
        assertTrue(
                GarbageCollector.getAddrFromSymTable(
                        List.of()).isEmpty());
    }

    @Test
    void testEmptyHeap() {
        assertTrue(
                GarbageCollector.safeGarbageCollector(
                        Set.of(1), Map.of()).isEmpty());
    }

    @Test
    void testAllLocationsUnreachable() {
        assertTrue(
                GarbageCollector.safeGarbageCollector(
                        Set.of(), heap).isEmpty());
    }

    @Test
    void testCircularReferences() {
        Map<Integer, IValue> h = Map.of(
                1, new RefValue(2, new IntType()),
                2, new RefValue(1, new IntType())
        );

        assertEquals(Set.of(1, 2),
                GarbageCollector.reachableAddresses(Set.of(1), h));
    }

    @Test
    void testOriginalHeapUnchanged() {
        GarbageCollector.safeGarbageCollector(Set.of(2, 3), heap);

        assertEquals(Set.of(1, 2, 3, 4), heap.keySet());
    }
}