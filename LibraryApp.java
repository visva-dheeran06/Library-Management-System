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
        private JPanel buildMembersTab() {
               JPanel panel = new JPanel(new java.awt.BorderLayout());        
               String[] columns = {"ID", "Name", "Type", "Fine Balance"};        
               javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columns, 0);        
               JTable table = new JTable(model);        panel.add(new JScrollPane(table), java.awt.BorderLayout.CENTER);        
               return panel;    
        }
       
        tabs.addTab("Members", buildMembersTab());
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
