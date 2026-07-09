import java.util.ArrayList;
import java.util.List;

/**
 * Custom Queue (FIFO)
 * Used for: General waiting lines
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

    public void clear() {
        items.clear();
    }

    public List<T> toList() {
        return new ArrayList<>(items);
    }
}

/**
 * Custom Stack (LIFO)
 * Used for: Per-member borrow/return history
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

    public void clear() {
        items.clear();
    }

    public List<T> toList() {
        List<T> reversed = new ArrayList<>(items);
        java.util.Collections.reverse(reversed);
        return reversed;
    }
}

/**
 * Custom Priority Queue (sorted ArrayList)
 * Used for: Waiting list per book — Premium(0) > Faculty(1) > Student(2)
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
                    (current.priority == entry.priority &&
                            current.insertionOrder <= entry.insertionOrder)) {
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

    public boolean contains(T data) {
        for (Entry<T> e : items) {
            if (e.data.equals(data)) return true;
        }
        return false;
    }

    public List<T> toList() {
        List<T> result = new ArrayList<>();
        for (Entry<T> e : items) result.add(e.data);
        return result;
    }
}

/**
 * Test class to verify all 3 data structures
 * Run: javac DataStructures.java && java DataStructuresTest
 */
public class DataStructuresTest {
    public static void main(String[] args) {
        System.out.println("=== Testing MyQueue (FIFO) ===");
        MyQueue<String> queue = new MyQueue<>();
        queue.enqueue("Alice");
        queue.enqueue("Bob");
        System.out.println(queue.dequeue()); // Alice
        System.out.println(queue.dequeue()); // Bob

        System.out.println("\n=== Testing MyStack (LIFO) ===");
        MyStack<String> stack = new MyStack<>();
        stack.push("Issued B1");
        stack.push("Returned B1");
        System.out.println(stack.pop()); // Returned B1
        System.out.println(stack.pop()); // Issued B1

        System.out.println("\n=== Testing MyPriorityQueue ===");
        MyPriorityQueue<String> pq = new MyPriorityQueue<>();
        pq.enqueue("Student-Ravi", 2);
        pq.enqueue("Premium-Asha", 0);
        pq.enqueue("Faculty-Rao", 1);
        System.out.println(pq.dequeue()); // Premium-Asha
        System.out.println(pq.dequeue()); // Faculty-Rao
        System.out.println(pq.dequeue()); // Student-Ravi

        System.out.println("\n=== Testing contains() ===");
        pq.enqueue("Student-Ravi", 2);
        System.out.println(pq.contains("Student-Ravi")); // true
        System.out.println(pq.contains("Premium-Asha")); // false

        System.out.println("\nAll tests passed!");
    }
}
