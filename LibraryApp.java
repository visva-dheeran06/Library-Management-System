import javax.swing.*; 
import javax.swing.table.DefaultTableModel; 
import java.awt.*; 

public class LibraryApp extends JFrame {    
     private final LibrarySystem lib = new LibrarySystem();    
     private DefaultTableModel catalogModel;    
     private DefaultTableModel membersModel;    
     private DefaultTableModel leaderboardModel;    
     
     public LibraryApp() {        
           super("Library Management System");        
           setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
           setSize(1000, 650);        
           setLocationRelativeTo(null);        
           
           LibraryDemo.seedData(lib);   
      
           JTabbedPane tabs = new JTabbedPane();        
           tabs.addTab("Catalog", buildCatalogTab());        
           tabs.addTab("Members", buildMembersTab());        
           tabs.addTab("Issue / Return", buildTransactionsTab());        
           tabs.addTab("Waiting List", buildWaitingTab());        
           tabs.addTab("Leaderboard", buildLeaderboardTab());        
           add(tabs);        
           refreshCatalog();        
           refreshMembers();        
           refreshLeaderboard();    
     }   
    
     private JPanel buildCatalogTab() {        
           JPanel panel = new JPanel(new BorderLayout());        
           String[] columns = {"ID", "Title", "Author", "Genre", "Available/Total"};        
           catalogModel = new DefaultTableModel(columns, 0);        
           JTable table = new JTable(catalogModel);        
           panel.add(new JScrollPane(table), BorderLayout.CENTER);        
           return panel;    
     }    
    
     private JPanel buildMembersTab() {    
           JPanel panel = new JPanel(new BorderLayout());        
           String[] columns = {"ID", "Name", "Type", "Fine Balance"};        
           membersModel = new DefaultTableModel(columns, 0);        
           JTable table = new JTable(membersModel);        
           panel.add(new JScrollPane(table), BorderLayout.CENTER);        
           return panel;    
     }    
   
     private JPanel buildTransactionsTab() {        
           JPanel panel = new JPanel(new BorderLayout());        
           JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));        
           form.add(new JLabel("Book ID")); form.add(new JTextField(8));        
           form.add(new JLabel("Member ID")); form.add(new JTextField(8));       
           form.add(new JButton("Issue Book"));        
           form.add(new JButton("Return Book"));        
           panel.add(form, BorderLayout.NORTH);        
           return panel;    
     }    
     
     private JPanel buildWaitingTab() {        
           JPanel panel = new JPanel(new BorderLayout());        
           String[] columns = {"Position", "Member ID", "Name", "Membership Type"};       
           DefaultTableModel model = new DefaultTableModel(columns, 0);      
           JTable table = new JTable(model);       
           panel.add(new JScrollPane(table), BorderLayout.CENTER);        
           return panel;    
     }    
 
     private JPanel buildLeaderboardTab() {        
           JPanel panel = new JPanel(new BorderLayout());        
           String[] columns = {"Rank", "Title", "Author", "Times Borrowed"};       
           leaderboardModel = new DefaultTableModel(columns, 0);       
           JTable table = new JTable(leaderboardModel);      
           panel.add(new JScrollPane(table), BorderLayout.CENTER);       
           return panel;    
     }    
 
     private void refreshCatalog() {    
           catalogModel.setRowCount(0);      
           for (Book b : lib.books.values()) {   
                   catalogModel.addRow(new Object[]{b.bookId, b.title, b.author, b.genre
                                 b.availableCopies + "/" + b.totalCopies});        
           }    
     }    
 
     private void refreshMembers() {      
            membersModel.setRowCount(0);       
            for (Member m : lib.members.values()) {          
                    membersModel.addRow(new Object[]{m.memberId, m.name, m.membershipType                 
                                  "Rs." + m.fineBalance});        
            }    
     }     
     
     private void refreshLeaderboard() {        
             leaderboardModel.setRowCount(0);        
             int rank = 1;       
             for (Book b : lib.getLeaderboard(10)) {           
                    leaderboardModel.addRow(new Object[]{rank++, b.title, b.author, b.timesBorrowed});        
             }    
     }    
  
     public static void main(String[] args) {        
              SwingUtilities.invokeLater(() -> {            
                     LibraryApp app = new LibraryApp();            
                     app.setVisible(true);        
              });    
     } 
}
