
package musicapp;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenüPage {

    public MenüPage() {
        
    
     
       JFrame menü= new JFrame("Music Application");
       menü.setSize(800,600);
       menü.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       menü.setLocationRelativeTo(null);
       menü.setContentPane(new menüBackgroundPanel());
       
       JButton AllMusic=new JButton("ALL MUSİC");
       AllMusic.setBounds(40,20,130,30);
       menü.add(AllMusic);
       
       JLabel hel= new JLabel("Hello ");//username
       hel.setBounds(350, 40, 200, 30);
       Font helfont= new Font("Arial",Font.BOLD,20);
       hel.setFont(helfont);
       menü.add(hel);
       
       JButton fav= new JButton("Favorite");
       fav.setBounds(60,240,150,30);
       menü.add(fav);
       
       JButton sing= new JButton("Singer");
       sing.setBounds(300,240,150,30);
       menü.add(sing);
       
        JButton play= new JButton("Playlist");
        play.setBounds(580, 240, 150, 30);
        menü.add(play);
        
         JButton search= new JButton("Search");
        search.setBounds(60, 480, 150, 30);
        menü.add(search);
        
        JButton friend= new JButton("Add Friend");
        friend.setBounds(310, 480, 150, 30);
        menü.add(friend);
        
        JButton profil= new JButton("Profile");
        profil.setBounds(610, 480, 150, 30);
        menü.add(profil);
        
        JButton exit= new JButton("Exit");
        exit.setBounds(680,10,100,30);
        exit.setForeground(Color.red);
        menü.add(exit);
        
        

        fav.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                FavoritePage favoritep=new FavoritePage();
                menü.setVisible(false);
            }
           
       });
        
        AllMusic.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                AllMusic music=new AllMusic();
                menü.setVisible(false);
            }
            
        });
        
        sing.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               SingerPage singerp= new SingerPage();
               menü.setVisible(false);
           }
            
        });
        
        play.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               PlaylistPage playlistp= new PlaylistPage(); 
               menü.setVisible(false);
           }
            
        });
        
        search.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               SearchPage searchp=new SearchPage();
               menü.setVisible(false);
           }
            
        });
        
        friend.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               FriendPage friendp=new FriendPage();
               menü.setVisible(false);
           }
            
        });
        
        profil.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               ProfilePage profilp=new ProfilePage();
               menü.setVisible(false);
              
           }
            
        });
        
        exit.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               int response = JOptionPane.showConfirmDialog(null, "Are you sure?","",JOptionPane.YES_NO_OPTION);
               
               if(response==JOptionPane.YES_OPTION){
                   BeginPage begin=new BeginPage();
                   menü.setVisible(false);
               }  
           }
            
        });
        
        
        menü.setLayout(null);
        menü.setVisible(true);
        
    
}
}