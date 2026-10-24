package interpreter.adt;

import interpreter.model.adt.MyList;
import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyListTest {
    private MyList<Integer> arr ;
    @BeforeEach
    void setUp(){
         arr =  new MyList<>();
         arr.add(2);
    }

    @Test
    void testSize(){
        assertEquals(1,arr.size());
    }

    @Test
    void testGet() throws ADTException {
        assertEquals(2,arr.get(0));
    }

    @Test
    void testGetWhenIndexIsNegative(){
        ADTException ex = assertThrows(ADTException.class,()->arr.get(-1));
        assertEquals("list: get an invalid index", ex.getMessage());
    }

    @Test
    void testGetWhenIndexIsTooBig(){
        ADTException ex = assertThrows(ADTException.class,()->arr.get(10));
        assertEquals("list: get an invalid index", ex.getMessage());
    }

    @Test
    void tesAdd() throws ADTException {
        arr.add(1);
        assertEquals(2,arr.size());
        assertEquals(1,arr.get(1));
    }

    @Test
    void testToString(){
        assertEquals("[2]", arr.toString());
    }

}
