

package musicapp;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import static musicapp.PlaylistPage.model3;
import static musicapp.PlaylistPage.rs;

public class AllMusic {
    protected static JFrame Music;
    protected static MusicPlayer player=new MusicPlayer();
    protected static int rowCount;
    protected static int selectedRow;
    
   
              private static final String TABLE_CREATION_QUERY = "CREATE TABLE IF NOT EXISTS songs (\n" +
"    song_name TEXT,UNIQUE NOT NULL,\n" +
"    singer_name TEXT,\n" +
"    song_path TEXT,UNIQUE,\n" +
"    duration TEXT,\n" +
"    isTurkish INTEGER,\n" +
")";                    

    static DefaultTableModel model=new DefaultTableModel(new String[]{"Name","Singer","Duration","Path"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };


    public AllMusic() {
      
        
        
       model.setRowCount(0);
       Music= new JFrame("Music Application");
       Music.setSize(800,600);
       Music.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       Music.setLocationRelativeTo(null);
       Music.setContentPane(new regBackgroundPanel());
          loadAllSongs();
       
       String[] dizi={"All Music","Turkish","English"};
       JComboBox option= new JComboBox(dizi);
       option.setBounds(550, 30, 200, 80);
       Music.add(option);
       
       JTable table= new JTable(model);
       JScrollPane sc= new JScrollPane(table);
       sc.setBounds(20, 100, 740, 470);
       Music.add(sc);
       
        JButton exportPdf= new JButton("List Of Music");
        exportPdf.setBounds(550,10,150,30);
        Music.add(exportPdf);
        
        
        exportPdf.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e) {
             try {
                  String Item=(String)option.getSelectedItem();
                     exportTableToPDF(table,Item,"musiclist_"+Item+".pdf");
                    JOptionPane.showMessageDialog(null, "PDF successfully created!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error creating PDF: " + ex.getMessage());
                }
               
            }
            
        });
            
        
        

       
      
       
       rowCount=table.getRowCount();
       
        JButton geri= new JButton("<-");
       geri.setBounds(10, 10, 70, 30);
       Music.add(geri);
       
        geri.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenüPage menü=new MenüPage();
                Music.setVisible(false);
            }
           
       });
        
      option.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
              if(option.getSelectedIndex()==0){
                   model.setRowCount(0);
          
       loadAllSongs();
                   
               }
               
              else if(option.getSelectedIndex()==1){
                   model.setRowCount(0);
    String sql = "SELECT song_name, singer_name, song_path, Duration FROM songs WHERE isTurkish = 1";
    try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        rs = stmt.executeQuery();

        // Verileri tabloya ekle
        while (rs.next()) {
            String songName = rs.getString("song_name");
            String artistName = rs.getString("singer_name");
            String songUrl = rs.getString("song_path");
            String duration = rs.getString("Duration");

            // Yeni satırı tabloya ekle
            model.addRow(new Object[]{songName, artistName, duration, songUrl});
        }
    } catch (Exception c) {
        c.printStackTrace();
        JOptionPane.showMessageDialog(null, "Veriler yüklenirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
    }
              }
       
       else{
                model.setRowCount(0);
    String sql = "SELECT song_name, singer_name, song_path, Duration FROM songs WHERE isTurkish = 0";
    try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        rs = stmt.executeQuery();

        
        while (rs.next()) {
            String songName = rs.getString("song_name");
            String artistName = rs.getString("singer_name");
            String songUrl = rs.getString("song_path");
            String duration = rs.getString("Duration");

            
            model.addRow(new Object[]{songName, artistName, duration, songUrl});
        }
    } catch (Exception b) {
        b.printStackTrace();
        JOptionPane.showMessageDialog(null, "Veriler yüklenirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
    }

       
                   
                   
               }
       
       
                   
               
               }
            
        });

       
       table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                  
                if(PlayPage.play!=null){
                    player.stop();
                    PlayPage.play.setVisible(false);
                }
                try {
                    
            String Mname = (String) table.getValueAt(table.getSelectedRow(), 0);
            String Duration = (String) table.getValueAt(table.getSelectedRow(), 2);
            String Sname = (String) table.getValueAt(table.getSelectedRow(), 1);
            String path = (String) table.getValueAt(table.getSelectedRow(), 3);
            
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
            
            if(PlayPage.play!=null){
                player.stop();
                PlayPage.play.setVisible(false);
            }
            
            
            PlayPage play=new PlayPage(Mname, Duration, Sname, path, player);
            player = new MusicPlayer();
            ProfilePage.model5.addRow(new Object[]{Mname});
            player.load(path);
            player.play();
            selectedRow = table.getSelectedRow();
        } catch (Exception ex) {
            ex.printStackTrace();  // Hata mesajını yazdırabilir veya uygun şekilde işleyebilirsiniz
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
       
       
       Music.setLayout(null);
       Music.setVisible(true);
       
    }
     private void loadAllSongs() {
         model.setRowCount(0);
         SearchPage.model.setRowCount(0);
    String sql = "SELECT song_name, singer_name, song_path, Duration,isTurkish FROM songs";
    try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        rs = stmt.executeQuery();

        // Verileri tabloya ekle
        while (rs.next()) {
            String songName = rs.getString("song_name");
            String artistName = rs.getString("singer_name");
            String songUrl = rs.getString("song_path");
            String duration = rs.getString("Duration");
            int language=rs.getInt("isTurkish");

            // Yeni satırı tabloya ekle
            model.addRow(new Object[]{songName, artistName, duration ,songUrl});
            SearchPage.model.addRow(new Object[]{songName});
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Veriler yüklenirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
    }
}
     public void exportTableToPDF(JTable table, String selectedType,String fileName) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        document.add(new Paragraph("Type: "+selectedType));
        document.add(new Paragraph("\n"));

        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
        pdfTable.setWidthPercentage(100);

        for (int i = 0; i < table.getColumnCount(); i++) {
            //pdfTable.addCell(table.getColumnName(i));
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
