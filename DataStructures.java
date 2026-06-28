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

/**
 * Custom Priority Queue - implemented with a sorted ArrayList (not java.util.PriorityQueue),
 * so the insertion/removal logic is fully visible and easy to explain in a viva.
 * Lower priority NUMBER = served first (e.g. Premium=0, Faculty=1, Student=2).
 */
class MyPriorityQueue<T> {

    private static class Entry<T> {
        int priority;
        int insertionOrder;
        T data;

        Entry(int priority, int insertionOrder, T data) {
            this.priority = priority;
            this.insertionOrder = insertionOrder;
            this.data = data;
        }
    }

    private final List<Entry<T>> items = new ArrayList<>();
    private int counter = 0;

    public void enqueue(T data, int priority) {
        Entry<T> entry = new Entry<>(priority, counter++, data);
        int index = 0;
        while (index < items.size()) {
            Entry<T> current = items.get(index);
            if (current.priority < entry.priority ||
                    (current.priority == entry.priority && current.insertionOrder <= entry.insertionOrder)) {
                index++;
            } else {
                break;
            }
        }
        items.add(index, entry);
    }

    public T dequeue() {
        if (isEmpty()) return null;
        return items.remove(0).data;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public List<T> toList() {
        List<T> result = new ArrayList<>();
        for (Entry<T> e : items) result.add(e.data);
        return result;
    }
}
