
package musicapp;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;


public class BeginPage {
    private JFrame begin;
    
    public BeginPage() {
        
       begin= new JFrame("Music Application");
       begin.setSize(800,600);
       begin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       begin.setLocationRelativeTo(null);
       begin.setContentPane(new BackgroundPanel());
        

       JLabel wel= new JLabel("Welcome");
       wel.setBounds(350, 150, 130, 50);
       Font welfont = new Font("Arial", Font.BOLD, 25);
       wel.setFont(welfont);
       begin.add(wel);
       
       JButton grş= new JButton("Log in");
       grş.setBounds(300,250,200,30);
       Font grşfont = new Font("Arial", Font.BOLD, 17);
       grş.setFont(grşfont);
       begin.add(grş);
       
       
       JButton kayıt= new JButton("Register");
       kayıt.setBounds(300,300,200,30);
       Font kytfont = new Font("Arial", Font.BOLD, 17);
       kayıt.setFont(kytfont);
       begin.add(kayıt);
       
        JButton exit= new JButton("Exit");
        exit.setBounds(680,10,100,30);
        exit.setForeground(Color.red);
        begin.add(exit);
       
       kayıt.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e) {
                   RegisterPage registerPage= new RegisterPage();
                   begin.setVisible(false);
             }
             
           
       });
       
       grş.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               LoginPage login= new LoginPage();
                begin.setVisible(false);
           }
           
       });
       
       exit.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               int response = JOptionPane.showConfirmDialog(null, "Are you sure?","",JOptionPane.YES_NO_OPTION);
               
               if(response==JOptionPane.YES_OPTION){
                   System.exit(0);
               }  
           }
            
        });
       
       
       begin.setLayout(null);
       begin.setVisible(true);
        
    }
    
    
    
}
