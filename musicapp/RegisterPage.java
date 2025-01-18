
package musicapp;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import java.sql.*;


public class RegisterPage  {
        private JFrame reg;
       protected static String username;
       protected static String password;
       protected static String namefld;
               protected static final String URL = "jdbc:sqlite:SaveUser.db"; 
                private static final String TABLE_CREATION_QUERY = "CREATE TABLE IF NOT EXISTS users (" +
            "username TEXT PRIMARY KEY,NOT NULL" +
            "name TEXT NOT NULL," +
            "password TEXT NOT NULL" +
            ");";


    public RegisterPage() {
        
   
       
       reg= new JFrame("Music Application");
       reg.setSize(800,600);
       reg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       reg.setLocationRelativeTo(null);
       reg.setContentPane(new regBackgroundPanel());
       
       
       
       JLabel name= new JLabel("Name-Surname: ");
       name.setBounds(230, 130, 200, 50);
       Font namefont = new Font("Arial", Font.BOLD, 17);
       name.setFont(namefont);
       reg.add(name);
       
       
       JLabel Username= new JLabel("Username: ");
       Username.setBounds(230, 175, 200, 50);
       Font userfont = new Font("Arial", Font.BOLD, 17);
       Username.setFont(userfont);
       reg.add(Username);
       
       
       JLabel pas= new JLabel("Password: ");
       pas.setBounds(230, 220, 200, 50);
       Font pasfont = new Font("Arial", Font.BOLD, 17);
       pas.setFont(pasfont);
       reg.add(pas);
       
       JTextField name1= new JTextField();
       name1.setBounds(370,140,130,30);
       reg.add(name1);
       
       JTextField Username1= new JTextField();
       Username1.setBounds(370,185,130,30);
       reg.add(Username1);
       
       
       JPasswordField pas1= new JPasswordField();
       pas1.setBounds(370, 230, 130, 30);
       reg.add(pas1);
       
       JCheckBox onay= new JCheckBox("I approve");
       onay.setBounds(230, 275, 130, 30);
       reg.add(onay);
       
       JButton reg2= new JButton("Register");
       Font ab= new Font("Arial",Font.BOLD,18);
       reg2.setFont(ab);
       reg2.setBounds(290,315,200,30);
       reg.add(reg2);
       
       reg2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               
                if(!(Username1.getText().isEmpty()&&pas1.getPassword().length==0&&name.getText().isEmpty())){
                username=Username1.getText();
                password=String.valueOf(pas1.getPassword());
                namefld= name1.getText();
                String insertQuery = "INSERT INTO  users(Username,Name,Password) VALUES (?, ?, ?)";
                try (Connection connection = DriverManager.getConnection(URL);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, namefld);
                preparedStatement.setString(3, password);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "User registered successfully in the database!");

            } catch (SQLException ex) {
                if (ex.getMessage().contains("UNIQUE constraint failed")) {
                    JOptionPane.showMessageDialog(null, "This username is already registered.");
                } else {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
                return;
            }
                
               
                BeginPage begin= new BeginPage();
                reg.setVisible(false);
                
                FriendPage.model4.addRow(new Object[]{username});
            } else{
                    JOptionPane.showMessageDialog(null, "You must approve");
                }
                    }
            
            
           
       });
       
       JLabel already= new JLabel("If you have an account");
       already.setBounds(10, 60, 220, 30);
       reg.add(already);
       
       ImageIcon ıcon=new ImageIcon(getClass().getResource("/Images/93634.png"));
       Image scaledImage = ıcon.getImage().getScaledInstance(130, 70, Image.SCALE_SMOOTH);
       ıcon = new ImageIcon(scaledImage);
      
       
       JButton geri= new JButton();
       geri.setIcon(ıcon);
       geri.setBounds(10, 10, 120, 50);
       reg.add(geri);
       
     
       
       
       
       geri.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               BeginPage begin=new BeginPage();
               reg.setVisible(false);
           }
           
       });
       
       
       reg.setLayout(null);
       reg.setVisible(true);
       
      
       
       
    }
    
    
    private void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            statement.execute(TABLE_CREATION_QUERY);
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    
    
    
}}
