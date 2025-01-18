
package musicapp;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.PatternSyntaxException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import java.util.ArrayList;


public class FriendPage {
     private static ArrayList<String> friend=new ArrayList<>();
    
       DefaultTableModel model=new DefaultTableModel(new String[]{"Song Name","Duration","Path"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
       
 protected static DefaultTableModel model4= new DefaultTableModel(new String[]{"UserName"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
 
 protected static DefaultTableModel friendlist= new DefaultTableModel(new String[]{"MY FRİEND"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
 
 private static final String TABLE_CREATION_QUERY = "CREATE TABLE IF NOT EXISTS friends_list (" +
            "username TEXT PRIMARY KEY,NOT NULL" +
            "name TEXT NOT NULL," +
            ");";
    public FriendPage() {
         
        loadAllUsers();
        AllFriends();
               
        
       JFrame addfriend= new JFrame("Music Application");
       addfriend.setSize(800,600);
       addfriend.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       addfriend.setLocationRelativeTo(null);
       addfriend.setContentPane(new regBackgroundPanel());
       
       JLabel allUser= new JLabel("All Users 👩🏻🧑🏻‍");
       allUser.setBounds(40, 40, 200, 50);
       Font helfont= new Font("Arial",Font.BOLD,20);
       allUser.setFont(helfont);
       addfriend.add(allUser);
       
       JTextField find=new JTextField();
       find.setBounds(60, 100, 200, 40);
       addfriend.add(find);
       

      
       JTable table4=new JTable(model4);
       JScrollPane sc4= new JScrollPane(table4);
       sc4.setBounds(60,180, 300, 80);
       addfriend.add(sc4);
       
       TableRowSorter<TableModel> sorter1 = new TableRowSorter<>(table4.getModel());
       
       
                  RowFilter<TableModel, Integer> hideUserFilter = new RowFilter<>() {
                            @Override
                        public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                            String username = entry.getStringValue(0); 
                            return !username.equals(LoginPage.Username); 
    }
};

                
       
       
      find.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }

            private void filter() {
                
        String text = find.getText();
        if (text.trim().isEmpty()) {
            sorter1.setRowFilter(null);
            
        } else {
            try {
                sorter1.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            } catch (PatternSyntaxException ex) {
                sorter1.setRowFilter(null);
            }
        }
        table4.setRowSorter(sorter1);
    }
        });
      
       
    

       
       
       JButton followbut= new JButton("Follow");
       followbut.setBounds(400, 210, 120, 30);
       addfriend.add(followbut);
       
       JButton geri= new JButton("<-");
       geri.setBounds(10, 10, 70, 30);
       addfriend.add(geri);
       
       geri.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenüPage menü=new MenüPage();
                addfriend.setVisible(false);
            }
           
       });
        
        JLabel my=new JLabel("My Friends");
        my.setBounds(60, 320, 130, 30);
        my.setFont(helfont);
        addfriend.add(my);
       
       
       JTable ftable=new JTable(friendlist);
       JScrollPane frisc=new JScrollPane(ftable);
       frisc.setBounds(60, 350, 300, 100);
       addfriend.add(frisc);
       
       JPanel pnl2=new JPanel();
       JTable table=new JTable(model);
       JScrollPane sc= new JScrollPane(table);
       pnl2.add(sc);
       pnl2.setLayout(new BoxLayout(pnl2, BoxLayout.Y_AXIS));
       
       JButton unfollow= new JButton("Unfollow");
       unfollow.setBounds(400, 400, 120, 30);
       addfriend.add(unfollow);
       
       
       
       followbut.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               String insertQuery = "INSERT INTO  friends_list(username,name) VALUES (?, ?)";
    try (Connection connection = DriverManager.getConnection(RegisterPage.URL);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
       
                preparedStatement.setString(1, LoginPage.Username);
                preparedStatement.setString(2,(String)table4.getValueAt(table4.getSelectedRow(), 0));

                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                if (ex.getMessage().contains("UNIQUE constraint failed")) {
                    JOptionPane.showMessageDialog(null, "This users already added.");
                } else {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
                return;
                
            }
    AllFriends();
    loadAllUsers();
           }
           
       });
       
       unfollow.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               
                int selectedRow=ftable.getSelectedRow();
              friend.remove(friendlist.getValueAt(selectedRow, 0));
               
            String query = "DELETE FROM friends_list WHERE username = ? AND name = ?";
;
    try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
     PreparedStatement stmt = conn.prepareStatement(query)) {
    
 
    stmt.setString(1, LoginPage.Username); 
    stmt.setString(2, (String)friendlist.getValueAt(selectedRow, 0));


    int rowsDeleted = stmt.executeUpdate();
    if (rowsDeleted > 0) {
        JOptionPane.showMessageDialog(null, "Account deleted successfully!");
    } else {
        JOptionPane.showMessageDialog(null, "Failed to delete account! No matching user.");
    }
} catch (SQLException abc) {
    abc.printStackTrace();
    JOptionPane.showMessageDialog(null, "Error deleting user data: " + abc.getMessage());
}
          
              
                if(selectedRow!=-1){
                   friendlist.removeRow(selectedRow);
               }
         loadAllUsers();  }
           
           
       });
       
       ftable.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                  int selectedRow  =ftable.getSelectedRow();
                  
                  String username=(String)ftable.getValueAt(selectedRow, 0);
                  PlaylistBySinger(username,model);
                  JOptionPane.showMessageDialog(null, pnl2);
    
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
           
       });
       

       
       
       
       addfriend.setLayout(null);
       addfriend.setVisible(true);
    
    
}
private void loadAllUsers() {
   for(int row=0;row<friendlist.getRowCount();row++){
       friend.add((String)friendlist.getValueAt(row, 0));
   }
    
   model4.setRowCount(0);
   String sql = "SELECT username FROM users";
    
   

    try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String username = rs.getString("username");

           
            if (!LoginPage.Username.equals(username) && !friend.contains(username)) {
                model4.addRow(new Object[]{username}); 
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading users: " + e.getMessage());
    }

}

private void PlaylistBySinger(String friendName, DefaultTableModel model) {
    
        
        
         model.setRowCount(0);
        String query = "SELECT song_name, duration,song_path FROM playlist_songs WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, friendName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String songName = rs.getString("song_name");
                String duration = rs.getString("duration");
                String path=rs.getString("song_path");
                model.addRow(new Object[]{songName,duration,path});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching songs: " + e.getMessage());
        }
    }

private void AllFriends() {
         friendlist.setRowCount(0);
        String query = "SELECT name FROM friends_list WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, LoginPage.Username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                friendlist.addRow(new Object[]{name});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching songs: " + e.getMessage());
        }
    }



}
