import java.util.ArrayList;
import java.util.List;

/**
 * Custom Queue (FIFO) - implemented with an ArrayList for transparency in viva.
 */
class MyQueue<T> {
    private final List<T> items = new ArrayList<>();

    public void enqueue(T item) {
        items.add(item);
    }

    public T dequeue() {
        if (isEmpty()) return null;
        return items.remove(0);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public List<T> toList() {
        return new ArrayList<>(items);
    }
}

/**
 * Custom Stack (LIFO) - implemented with an ArrayList.
 */
class MyStack<T> {
    private final List<T> items = new ArrayList<>();

    public void push(T item) {
        items.add(item);
    }

    public T pop() {
        if (isEmpty()) return null;
        return items.remove(items.size() - 1);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    /** Returns items most-recent-first for display. */
    public List<T> toList() {
        List<T> reversed = new ArrayList<>(items);
        java.util.Collections.reverse(reversed);
        return reversed;
    }
}

// PriorityQueue will be added in upcoming commit
