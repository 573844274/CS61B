package synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testEnqueue() {
        ArrayRingBuffer arb = new ArrayRingBuffer<Integer>(10);
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(i);
        }
        assertEquals(10, arb.fillCount());
        assertEquals(0, arb.dequeue());
        assertEquals(1, arb.dequeue());
        assertEquals(8, arb.fillCount());
        assertEquals(2, arb.peek());
        assertEquals(8, arb.fillCount());
        //assertEquals(0, arb.last);
        //assertEquals(2, arb.first);
        Iterator<Integer> seer = arb.iterator();
        while (seer.hasNext()) {
            System.out.println(seer.next());
        }


    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
