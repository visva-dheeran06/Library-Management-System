import java.util.List;
import java.util.Map;

class Book {
    String bookId;
    String title;
    String author;
    String genre;
    int totalCopies;
    int availableCopies;
    int timesBorrowed = 0;

    public Book(String bookId, String title, String author, String genre, int totalCopies) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be empty.");
        }
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public boolean isAvailable() {
        return availableCopies > 0;
    }

    public double getUtilizationRate() {
        return ((double)(totalCopies - availableCopies) / totalCopies) * 100;
    }

    @Override
    public String toString() {
        return bookId + " | " + title + " | " + author + " | " + genre
                + " | " + availableCopies + "/" + totalCopies + " available";
    }
}

class Member {
    static final Map<String, Integer> MEMBERSHIP_PRIORITY = new java.util.HashMap<>();
    static final List<String> MEMBERSHIP_TYPES = java.util.Arrays.asList("Student", "Faculty", "Premium");

    static {
        MEMBERSHIP_PRIORITY.put("Premium", 0);
        MEMBERSHIP_PRIORITY.put("Faculty", 1);
        MEMBERSHIP_PRIORITY.put("Student", 2);
    }

    String memberId;
    String name;
    String membershipType;
    Map<String, java.time.LocalDate> borrowedBooks = new java.util.HashMap<>();
    double fineBalance = 0.0;

    public Member(String memberId, String name, String membershipType) {
        this.memberId = memberId;
        this.name = name;
        this.membershipType = membershipType;
    }

    public int getPriority() {
        return MEMBERSHIP_PRIORITY.getOrDefault(membershipType, 2);
    }

    public int getBorrowedCount() {
        return borrowedBooks.size();
    }

    @Override
    public String toString() {
        return memberId + " | " + name + " | " + membershipType + " | Fine: Rs." + fineBalance;
    }
}
