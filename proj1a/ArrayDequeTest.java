import static org.junit.Assert.*;

import org.junit.Test;
public class ArrayDequeTest {
    @Test
    public void testConstructor() {
        ArrayDeque<Integer> one = new ArrayDeque<Integer>();
        one.addFirst(1);
        int exp = 1;
        int input = one.get(0);
        assertEquals(exp, input);
        ArrayDeque<Integer> none = new ArrayDeque<Integer>();
        assertTrue(none.isEmpty());
    }
    @Test
    public void testAdd() {
        ArrayDeque<Integer> a1 = new ArrayDeque<Integer>();
        a1.addFirst(1);
        a1.addFirst(0);
        a1.addLast(2);
        a1.addLast(3);
        int exp = 3;
        int input = a1.get(3);
        assertEquals(exp, input);
        int size = 4;
        assertEquals(size, a1.size());
        assertNull(a1.get(5));
    }
    @Test
    public void restRemove() {
        ArrayDeque<Integer> a1 = new ArrayDeque<Integer>();
        a1.addFirst(1);
        a1.addFirst(0);
        a1.addLast(2);
        a1.removeFirst();
        a1.removeLast();
        a1.addFirst(0);
        a1.addLast(2);
        a1.addLast(3);
        a1.printDeque();
        int zero = a1.removeFirst();
        int three = a1.removeLast();
        assertEquals(0, zero);
        assertEquals(3, three);
        int one = a1.get(0);
        int two = a1.get(1);
        assertEquals(1, one);
        assertEquals(2, two);
        assertEquals(2, a1.size());
        a1.printDeque();
    }

    @Test
    public void testResize() {
        ArrayDeque<Integer> a1 = new ArrayDeque<Integer>();
        a1.addLast(1);
        a1.addFirst(0);
        a1.addLast(2);
        a1.addLast(3);
        a1.addLast(4);
        a1.addLast(5);
        a1.addLast(6);
        a1.addLast(7);
        a1.addLast(8);
        a1.addLast(9);
        for (int i = 0; i < 8; i += 1) {
            a1.removeLast();
        }
        int zero = a1.get(0);
        int one = a1.get(1);
        int two = a1.size();
        assertEquals(0, zero);
        assertEquals(1, one);
        assertEquals(2, two);
    }
}
