import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibraryApp extends JFrame {

    private final LibrarySystem lib = new LibrarySystem();

    // Catalog tab
    private DefaultTableModel catalogModel;
    private JTextField searchField;
    private JTextField bookIdField, titleField, authorField, genreField, copiesField;

    // Members tab
    private DefaultTableModel membersModel;
    private JTextField memIdField, memNameField;
    private JComboBox<String> memTypeBox;

    // Transactions tab
    private JTextField txBookField, txMemberField;
    private JTextArea txOutput;

    // Waiting list tab
    private DefaultTableModel waitingModel;
    private JTextField waitBookField;

    // Leaderboard tab
    private DefaultTableModel leaderboardModel;

    // History tab
    private DefaultTableModel historyModel;
    private JTextField histMemberField;

    public LibraryApp() {
        super("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        LibraryDemo.seedData(lib);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("📚 Catalog", buildCatalogTab());
        tabs.addTab("🧑 Members", buildMembersTab());
        tabs.addTab("🔄 Issue / Return", buildTransactionsTab());
        tabs.addTab("⏳ Waiting List", buildWaitingTab());
        tabs.addTab("🏆 Leaderboard", buildLeaderboardTab());
        tabs.addTab("📜 History", buildHistoryTab());

        add(tabs);
        refreshAll();
    }

    // ---------------- Catalog Tab ----------------
    private JPanel buildCatalogTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        // Search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(18);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> doSearch());
        searchPanel.add(searchBtn);
        JButton showAllBtn = new JButton("Show All");
        showAllBtn.addActionListener(e -> refreshCatalog());
        searchPanel.add(showAllBtn);

        // Add book form
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPanel.setBorder(BorderFactory.createTitledBorder("Add Book"));
        bookIdField = new JTextField(6);
        titleField = new JTextField(10);
        authorField = new JTextField(10);
        genreField = new JTextField(8);
        copiesField = new JTextField(3);
        addPanel.add(new JLabel("Book ID")); addPanel.add(bookIdField);
        addPanel.add(new JLabel("Title")); addPanel.add(titleField);
        addPanel.add(new JLabel("Author")); addPanel.add(authorField);
        addPanel.add(new JLabel("Genre")); addPanel.add(genreField);
        addPanel.add(new JLabel("Copies")); addPanel.add(copiesField);
        JButton addBookBtn = new JButton("Add Book");
        addBookBtn.addActionListener(e -> addBook());
        addPanel.add(addBookBtn);

        JPanel north = new JPanel(new BorderLayout());
        north.add(searchPanel, BorderLayout.NORTH);
        north.add(addPanel, BorderLayout.SOUTH);

        // Table
        catalogModel = new DefaultTableModel(
                new String[]{"ID", "Title", "Author", "Genre", "Available/Total"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(catalogModel);
        table.setRowHeight(24);

        panel.add(north, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void addBook() {
        String id = bookIdField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String genre = genreField.getText().trim();
        String copiesStr = copiesField.getText().trim();
        if (id.isEmpty() || title.isEmpty() || author.isEmpty() || genre.isEmpty() || copiesStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int copies;
        try {
            copies = Integer.parseInt(copiesStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Copies must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String msg = lib.addBook(id, title, author, genre, copies);
        JOptionPane.showMessageDialog(this, msg);
        bookIdField.setText(""); titleField.setText("");
        authorField.setText(""); genreField.setText(""); copiesField.setText("");
        refreshAll();
    }

    private void doSearch() {
        String kw = searchField.getText().trim();
        List<Book> results = kw.isEmpty()
                ? new java.util.ArrayList<>(lib.books.values())
                : lib.searchCatalog(kw);
        populateCatalog(results);
    }

    private void refreshCatalog() {
        searchField.setText("");
        populateCatalog(new java.util.ArrayList<>(lib.books.values()));
    }

    private void populateCatalog(List<Book> books) {
        catalogModel.setRowCount(0);
        for (Book b : books) {
            catalogModel.addRow(new Object[]{
                    b.bookId, b.title, b.author, b.genre,
                    b.availableCopies + "/" + b.totalCopies
            });
        }
    }

    // ---------------- Members Tab ----------------
    private JPanel buildMembersTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPanel.setBorder(BorderFactory.createTitledBorder("Add Member"));
        memIdField = new JTextField(8);
        memNameField = new JTextField(14);
        memTypeBox = new JComboBox<>(new String[]{"Student", "Faculty", "Premium"});
        addPanel.add(new JLabel("Member ID")); addPanel.add(memIdField);
        addPanel.add(new JLabel("Name")); addPanel.add(memNameField);
        addPanel.add(new JLabel("Type")); addPanel.add(memTypeBox);
        JButton addBtn = new JButton("Add Member");
        addBtn.addActionListener(e -> addMember());
        addPanel.add(addBtn);

        membersModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Type", "Fine Balance"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(membersModel);
        table.setRowHeight(24);

        panel.add(addPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void addMember() {
        String id = memIdField.getText().trim();
        String name = memNameField.getText().trim();
        String type = (String) memTypeBox.getSelectedItem();
        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill Member ID and Name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String msg = lib.addMember(id, name, type);
        JOptionPane.showMessageDialog(this, msg);
        memIdField.setText(""); memNameField.setText("");
        refreshAll();
    }

    private void refreshMembers() {
        membersModel.setRowCount(0);
        for (Member m : lib.members.values()) {
            membersModel.addRow(new Object[]{
                    m.memberId, m.name, m.membershipType, "Rs." + m.fineBalance
            });
        }
    }

    // ---------------- Transactions Tab ----------------
    private JPanel buildTransactionsTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.setBorder(BorderFactory.createTitledBorder("Issue / Return Book"));
        txBookField = new JTextField(8);
        txMemberField = new JTextField(8);
        form.add(new JLabel("Book ID")); form.add(txBookField);
        form.add(new JLabel("Member ID")); form.add(txMemberField);

        JButton issueBtn = new JButton("Issue Book");
        issueBtn.addActionListener(e -> doIssue());
        JButton returnBtn = new JButton("Return Book");
        returnBtn.addActionListener(e -> doReturn());
        JButton statusBtn = new JButton("Check Overdue Status");
        statusBtn.addActionListener(e -> checkStatus());

        form.add(issueBtn);
        form.add(returnBtn);
        form.add(statusBtn);

        txOutput = new JTextArea();
        txOutput.setEditable(false);
        txOutput.setLineWrap(true);
        txOutput.setWrapStyleWord(true);
        txOutput.setFont(new Font("Monospaced", Font.PLAIN, 13));

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(txOutput), BorderLayout.CENTER);
        return panel;
    }

    private void log(String text) {
        txOutput.append(text + "\n" + "-".repeat(60) + "\n");
        txOutput.setCaretPosition(txOutput.getDocument().getLength());
    }

    private void doIssue() {
        String bid = txBookField.getText().trim();
        String mid = txMemberField.getText().trim();
        if (bid.isEmpty() || mid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both Book ID and Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String result = lib.issueBook(bid, mid);
        log((result.startsWith("OK") ? "✅ " : "⏳ ") + result.substring(result.indexOf(':') + 2));
        refreshAll();
    }

    private void doReturn() {
        String bid = txBookField.getText().trim();
        String mid = txMemberField.getText().trim();
        if (bid.isEmpty() || mid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both Book ID and Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String result = lib.returnBook(bid, mid);
        log((result.startsWith("OK") ? "✅ " : "❌ ") + result.substring(result.indexOf(':') + 2));
        refreshAll();
    }

    private void checkStatus() {
        String bid = txBookField.getText().trim();
        String mid = txMemberField.getText().trim();
        Member member = lib.members.get(mid);
        if (member == null || !member.borrowedBooks.containsKey(bid)) {
            JOptionPane.showMessageDialog(this, "No active loan found for this Book ID + Member ID.");
            return;
        }
        java.time.LocalDate issueDate = member.borrowedBooks.get(bid);
        long daysKept = java.time.temporal.ChronoUnit.DAYS.between(issueDate, java.time.LocalDate.now());
        java.time.LocalDate dueDate = issueDate.plusDays(LibrarySystem.LOAN_PERIOD_DAYS);
        boolean overdue = daysKept > LibrarySystem.LOAN_PERIOD_DAYS;
        double fine = Math.max(0, daysKept - LibrarySystem.LOAN_PERIOD_DAYS) * LibrarySystem.FINE_PER_DAY;
        log(String.format("Book: %s | Member: %s | Due: %s | Days kept: %d | Status: %s | Fine: Rs.%.2f",
                bid, mid, dueDate, daysKept, overdue ? "OVERDUE" : "On time", fine));
    }

    // ---------------- Waiting List Tab ----------------
    private JPanel buildWaitingTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Book ID:"));
        waitBookField = new JTextField(8);
        top.add(waitBookField);
        JButton viewBtn = new JButton("View Waiting List");
        viewBtn.addActionListener(e -> viewWaiting());
        top.add(viewBtn);

        waitingModel = new DefaultTableModel(
                new String[]{"Position", "Member ID", "Name", "Membership Type"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(waitingModel);
        table.setRowHeight(24);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void viewWaiting() {
        String bid = waitBookField.getText().trim();
        waitingModel.setRowCount(0);
        if (!lib.books.containsKey(bid)) {
            JOptionPane.showMessageDialog(this, "Unknown Book ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int pos = 1;
        for (String[] row : lib.getWaitingList(bid)) {
            waitingModel.addRow(new Object[]{pos++, row[0], row[1], row[2]});
        }
    }

    // ---------------- Leaderboard Tab ----------------
    private JPanel buildLeaderboardTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshBtn = new JButton("Refresh Leaderboard");
        refreshBtn.addActionListener(e -> refreshLeaderboard());
        top.add(refreshBtn);

        leaderboardModel = new DefaultTableModel(
                new String[]{"Rank", "Title", "Author", "Times Borrowed"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(leaderboardModel);
        table.setRowHeight(24);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void refreshLeaderboard() {
        leaderboardModel.setRowCount(0);
        int rank = 1;
        for (Book b : lib.getLeaderboard(10)) {
            leaderboardModel.addRow(new Object[]{rank++, b.title, b.author, b.timesBorrowed});
        }
    }

    // ---------------- History Tab ----------------
    private JPanel buildHistoryTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Member ID:"));
        histMemberField = new JTextField(8);
        top.add(histMemberField);
        JButton viewBtn = new JButton("View History");
        viewBtn.addActionListener(e -> viewHistory());
        top.add(viewBtn);

        historyModel = new DefaultTableModel(
                new String[]{"#", "Action"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(historyModel);
        table.setRowHeight(24);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void viewHistory() {
        String mid = histMemberField.getText().trim();
        historyModel.setRowCount(0);
        if (!lib.members.containsKey(mid)) {
            JOptionPane.showMessageDialog(this, "Unknown Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<String> entries = lib.getHistory(mid);
        for (int i = 0; i < entries.size(); i++) {
            historyModel.addRow(new Object[]{i + 1, entries.get(i)});
        }
    }

    // ---------------- Refresh All ----------------
    private void refreshAll() {
        refreshCatalog();
        refreshMembers();
        refreshLeaderboard();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryApp app = new LibraryApp();
            app.setVisible(true);
        });
    }
}
