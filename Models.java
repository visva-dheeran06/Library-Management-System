class Book {
    String bookId;
    String title;
    String author;
    String genre;
    int totalCopies;
    int availableCopies;
    int timesBorrowed = 0;

    public Book(String bookId, String title, String author, String genre, int totalCopies) {
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
}

// Member class coming in next commit// Will contain Book and Member classes
