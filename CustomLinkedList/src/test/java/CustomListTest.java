import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CustomListTest {
    private final Linked<Integer> testList = new CustomList<>();

    @Test
    void testSize() {
        Assertions.assertEquals(0, testList.size());
    }

    @Test
    void testAdd() {

        testList.add(0, 2);
        testList.add(1, 3);
        testList.add(2, 4);

        assertEquals(3, testList.size());
        assertEquals(2, testList.get(0));
    }
    @Test
    void testAddException(){
        Exception exception = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            testList.add(-6, 2);
        });
        assertEquals("Index is out of bounds", exception.getMessage());
    }

    @Test
    void testAddFirst() {
        testList.addFirst(2);
        assertEquals(2, testList.getFirst());
        assertEquals(1, testList.size());
    }

    @Test
    void testAddLast() {
        testList.addFirst(2);
        testList.addLast(3);
        assertEquals(3, testList.getLast());
        assertEquals(2, testList.size());
    }

    @Test
    void testGet() {
        testList.addFirst(2);
        testList.addLast(5);

        assertEquals(5, testList.get(1));
    }

    @Test
    void testGetNoSuchElementException() {

        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () ->{
            testList.get(1);
        });

        assertEquals("List is empty", exception.getMessage());
    }

    @Test
    void testGetIndexOutOfBoundException() {
        testList.addFirst(2);
        testList.addLast(5);

        Exception exception = Assertions.assertThrows(IndexOutOfBoundsException.class, () ->{
            testList.get(13);
        });

        assertEquals("Index is out of bounds", exception.getMessage());
    }

    @Test
    void testGetFirst() {
        testList.addFirst(2);
        testList.addFirst(3);
        testList.addLast(5);

        assertEquals(3, testList.getFirst());
    }
    @Test
    void testGetFirstException() {
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () ->{
            testList.getFirst();
        });

        assertEquals("List is empty", exception.getMessage());
    }

    @Test
    void testGetLast() {
        testList.addLast(2);
        testList.addLast(5);

        assertEquals(5, testList.getLast());

    }

    @Test
    void testGetLastException() {
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () ->{
            testList.getLast();
        });

        assertEquals("List is empty", exception.getMessage());
    }

    @Test
    void testRemove() {
        testList.addFirst(2);
        testList.addLast(5);

        testList.remove(0);

        assertEquals(5, testList.getFirst());
        assertEquals(1, testList.size());
    }

    @Test
    void testRemoveNoSuchElementException() {
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () ->{
            testList.remove(0);
        });

        assertEquals("List is empty", exception.getMessage());
    }

    @Test
    void testRemoveOutOfBoundsException() {
        testList.addFirst(2);
        testList.addLast(15);

        Exception exception = Assertions.assertThrows(IndexOutOfBoundsException.class, () ->{
            testList.remove(15);
        });

        assertEquals("Index is out of bounds", exception.getMessage());
    }

    @Test
    void testRemoveFirst() {
        testList.addFirst(2);
        int removed;

        removed = testList.removeFirst();

        assertEquals(2, removed);
        assertEquals(true, testList.isEmpty());
    }

    @Test
    void testRemoveFirstException() {
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () ->{
            testList.removeFirst();
        });
        assertEquals("List is empty", exception.getMessage());
    }

    @Test
    void testRemoveLast() {
        testList.addFirst(2);
        testList.addLast(5);
        int removed;

        removed = testList.removeLast();

        assertEquals(5, removed);
        assertEquals(1, testList.size());
    }

    @Test
    void testRemoveLastException() {
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () ->{
            testList.removeLast();
        });
        assertEquals("List is empty", exception.getMessage());
    }

}