package interpreter.adt;

import interpreter.model.adt.MyDictionary;
import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyDictionaryTest {
    private MyDictionary<String,Integer> dict;
    @BeforeEach
    void setUp() throws ADTException {
        dict = new MyDictionary<>();
        dict.put("hello",1);
    }

    @Test
    void testIsDefinedWhenTheElemExists(){
        assertTrue(dict.isDefined("hello"));
    }

    @Test
    void testIsDefinedWhenTheElemDoesNotExist(){
        assertFalse(dict.isDefined("world"));
    }

    @Test
    void testLookupWhenTheKeyExists() throws ADTException {
        assertEquals(1,dict.lookup("hello"));
    }

    @Test
    void testLookupWhenTheKeyDoesNotExist() throws ADTException {
        ADTException ex = assertThrows(ADTException.class, ()->dict.lookup("world"));
        assertEquals("dict: lookup on an item that does not exist", ex.getMessage());
    }

    @Test
    void testPutANewElement() throws ADTException {
        dict.put("world",2);
        assertTrue(dict.isDefined("world"));
    }

    @Test
    void testPutKeyThatAlreadyExists() throws ADTException {
        ADTException ex = assertThrows(ADTException.class, ()->dict.put("hello",2));
        assertEquals("dict: put a key that already exists", ex.getMessage());
    }

    @Test
    void testUpdateWhenTheKeyExists() throws ADTException {
        dict.update("hello",2);
        assertEquals(2,dict.lookup("hello"));
    }

    @Test
    void testUpdateWhenTheKeyDoesNotExist() throws ADTException {
        ADTException ex = assertThrows(ADTException.class, ()->dict.update("world",2));
    }

    @Test
    void testToString(){
        assertEquals("{hello=1}", dict.toString());
    }
}
