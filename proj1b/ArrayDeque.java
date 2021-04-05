public class ArrayDeque<T> implements Deque<T> {
    private int startingCapacity = 8;
    private static double usage = 0.25;

    private T[] box = (T[]) new Object[startingCapacity];
    private int size = 0;
    private int pointer = 0;
    private int capacity = startingCapacity;
    public ArrayDeque() {

    }

    @Override
    public void addFirst(T item) {
        /* Adds an item of type T to the front of the deque. */
        if (size == capacity) {
            resize(capacity * 2);
        }
        if (pointer == 0) {
            pointer = capacity - 1;
        } else {
            pointer -= 1;
        }
        box[pointer] = item;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        /* Adds an item of type T to the back of the deque. */
        if (size == capacity) {
            resize(capacity * 2);
        }
        if (pointer + size < capacity) {
            box[pointer + size] = item;
        } else {
            box[pointer + size - capacity] = item;
        }
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        /* Returns true if deque is empty, false otherwise. */
        return (size == 0);
    }

    @Override
    public int size() {
        /* Returns the number of items in the deque. */
        return size;
    }

    @Override
    public void printDeque() {
        /* Prints the items in the deque from first to last, separated by a space. */
        int target;
        for (int i = 0; i < size; i += 1) {
            if (pointer + i >= capacity) {
                target = pointer + i - capacity;
            } else {
                target = pointer + i;
            }
            System.out.print(box[target] + " ");
        }
    }

    @Override
    public T removeFirst() {
        /* Removes and returns the item at the front of the deque.
         * If no such item exists, returns null. */
        if (capacity > startingCapacity && size < capacity * usage) {
            resize(capacity / 2);
        }
        if (size == 0) {
            return null;
        }
        size -= 1;
        if (pointer == capacity - 1) {
            pointer = 0;
            return box[capacity - 1];
        } else {
            pointer += 1;
            return box[pointer - 1];
        }
    }

    @Override
    public T removeLast() {
        /* Removes and returns the item at the back of the deque.
         * If no such item exists, returns null. */
        if (size == 0) {
            return null;
        }
        size -= 1;
        if (pointer + size >= capacity) {
            return box[pointer + size - capacity];
        } else {
            return box[pointer + size];
        }
    }

    @Override
    public T get(int index) {
        /* Gets the item at the given index, where 0 is the front,
         * 1 is the next item, and so forth.
         * If no such item exists, returns null. Must not alter the deque! */
        if (index >= size) {
            return null;
        }
        if (pointer + index < capacity) {
            return box[pointer + index];
        } else {
            return box[pointer + index - capacity];
        }
    }

    private void resize(int newCapacity) {
        T[] newBox = (T[]) new Object[newCapacity];
        int target;
        for (int i = 0; i < size; i += 1) {
            if (pointer + i >= capacity) {
                target = pointer + i - capacity;
            } else {
                target = pointer + i;
            }
            newBox[i] = box[target];
        }
        box = newBox;
        capacity = newCapacity;
        pointer = 0;
    }
}
