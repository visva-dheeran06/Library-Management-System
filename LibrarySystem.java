// Core library logic
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class LibrarySystem {
    Map<String, Book> books = new HashMap<>();

    public String addBook(String bookId, String title, String author, String genre, int copies) {
        if (books.containsKey(bookId)) return "Book ID already exists.";
        books.put(bookId, new Book(bookId, title, author, genre, copies));
        return "Book added.";
    }
}

Map<String, Member> members = new HashMap<>();
public String addMember(String memberId, String name, String membershipType) {
    if (members.containsKey(memberId)) return "Member ID already exists.";
    members.put(memberId, new Member(memberId, name, membershipType));
    return "Member added.";
}

public List<Book> searchCatalog(String keyword) {
    String kw = keyword.toLowerCase().trim();
    List<Book> results = new ArrayList<>();
    for (Book b : books.values()) {
        if (b.title.toLowerCase().contains(kw) || b.author.toLowerCase().contains(kw)
                || b.genre.toLowerCase().contains(kw)) {
            results.add(b);
        }
    }
    return results;
}
