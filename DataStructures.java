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

// Stack and PriorityQueue will be added in upcoming commits
