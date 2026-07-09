public class LibraryDemo {
    public static void seedData(LibrarySystem lib) {
        // Sample books
        lib.addBook("B1", "The Hobbit", "J.R.R. Tolkien", "Fantasy", 2);
        lib.addBook("B2", "Clean Code", "Robert Martin", "Programming", 1);
        lib.addBook("B3", "Dune", "Frank Herbert", "Sci-Fi", 1);
        lib.addBook("B4", "Atomic Habits", "James Clear", "Self-Help", 2);
        lib.addBook("B5", "The Alchemist", "Paulo Coelho", "Fiction", 3);

        // Sample members
        lib.addMember("M1", "Visva", "Student");
        lib.addMember("M2", "Dr. Rao", "Faculty");
        lib.addMember("M3", "Asha", "Premium");
        lib.addMember("M4", "Ravi", "Student");
        lib.addMember("M5", "Meena", "Faculty");
    }
}
