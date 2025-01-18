  
package musicapp;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


public class ProfilePage {
    
  
    protected static  MusicPlayer player =AllMusic.player;
   protected static DefaultTableModel listmodel=new DefaultTableModel(new String[]{"Name","Duration"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
protected static DefaultTableModel model5= new DefaultTableModel(new String[]{"Music Name"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
protected static DefaultTableModel model= new DefaultTableModel(new String[]{"Singer Name"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
private static final String TABLE_CREATION_QUERY = "CREATE TABLE IF NOT EXISTS my_songs (\n" +
"    username TEXT NOT NULL,\n" +
"    song_name TEXT,UNIQUE NOT NULL,\n" +
"    singer_name TEXT,\n" +
"    song_path TEXT,UNIQUE,\n" +
"    duration TEXT,\n" +
"    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE\n" +
")";          

private static final String TABLE_CREATION_QUERY2 = "CREATE TABLE IF NOT EXISTS last_music (\n" +
"    username TEXT NOT NULL,\n" +
"    song_name TEXT,NOT NULL,\n" +
"    singer_name TEXT,\n" +
"    song_path TEXT,\n" +
"    duration TEXT,\n" +
"    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE\n" +
")";

    public ProfilePage( ) {
        listmodel.setRowCount(0);
        loadMySongs();
        
        
        String name="";
       String password="";
        String sql = "SELECT Name,Password FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LoginPage.Username);
            ResultSet rs = stmt.executeQuery();

            
           name=rs.getString("Name");
           password=rs.getString("Password");

               
            }
        
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veriler yüklenirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    
        loadLastMusic();
        
       JFrame profile= new JFrame("Music Application");
       profile.setSize(800,600);
       profile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       profile.setLocationRelativeTo(null);
       profile.setContentPane(new regBackgroundPanel());
       
        JLabel gcms=new JLabel("The last you listened ");
        gcms.setBounds(40,50,200,30);
        profile.add(gcms);
        
        
        JTable table5=new JTable(model5);
        JScrollPane sc5=new JScrollPane(table5);
        sc5.setBounds(40, 80, 400, 80);
        profile.add(sc5);
        
        JButton exportPdf= new JButton("View music history");
        exportPdf.setBounds(220,170,150,30);
        profile.add(exportPdf);
        
        exportPdf.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    exportTableToPDF(table5,"musicHistory_"+LoginPage.Username+".pdf");
                    JOptionPane.showMessageDialog(null, "PDF successfully created!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error creating PDF: " + ex.getMessage());
                }
               
            }
            
        });
        
        
        String a=LoginPage.Username;
       
       JLabel pro1=new JLabel("Name: ");
       pro1.setBounds(20, 300, 130, 30);
       Font helfont= new Font("Arial",Font.BOLD,20);
       pro1.setFont(helfont);
       profile.add(pro1);
       
       JTextField fld1= new JTextField(name);//getnaem
       fld1.setBounds(150,300,130,30);
       profile.add(fld1);
       
       
       JLabel pro2=new JLabel("UserName: ");
       pro2.setBounds(20, 340, 130, 30);
       pro2.setFont(helfont);
       profile.add(pro2);
       
       JTextField fld2= new JTextField(a);
       fld2.setBounds(150,340,130,30);
       profile.add(fld2);
       
       JLabel pro3=new JLabel("Password: ");
       pro3.setBounds(20, 380, 130, 30);
       pro3.setFont(helfont);
       profile.add(pro3);
       
       JTextField fld3= new JTextField(password);
       fld3.setBounds(150,380,130,30);
       profile.add(fld3);
       
 
       
       JButton updateInfo= new JButton("Update");
       updateInfo.setBounds(20,420,130,30);
       profile.add(updateInfo);
       
       JButton saveInfo= new JButton("Save");
       saveInfo.setBounds(150,420,130,30);
       profile.add(saveInfo);
       saveInfo.setEnabled(false);
       
       JButton deleteInfo= new JButton("Delete My Account");
       deleteInfo.setBounds(600,530,200,30);
       profile.add(deleteInfo);
       deleteInfo.setForeground(Color.red);
    
       
       JButton addMus =new JButton("+Add Music");
       addMus.setBounds(490, 320, 130, 30);
       profile.add(addMus);
       
       
       //MÜZİKLERİMİ GÖRECEĞİM KISIM 
       JLabel mlist= new JLabel("My Music");
       mlist.setBounds(570,50,100,30);
       mlist.setFont(helfont);
       mlist.setForeground(Color.BLUE);
       profile.add(mlist);
       
       JButton deleteme=new JButton("Delete Music");
       deleteme.setBounds(630, 320, 130, 30);
       profile.add(deleteme);
       
       
       
       JTable table=new JTable(listmodel);
       JScrollPane scrollListe= new JScrollPane(table);
       scrollListe.setBounds(500, 80, 240, 240);
       profile.add(scrollListe);
       
       
       
       
       //MÜZİK EKLEMEK İÇİN OPTİON
       JPanel addM= new JPanel();
       TitledBorder border1=new TitledBorder("Add Music");
       addM.setBorder(border1);
       
       
       JLabel music=new JLabel("Music Name: ");
       addM.add(music);
       
       JTextField musicName= new JTextField();
       addM.add(musicName);
       
       JLabel addr=new JLabel("Address:  ");
       addM.add(addr);
       
       JTextField address= new JTextField();
       addM.add(address);
       
       JLabel singer= new JLabel("Singer Name: ");
      addM.add(singer);
       
       JTextField singerName= new JTextField(name);//buraya direkt profile girelen ismi alacağım 
       singerName.setEditable(false);
       addM.add(singerName);
       
       JLabel durat=new JLabel("Duration: ");
       addM.add(durat);
       
       JTextField duration=new JTextField();
       addM.add(duration);
       
       addM.setLayout(new BoxLayout(addM, BoxLayout.Y_AXIS));
       
       
       
      
       
     
       
       
       
     
       
       
       
       JButton geri= new JButton("<-");
       geri.setBounds(10, 10, 70, 30);
       profile.add(geri);
       
       deleteInfo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "DELETE FROM users WHERE username = ?";
try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
     PreparedStatement stmt = conn.prepareStatement(query)) {
    
 
    stmt.setString(1, LoginPage.Username); 


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
                JOptionPane.showMessageDialog(null, "GOOD BYE :(");
                BeginPage begin=new BeginPage();
                profile.setVisible(false);
            }
           
       });
       geri.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenüPage menü=new MenüPage();
                profile.setVisible(false);
            }
           
       });
       
       addMus.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
              int response= JOptionPane.showConfirmDialog(null,addM, "Add Music",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
           if(response==JOptionPane.YES_OPTION){
               String Name= musicName.getText();
               String durati=duration.getText();
               String path=address.getText();

            PlayPage play=new PlayPage(Name,durati,LoginPage.Username,path,AllMusic.player);
            AllMusic.player.load(path);
            AllMusic.player.play(); //    /music/myMusic.wav
            listmodel.addRow(new Object[]{Name,durati});
               
                       String insertQuery = "INSERT INTO  my_songs(username,song_name,singer_name,song_path,duration) VALUES (?, ?, ?,?,?)";
    try (Connection connection = DriverManager.getConnection(RegisterPage.URL);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

                preparedStatement.setString(1, LoginPage.Username);
                preparedStatement.setString(2, Name);
                preparedStatement.setString(3, fld1.getText());
                preparedStatement.setString(4, address.getText());
                preparedStatement.setString(5, durati);
                

                preparedStatement.executeUpdate();
                

            } catch (SQLException ex) {
                if (ex.getMessage().contains("UNIQUE constraint failed")) {
                    JOptionPane.showMessageDialog(null, "This songs already added.");
                } else {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
                return;
            }
           }
           }
           
       });
       
       deleteme.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               
               int selectedRow=table.getSelectedRow();
               String songName=(String)table.getValueAt(selectedRow, 0);
               String sql = "DELETE FROM my_songs WHERE username = ? AND song_name = ?";
    try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        
        stmt.setString(1, LoginPage.Username); 
        stmt.setString(2, songName); 

        
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(null, "Song deleted from my song");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to delete song from my song.");
        }
    } catch (SQLException abc) {
        abc.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error deleting song from my song: " + abc.getMessage());
    }
               if(selectedRow!=-1){
                   listmodel.removeRow(selectedRow);
               }
               else{
                   JOptionPane.showMessageDialog(null, "Please before select row");
               }
               
              
           }
           
       });
       
      updateInfo.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               
              
               fld1.setEditable(true);
               fld2.setEditable(true);
               fld3.setEditable(true);
               saveInfo.setEnabled(true);
           }
          
      });
      
      saveInfo.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
          
               
             String query = "UPDATE users SET name = ?, password = ?,username=?  WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            
            

           

            stmt.setString(1, fld1.getText());
            stmt.setString(2, fld3.getText());
            stmt.setString(3, fld2.getText());
            stmt.setString(4,LoginPage.Username);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update profile!");
            }
        } catch (SQLException abc) {
            abc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating user data: " +abc.getMessage());
        }
               
               
               fld1.setEditable(false);
               fld2.setEditable(false);
               fld3.setEditable(false);
               saveInfo.setEnabled(false);
               
              
           }
          
      });
   
       

    profile.setLayout(null);
    profile.setVisible(true);
    }
    
    
   private void loadMySongs() {
        String sql = "SELECT song_name, singer_name, song_path, Duration FROM my_songs WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LoginPage.Username);
            ResultSet rs = stmt.executeQuery();

            
            while (rs.next()) {
                String songName = rs.getString("song_name");
                String artistName = rs.getString("singer_name");
                String songUrl = rs.getString("song_path");
                String duration = rs.getString("Duration");
                
                

               
                listmodel.addRow(new Object[]{songName,duration});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veriler yüklenirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
   
   private void loadLastMusic() {
       model5.setRowCount(0);
        String sql = "SELECT song_name, singer_name, song_path, Duration FROM last_music WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LoginPage.Username);
            ResultSet rs = stmt.executeQuery();

            // Verileri tabloya ekle
            while (rs.next()) {
                String songName = rs.getString("song_name");
                String artistName = rs.getString("singer_name");
                String songUrl = rs.getString("song_path");
                String duration = rs.getString("Duration");
                
                

                // Yeni satırı tabloya ekle
                model5.addRow(new Object[]{songName, artistName, duration, songUrl});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veriler yüklenirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void exportTableToPDF(JTable table, String fileName) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        document.add(new Paragraph("Kullanici: "+LoginPage.Username));
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
