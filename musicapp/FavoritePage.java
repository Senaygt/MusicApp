
package musicapp;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;





public class FavoritePage {
    protected static JFrame favorite;
    protected static MusicPlayer player=AllMusic.player ;
    PlayPage play;
    private ArrayList<String> favsing=new ArrayList<>();
    
   

    
    protected static int rowCount;
     static int selectedRow;
   public static DefaultTableModel model = new DefaultTableModel(new String[]{"Music", "Singer", "Duration", "Path"}, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
  
  

    public FavoritePage() {
        
         loadFavoriteSongs();
        
       favorite= new JFrame("Music Application");
       favorite.setSize(800,600);
       favorite.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       favorite.setLocationRelativeTo(null);
       favorite.setContentPane(new regBackgroundPanel());
       
       JLabel favo= new JLabel("My Favorite 🥰" );
       favo.setBounds(30, 30, 300, 80);
       Font favofont= new Font("Arial",Font.BOLD,40);
       favo.setFont(favofont);
       favorite.add(favo);
       
       
       JTable table= new JTable(model);
       JScrollPane sc= new JScrollPane(table);
       sc.setBounds(30, 120, 750, 380);
       sc.setBackground(Color.pink);
       sc.getViewport().setBackground(Color.LIGHT_GRAY);
       favorite.add(sc);
       
       

       
       
       JButton delete= new JButton("Delete");
       delete.setBounds(650,500,130,40);
       delete.setBackground(Color.black);
       delete.setForeground(Color.pink);
       Font delfont= new Font("Arial",Font.BOLD,17);
       delete.setFont(delfont);
       favorite.add(delete);
       
       delete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow=table.getSelectedRow();
                String songName=(String)table.getValueAt(selectedRow, 0);
                deleteFavoriteSong(songName);
                model.removeRow(selectedRow);
                favsing.remove(songName);

            }});
   
       JButton geri= new JButton("<-");
       geri.setBounds(10, 10, 70, 30);
       favorite.add(geri);
       
       geri.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenüPage menü=new MenüPage();
                favorite.setVisible(false);
            }
           
       });
       
      
      
      
      
       
       table.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
              
                if(PlayPage.play!=null){
                    AllMusic.player.stop();
                    PlayPage.play.setVisible(false);
                }
                
            String Mname=(String)table.getValueAt(table.getSelectedRow(), 0);
            String Duration=(String)table.getValueAt(table.getSelectedRow(), 2);
            String Sname= (String)table.getValueAt(table.getSelectedRow(), 1);
            String path=(String)table.getValueAt(table.getSelectedRow(),3);
            play=new PlayPage(Mname,Duration,Sname,path,AllMusic.player);
            ProfilePage.model5.addRow(new Object[]{Mname});
            int row=table.getSelectedRow();
            AllMusic.player.load(path);
            AllMusic.player.play();
            selectedRow=table.getSelectedRow();
            rowCount=model.getRowCount();
            
                String insertQuery = "INSERT INTO  last_music(username,song_name,singer_name,song_path,duration) VALUES (?, ?, ?,?,?)";
    try (Connection connection = DriverManager.getConnection(RegisterPage.URL);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

                preparedStatement.setString(1, LoginPage.Username);
                preparedStatement.setString(2, Mname);
                preparedStatement.setString(3, Sname);
                 preparedStatement.setString(4, path);
                preparedStatement.setString(5, Duration);
                

                preparedStatement.executeUpdate();
                

            } catch (SQLException ex) { 
                return;
            }            

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

       favorite.setLayout(null);
       favorite.setVisible(true);
       
        
    }

 private void loadFavoriteSongs() {
   
     model.setRowCount(0);
        String sql = "SELECT song_name, singer_name, song_path, Duration FROM favorite_songs WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LoginPage.Username);
            ResultSet rs = stmt.executeQuery();

           
            while (rs.next()) {
                String songName = rs.getString("song_name");
                String artistName = rs.getString("singer_name");
                String songUrl = rs.getString("song_path");
                String duration = rs.getString("Duration");
                
                  for(int row=0;row<model.getRowCount();row++){
       favsing.add((String)model.getValueAt(row, 0));
   }
                
                

                if (!favsing.contains(songName)) {
               model.addRow(new Object[]{songName, artistName, duration, songUrl}); 
            }
               
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veriler yüklenirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
 private void deleteFavoriteSong(String songName) {
    String sql = "DELETE FROM favorite_songs WHERE username = ? AND song_name = ?";
    try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        
        stmt.setString(1, LoginPage.Username); 
        stmt.setString(2, songName); 

        
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(null, "Song deleted from favorites!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to delete song from favorites.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error deleting song from favorites: " + e.getMessage());
    }
 }
}
