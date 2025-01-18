
package musicapp;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SearchPage {
 protected static DefaultTableModel model=new DefaultTableModel(new String[]{"Music"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
  protected static DefaultTableModel model2=new DefaultTableModel(new String[]{"Singer "},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
  DefaultTableModel model3=new DefaultTableModel(new String[]{"Song Name","Duration","Path"},0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
    public SearchPage() {

        
       JFrame searchpage= new JFrame("Music Application");
       searchpage.setSize(800,600);
       searchpage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       searchpage.setLocationRelativeTo(null);
       searchpage.setContentPane(new regBackgroundPanel());
       

    
            
       
       
       JTable table=new JTable(model);
       JScrollPane sc=new JScrollPane(table);
       sc.setBounds(220, 140, 340, 200);
       
       JTable table2=new JTable(model2);
       JScrollPane sc2=new JScrollPane(table2);
       sc2.setBounds(220, 350, 340, 200);
       
       JPanel pnl2=new JPanel();
       
       JTable table3=new JTable(model3);
       JScrollPane sc3= new JScrollPane(table3);
       pnl2.add(sc3);
       pnl2.setLayout(new BoxLayout(pnl2, BoxLayout.Y_AXIS));
       
       
    TableRowSorter<TableModel> sorter1 = new TableRowSorter<>(table.getModel());
    TableRowSorter<TableModel> sorter2 = new TableRowSorter<>(table2.getModel());
    
    
   table2.addMouseListener(new MouseListener(){
           @Override
           public void mouseClicked(MouseEvent e) {
         int selectedRow = table2.getSelectedRow();
        String name = (String) table2.getValueAt(selectedRow, 0);
        SongsBySinger(name, model3);
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
       
       
       
       
       searchpage.add(sc);
       searchpage.add(sc2);
       
       JLabel serlbl= new JLabel("🔍Search: ");
       Font helfont= new Font("Arial",Font.BOLD,20);
       serlbl.setFont(helfont);
       serlbl.setBounds(200, 60, 200, 50);
       searchpage.add(serlbl);
       
       JTextField findSearch= new JTextField();
       findSearch.setBounds(300, 60, 200, 50);
       searchpage.add(findSearch);
       
       JButton geri= new JButton("<-");
       geri.setBounds(10, 10, 70, 30);
       searchpage.add(geri);
       
       geri.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenüPage menü=new MenüPage();
                searchpage.setVisible(false);
            }
           
       });
       
       
       
    findSearch.getDocument().addDocumentListener(new DocumentListener() {
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
        String text = findSearch.getText();
        if (text.trim().isEmpty()) {
            sorter1.setRowFilter(null);
            sorter2.setRowFilter(null);
        } else {
            try {
                sorter1.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                sorter2.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            } catch (PatternSyntaxException ex) {
                sorter1.setRowFilter(null);
                sorter2.setRowFilter(null);
            }
        }
        table.setRowSorter(sorter1);
        table2.setRowSorter(sorter2);
    }
        });
       
       
    
       
       
      
       
       searchpage.setLayout(null);
       searchpage.setVisible(true);
       
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
