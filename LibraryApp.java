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
               JTable table = new JTable(model);        
               panel.add(new JScrollPane(table), java.awt.BorderLayout.CENTER);        
               return panel;    
        }
       
        tabs.addTab("Members", buildMembersTab());
        private JPanel buildTransactionsTab() {
               JPanel panel = new JPanel(new java.awt.BorderLayout());
               JPanel form = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));        
               form.add(new JLabel("Book ID"));        
               form.add(new JTextField(8));        
               form.add(new JLabel("Member ID"));        
               form.add(new JTextField(8));        
               form.add(new JButton("Issue Book"));        
               form.add(new JButton("Return Book"));        
               panel.add(form, java.awt.BorderLayout.NORTH);        
               return panel;    
        }

        tabs.addTab("Issue / Return", buildTransactionsTab());
        private JPanel buildWaitingTab() {        
               JPanel panel = new JPanel(new java.awt.BorderLayout());        
               String[] columns = {"Position", "Member ID", "Name", "Membership Type"};        
               javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columns, 0);        
               JTable table = new JTable(model);        
               panel.add(new JScrollPane(table), java.awt.BorderLayout.CENTER);        
               return panel;    
        }
        private JPanel buildLeaderboardTab() {        
               JPanel panel = new JPanel(new java.awt.BorderLayout());        
               String[] columns = {"Rank", "Title", "Author", "Times Borrowed"};        
               javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columns, 0);        
               JTable table = new JTable(model);        
               panel.add(new JScrollPane(table), java.awt.BorderLayout.CENTER);        
               return panel;    
        }
        tabs.addTab("Waiting List", buildWaitingTab());
        tabs.addTab("Leaderboard", buildLeaderboardTab());

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
