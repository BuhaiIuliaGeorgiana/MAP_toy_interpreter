package interpreter.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.exp.ReadHeapExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReadHeapExpTest {

    private MyHeap heap;
    private MyDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        heap = new MyHeap();
        symTable = new MyDictionary<>();
    }

    @Test
    void testReadHeap() throws MyException {
        int address = heap.allocate(new IntValue(20));

        symTable.put("v", new RefValue(address, new IntType()));

        ReadHeapExp r = new ReadHeapExp(new VarExp("v"));

        assertEquals(new IntValue(20), r.eval(symTable, heap));
    }

    @Test
    void testNonReferenceThrows() {
        ReadHeapExp r = new ReadHeapExp(
                new ValueExp(new IntValue(20)));

        assertThrows(ExpressionException.class,
                () -> r.eval(symTable, heap));
    }

    @Test
    void testInvalidAddressThrows() {
        ReadHeapExp r = new ReadHeapExp(
                new ValueExp(new RefValue(10, new IntType())));

        assertThrows(ExpressionException.class,
                () -> r.eval(symTable, heap));
    }

    @Test
    void testNullReferenceThrows() {
        ReadHeapExp r = new ReadHeapExp(
                new ValueExp(new RefValue(0, new IntType())));

        assertThrows(ExpressionException.class,
                () -> r.eval(symTable, heap));
    }

    @Test
    void testNestedRead() throws MyException {
        int address1 = heap.allocate(new IntValue(20));

        int address2 = heap.allocate(
                new RefValue(address1, new IntType()));

        symTable.put("a",
                new RefValue(address2, new RefType(new IntType())));

        ReadHeapExp r = new ReadHeapExp(
                new ReadHeapExp(new VarExp("a")));

        assertEquals(new IntValue(20), r.eval(symTable, heap));
    }

    @Test
    void testReadAfterUpdate() throws MyException {
        int address = heap.allocate(new IntValue(20));

        symTable.put("v", new RefValue(address, new IntType()));

        heap.update(address, new IntValue(30));

        ReadHeapExp r = new ReadHeapExp(new VarExp("v"));

        assertEquals(new IntValue(30), r.eval(symTable, heap));
    }

    @Test
    void testToString() {
        ReadHeapExp r = new ReadHeapExp(new VarExp("v"));

        assertEquals("rH(v)", r.toString());
    }
}