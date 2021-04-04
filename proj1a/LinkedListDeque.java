public class LinkedListDeque<T> {

    private class TNode {
        private T item;
        private TNode next;
        private TNode back;

        private TNode(T i, TNode n, TNode b) {
            item = i;
            next = n;
            back = b;
        }
    }

    private TNode sentiniel;
    private int size = 0;

    public LinkedListDeque() {
        sentiniel = new TNode(null, null, null);
        sentiniel.next = sentiniel;
        sentiniel.back = sentiniel;
    }


    public void addFirst(T item) {
        /* Adds an item of type T to the front of the deque. */
        TNode replacement;
        replacement = new TNode(item, sentiniel.next, sentiniel);
        replacement.next.back = replacement;
        sentiniel.next = replacement;
        size += 1;
    }

    public void addLast(T item) {
        /* Adds an item of type T to the back of the deque. */
        TNode replacement;
        replacement = new TNode(item, sentiniel, sentiniel.back);
        replacement.back.next = replacement;
        sentiniel.back = replacement;
        size += 1;
    }

    public boolean isEmpty() {
        /* Returns true if deque is empty, false otherwise. */
        return (size == 0);
    }

    public int size() {
        /* Returns the number of items in the deque. */
        return size;
    }

    public void printDeque() {
        /* Prints the items in the deque from first to last, separated by a space. */
        TNode p = sentiniel.next;
        while (p != sentiniel) {
            System.out.print(p.item);
            p = p.next;
        }
    }

    public T removeFirst() {
        /* Removes and returns the item at the front of the deque.
         * If no such item exists, returns null. */
        T removeItem = sentiniel.next.item;
        sentiniel.next = sentiniel.next.next;
        sentiniel.next.back = sentiniel;
        size -= 1;
        return removeItem;
    }

    public T removeLast() {
        /* Removes and returns the item at the back of the deque.
         * If no such item exists, returns null. */
        T removeItem = sentiniel.back.item;
        sentiniel.back = sentiniel.back.back;
        sentiniel.back.next = sentiniel;
        size -= 1;
        return removeItem;
    }

    public T get(int index) {
        /* Gets the item at the given index, where 0 is the front,
         * 1 is the next item, and so forth.
         * If no such item exists, returns null. Must not alter the deque! */
        TNode p = sentiniel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    private T  helpGetRecursive(TNode p, int index) {
        if (index == 0) {
            return p.item;
        } else {
            return helpGetRecursive(p.next, index - 1);
        }
    }
    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        } else {
            return helpGetRecursive(sentiniel.next, index);
        }
    }
}



