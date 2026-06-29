import java.util.List;
import java.util.ArrayList;
import java.util.Map;
public class Reports {
    public static List<Book> getLeaderboard(Map<String, Book> books, int topN) {
        List<Book> sorted = new ArrayList<>(books.values());
        sorted.sort((a, b) -> b.timesBorrowed - a.timesBorrowed);
        return sorted.subList(0, Math.min(topN, sorted.size()));
    }
    public static void logAction(MyStack<String> historyStack, String bookId, String action) {
        historyStack.push(bookId + " - " + action + " - " + java.time.LocalDate.now());
    }
    public static List<String> getHistory(MyStack<String> historyStack) {
        return historyStack.toList();
    } }
