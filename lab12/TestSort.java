import org.junit.Test;

import edu.princeton.cs.algs4.Queue;

public class TestSort {
    @Test
    public void testSort() {
        Queue<Integer> q1 = new Queue<>();
        q1.enqueue(3);
        q1.enqueue(4);
        q1.enqueue(1);
        q1.enqueue(1);
        q1.enqueue(1);
        q1.enqueue(3);
        q1.enqueue(0);
        q1.enqueue(5);
        q1.enqueue(6);
        q1.enqueue(5);
        Queue<Integer> sortedQ1 = MergeSort.mergeSort(q1);
        System.out.println("Hold on!");

    }
}
