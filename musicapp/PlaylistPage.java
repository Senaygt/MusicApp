
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
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Font;
import java.io.FileOutputStream;
import java.util.ArrayList;



public class PlaylistPage {
    protected static ResultSet rs;
            protected ArrayList<String>playsing=new ArrayList<>();
    protected static JFrame playlist;
protected static  MusicPlayer player =AllMusic.player;
protected static int selectedRow;
protected static int rowCount;
protected static DefaultTableModel model3=new DefaultTableModel(new String[]{"Name","Singer","Duration","Path"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };


    public PlaylistPage( ) {  
           loadPlaylistSongs();
       playlist= new JFrame("Music Application");
       playlist.setSize(800,600);
       playlist.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       playlist.setLocationRelativeTo(null);
       playlist.setContentPane(new regBackgroundPanel());
       
      JLabel playlis= new JLabel("My Playlist 🎵");
      playlis.setForeground(Color.white);
      playlis.setBounds(30, 30, 300, 80);
      Font favofont= new Font("Arial",Font.BOLD,40);
      playlis.setFont(favofont);
      playlist.add(playlis);
      
      JButton exportPdf=new JButton("Table to Pdf");
      exportPdf.setBounds(370, 500, 130, 40);
       playlist.add(exportPdf);
       
    
       
       JTable table3= new JTable(model3);
       JScrollPane sc3= new JScrollPane(table3);
       sc3.setBounds(30, 120, 750, 380);
       sc3.setBackground(Color.pink);
       sc3.getViewport().setBackground(Color.LIGHT_GRAY);
       playlist.add(sc3);
       
          exportPdf.addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent e) {
                try {
                    exportTableToPDF(table3,"playlistfor"+LoginPage.Username+".pdf");
                    JOptionPane.showMessageDialog(null, "PDF successfully created!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error creating PDF: " + ex.getMessage());
                }
               }
           
       });
       
       
       
       JButton addMusic= new JButton("Add");
       addMusic.setBounds(510, 500, 130, 40);
       playlist.add(addMusic);
       
       JButton deleteMusic= new JButton("Delete");
       deleteMusic.setBounds(650, 500, 130, 40);
       playlist.add(deleteMusic);
       
       JButton geri= new JButton("<-");
       geri.setBounds(10, 10, 70, 30);
       playlist.add(geri);
       
       geri.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenüPage menü=new MenüPage();
                playlist.setVisible(false);
            }
           
       });
       
       addMusic.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               AllMusic allmusic=new AllMusic();
               playlist.setVisible(false);
           }
           
       });
       deleteMusic.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
             
                int selectedRow=table3.getSelectedRow();
 
                String songName=(String)table3.getValueAt(selectedRow, 0);
                deletePlaylistSong(songName);
                model3.removeRow(selectedRow);
                playsing.remove(songName);
           }

        
       
           
       });
       
       table3.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
             
       
                if(PlayPage.play!=null ){
                    AllMusic.player.stop();
                    PlayPage.play.setVisible(false);
                }
                
            
            String Mname=(String)table3.getValueAt(table3.getSelectedRow(), 0);
            String Duration=(String)table3.getValueAt(table3.getSelectedRow(), 2);
            String Sname= (String)table3.getValueAt(table3.getSelectedRow(), 1);
            String path=(String)table3.getValueAt(table3.getSelectedRow(),3);
            PlayPage play=new PlayPage(Mname,Duration,Sname,path,AllMusic.player);
            int row=table3.getSelectedRow();
            AllMusic.player.load(path);
            AllMusic.player.play();
            selectedRow=table3.getSelectedRow();
            rowCount=model3.getRowCount();
           
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
       
    


       playlist.setLayout(null);
       playlist.setVisible(true);
       
       
    }
     private void loadPlaylistSongs() {
         
         model3.setRowCount(0);
        String sql = "SELECT song_name, singer_name, song_path, Duration FROM playlist_songs WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LoginPage.Username);
             rs = stmt.executeQuery();

            
            while (rs.next()) {
                String songName = rs.getString("song_name");
                String artistName = rs.getString("singer_name");
                String songUrl = rs.getString("song_path");
                String duration = rs.getString("Duration");
                
                for(int row=0;row<model3.getRowCount();row++){
             playsing.add((String)model3.getValueAt(row, 0));
         }

               if(!playsing.contains(songName)){
                   model3.addRow(new Object[]{songName, artistName,duration , songUrl});
               }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veriler yüklenirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
        }}
    private void deletePlaylistSong(String songName) {
    String sql = "DELETE FROM playlist_songs WHERE username = ? AND song_name = ?";
    try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        
        stmt.setString(1, LoginPage.Username); 
        stmt.setString(2, songName); 

        
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(null, "Song deleted from playlist!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to delete song from playlist.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error deleting song from playlist: " + e.getMessage());
    }}
    
    public void exportTableToPDF(JTable table, String fileName) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        document.add(new Paragraph("Kullanici: "+LoginPage.Username));
        
        document.add(new Paragraph("\n"));

        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
        pdfTable.setWidthPercentage(100);

        for (int i = 0; i < table.getColumnCount(); i++) {
           // pdfTable.addCell(table.getColumnName(i));
            PdfPCell cell=new PdfPCell(new Phrase(table.getColumnName(i)));
            cell.setBackgroundColor(BaseColor.PINK);
            pdfTable.addCell(cell);
        }

        for (int rows = 0; rows < table.getRowCount(); rows++) {
            for (int cols = 0; cols < table.getColumnCount(); cols++) {
                pdfTable.addCell(table.getValueAt(rows, cols).toString());
            }
        }

        document.add(pdfTable);
        document.close();
    }    
    
}
