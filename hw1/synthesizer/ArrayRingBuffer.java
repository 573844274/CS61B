
package synthesizer;
import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer Overflow");
        } else {
            rb[last] = x;
            last = realIndex(last + 1);
            fillCount += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {

        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        } else {
            T popItem = rb[first];
            first = realIndex(first + 1);
            fillCount -= 1;
            return popItem;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {

        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        } else {
            T popItem = rb[first];
            return popItem;
        }
    }



    /**
     * A loop index.
     */
    private int realIndex(int index) {
        if (index < capacity) {
            return index;
        } else {
            return realIndex(index - capacity);
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new KeyIterator();
    }

    private class KeyIterator implements Iterator<T> {
        private int ptr;
        private KeyIterator() {
            ptr = 0;
        }
        @Override
        public boolean hasNext() {
            return ptr != fillCount();
        }

        @Override
        public T next() {
            T returnItem = rb[realIndex(ptr)];
            ptr = ptr + 1;
            return returnItem;
        }
    }

}
