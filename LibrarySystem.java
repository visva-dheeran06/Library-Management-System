// Core library logic
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

Map<String, Member> members = new HashMap<>();
Map<String, MyPriorityQueue<String>> waitingQueues = new HashMap<>();
static final int LOAN_PERIOD_DAYS = 14;
static final double FINE_PER_DAY = 5.0;

public class LibrarySystem {
    Map<String, Book> books = new HashMap<>();

    public String addBook(String bookId, String title, String author, String genre, int copies) {
        if (books.containsKey(bookId)) return "Book ID already exists.";
        books.put(bookId, new Book(bookId, title, author, genre, copies));
        return "Book added.";
    }
}

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

public String issueBook(String bookId, String memberId) {
    Book book = books.get(bookId);
    Member member = members.get(memberId);

    if (book == null || member == null) {
        return "Invalid book or member ID.";
    }
    if (book.isAvailable()) {
        book.availableCopies--;
        book.timesBorrowed++;
        member.borrowedBooks.put(bookId, java.time.LocalDate.now());
        return "OK: '" + book.title + "' issued to " + member.name + ".";
    } else {
        waitingQueues
            .computeIfAbsent(bookId, k -> new MyPriorityQueue<>())
            .enqueue(memberId, member.getPriority());
        return "WAIT: No copies available. " + member.name
                + " added to waiting list (priority: "
                + member.membershipType + ").";
    }
}

public String returnBook(String bookId, String memberId) {
    Book book = books.get(bookId);
    Member member = members.get(memberId);
    if (book == null || member == null || !member.borrowedBooks.containsKey(bookId)) {
        return "ERR: This member did not borrow this book.";
    }
    java.time.LocalDate issueDate = member.borrowedBooks.remove(bookId);
    long daysKept = java.time.temporal.ChronoUnit.DAYS.between(issueDate, java.time.LocalDate.now());
    double fine = 0.0;
    if (daysKept > LOAN_PERIOD_DAYS) {
        long overdueDays = daysKept - LOAN_PERIOD_DAYS;
        fine = overdueDays * FINE_PER_DAY;
        member.fineBalance += fine;
    }
    book.availableCopies++;
    String msg = "OK: '" + book.title + "' returned by " + member.name + ".";
    if (fine > 0) {
        msg += " Overdue! Fine: Rs." + fine;
    }
    MyPriorityQueue<String> waitQ = waitingQueues.get(bookId);

    if (waitQ != null && !waitQ.isEmpty()) {
        String nextMemberId = waitQ.dequeue();
        Member nextMember = members.get(nextMemberId);
        if (nextMember != null) {
            book.availableCopies--;
            book.timesBorrowed++;
            nextMember.borrowedBooks.put(bookId, java.time.LocalDate.now());
            msg += "\nAuto-issued to next in queue: "
                    + nextMember.name
                    + " ("
                    + nextMember.membershipType
                    + ").";
        }
    }
    return msg;
}

// Fine calculation coming next
