
package musicapp;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class SingerPage {
    protected static MusicPlayer player=AllMusic.player;


    public SingerPage() {
        JFrame singer= new JFrame("Music Application");
       singer.setSize(800,600);
       singer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       singer.setLocationRelativeTo(null);
       singer.setContentPane(new regBackgroundPanel());
       
       JLabel singers= new JLabel("Singers 🎙" );
       singers.setForeground(Color.white);
       singers.setBounds(30, 30, 300, 80);
       Font singfont= new Font("Arial",Font.BOLD,40);
       singers.setFont(singfont);
       singer.add(singers);
       
       DefaultTableModel model=new DefaultTableModel(new String[]{"Song Name","Duration","Path"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
       
       DefaultTableModel model2=new DefaultTableModel(new String[]{"Name"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
       JTable table2= new JTable(model2);
       JScrollPane sc2= new JScrollPane(table2);
       sc2.setBounds(30, 120, 750, 380);
       sc2.setBackground(Color.pink);
       sc2.getViewport().setBackground(Color.LIGHT_GRAY);
       singer.add(sc2);
       model2.setRowCount(0);
       SearchPage.model2.setRowCount(0);
       model2.addRow(new Object[]{"Nilüfer"});
       model2.addRow(new Object[]{"Sertab Erener"});
       model2.addRow(new Object[]{"Ayla Dikmen"});
       model2.addRow(new Object[]{"Modern Talking"});
       SearchPage.model2.addRow(new Object[]{"Nilüfer"});
       SearchPage.model2.addRow(new Object[]{"Sertab Erener"});
       SearchPage.model2.addRow(new Object[]{"Ayla Dikmen"});
       SearchPage.model2.addRow(new Object[]{"Modern Talking"});
         
       
       
       
       JButton geri= new JButton("<-");
       geri.setBounds(10, 10, 70, 30);
       singer.add(geri);
       geri.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenüPage menü=new MenüPage();
                singer.setVisible(false);
            }
           
       });
       
       
   JPanel pnl2=new JPanel();
       
       JTable table=new JTable(model);
       JScrollPane sc= new JScrollPane(table);
       pnl2.add(sc);
       pnl2.setLayout(new BoxLayout(pnl2, BoxLayout.Y_AXIS));



table2.addMouseListener(new MouseListener() {
    @Override
    public void mouseClicked(MouseEvent e){
        int selectedRow = table2.getSelectedRow();
        String name = (String) model2.getValueAt(selectedRow, 0);
        SongsBySinger(name, model);
        JOptionPane.showMessageDialog(null, pnl2);

      
        table.removeMouseListener(this); 
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selected=table2.getSelectedRow();
                String abc=(String)table2.getValueAt(selected, 0);
                AllMusic.player = new MusicPlayer();
                
                if (PlayPage.play!=null) {
                    AllMusic.player.stop();
                    PlayPage.play.setVisible(false);
                }

                String singPath = (String) model.getValueAt(table.getSelectedRow(), 2);

                PlayPage play = new PlayPage((String) model.getValueAt(table.getSelectedRow(), 0), 
                                              (String) model.getValueAt(table.getSelectedRow(), 1), 
                                              abc, singPath, AllMusic.player);

                int row = table.getSelectedRow();
                AllMusic.player.load(singPath);
                AllMusic.player.play();
            }

            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });
    }

    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
});

       
     
       
       singer.setLayout(null);
       singer.setVisible(true);
    }
    
    private void SongsBySinger(String singerName, DefaultTableModel model) {
         model.setRowCount(0);
         
        String query = "SELECT song_name, duration,song_path FROM songs WHERE singer_name = ?";
        try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, singerName);
            ResultSet rs = stmt.executeQuery();

            
           

            
            while (rs.next()) {
                String songName = rs.getString("song_name");
                String duration = rs.getString("duration");
                String path=rs.getString("song_path");
                
                model.addRow(new Object[]{songName, duration,path});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching songs: " + e.getMessage());
        }
    }
    
}
