
package musicapp;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.sql.*;


public class LoginPage {
    private JFrame login;
    protected static String Username;
   
    public LoginPage() {
 
      
     
        
       login= new JFrame("Music Application");
       login.setSize(800,600);
       login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       login.setLocationRelativeTo(null);
       login.setContentPane(new regBackgroundPanel());
       
       Font logFont= new Font("Arial",Font.ITALIC,18);
       
       JLabel userName= new JLabel("Username: "); 
       userName.setBounds(250,50,120,30);
       userName.setFont(logFont);
       login.add(userName);

       JTextField userField= new JTextField();
       userField.setBounds(340, 50, 180, 30);
       login.add(userField);
      
       
       JLabel password= new JLabel("Password: ");
       password.setBounds(250, 100 , 120, 30);
       password.setFont(logFont);
       login.add(password);
       
       JPasswordField pasField = new JPasswordField();
       pasField.setBounds(340, 100, 180, 30);
       login.add(pasField);
       
       JButton giriş= new JButton("Log in");
       giriş.setBounds(340, 190, 130 , 30);
      
       login.add(giriş);
       
        JLabel already= new JLabel("If you don't have an account");
       already.setBounds(10, 60, 220, 30);
       login.add(already);
       
        ImageIcon ıcon=new ImageIcon(getClass().getResource("/Images/93634.png"));
       Image scaledImage = ıcon.getImage().getScaledInstance(130, 70, Image.SCALE_SMOOTH);
       ıcon = new ImageIcon(scaledImage);
       
       JButton geri= new JButton();
       geri.setBounds(10, 10, 120, 50);
       geri.setIcon(ıcon);
       login.add(geri);
       
       
       
       JButton forgetpas=new JButton("Forget Password");
       forgetpas.setBounds(400,150,200,30);
       forgetpas.setBackground(Color.pink);
       forgetpas.setOpaque(true);
       forgetpas.setContentAreaFilled(true);
       forgetpas.setForeground(Color.black);
       login.add(forgetpas);
       
       JPanel forpanel= new JPanel();
       forpanel.setBounds(300,300,200,100);
       
       JLabel forname= new JLabel("Name-Surname");
       forpanel.add(forname);
       
       
       JTextField forfld= new JTextField();
       forpanel.add(forfld);
       
       
       JLabel fornuserame= new JLabel("Username");
       forpanel.add(fornuserame);
       
       
       JTextField foruser= new JTextField();
       forpanel.add(foruser);

       JLabel showpassword= new JLabel("");
       forpanel.add(showpassword);
       
       JButton checkInfo= new JButton("Check");
       forpanel.add(checkInfo);
       
       forpanel.setLayout(new BoxLayout(forpanel,BoxLayout.Y_AXIS));
       
       
       
        
       
       forgetpas.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               JOptionPane.showMessageDialog(null,forpanel,"",JOptionPane.PLAIN_MESSAGE);
           }
           
       });
       
       checkInfo.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
        
        String enteredUsername = foruser.getText().trim();
        String enteredName = forfld.getText().trim();

        if (enteredUsername.isEmpty() || enteredName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter both Username and Name.");
            return;
        }

      
        String query = "SELECT password FROM users WHERE Username = ? AND Name = ?";

        try (Connection conn = DriverManager.getConnection(RegisterPage.URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, enteredUsername);
            stmt.setString(2, enteredName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    
                    String password = rs.getString("password");
                    showpassword.setText("Password: "+password);
                    
                } else {
                    
                    JOptionPane.showMessageDialog(null, "Username or Name is incorrect.");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving password: " + ex.getMessage());
        }
    }
           
       });
       
        geri.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               BeginPage begin=new BeginPage();
               login.setVisible(false);
           }
           
       });
       
       giriş.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
                Username=userField.getText();
               String password=String.valueOf(pasField.getPassword());
              if(authenticateUser(Username, password)) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    MenüPage menü=new MenüPage();
                    login.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password.");
                }
            }
           
       });

       
       login.setLayout(null);
       login.setVisible(true);
       
      
       
       
        
    }
     private boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(RegisterPage.URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return false;
        }
    }
    
}
