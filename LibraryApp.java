 // Swing GUI
import javax.swing.*;

public class LibraryApp extends JFrame {

    public LibraryApp() {
        super("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Catalog", new JPanel());
        tabs.addTab("Members", new JPanel());
        tabs.addTab("Issue / Return", new JPanel());
        tabs.addTab("Waiting List", new JPanel());
        tabs.addTab("Leaderboard", new JPanel());

        add(tabs);

        // Each tab's content will be filled in upcoming commits
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryApp app = new LibraryApp();
            app.setVisible(true);
        });
    }
}
