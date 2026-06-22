// Core library logic
import java.util.HashMap;
import java.util.Map;

public class LibrarySystem {
    Map<String, Book> books = new HashMap<>();

    public String addBook(String bookId, String title, String author, String genre, int copies) {
        if (books.containsKey(bookId)) return "Book ID already exists.";
        books.put(bookId, new Book(bookId, title, author, genre, copies));
        return "Book added.";
    }
}
