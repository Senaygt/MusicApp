
package musicapp;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.*;
import javax.swing.*;

public class PlayPage {
   long currentime=0;
   boolean  isSkip=false;
   protected static JFrame play;
   private String path;
   private String Mname;
   private String Duration;
   private String Sname;
   private MusicPlayer player=AllMusic.player;

   int value=0;
   
          private static final String TABLE_CREATION_QUERY = "CREATE TABLE IF NOT EXISTS favorite_songs (\n" +
"    username TEXT NOT NULL,\n" +
"    song_name TEXT, NOT NULL,\n" +
"    singer_name TEXT,\n" +
"    song_path TEXT,\n" +
"    duration TEXT,\n" +
"    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE\n" +
")";
          
          private static final String TABLE_CREATION_QUERY2 = "CREATE TABLE IF NOT EXISTS playlist_songs (\n" +
"    username TEXT NOT NULL,\n" +
"    song_name TEXT,NOT NULL,\n" +
"    singer_name TEXT,\n" +
"    song_path TEXT,\n" +
"    duration TEXT,\n" +
"    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE\n" +
")";
    
        public PlayPage(String Mname, String Duration, String Sname,String path,MusicPlayer player)  {
        this.Mname = Mname;
        this.Duration = Duration;
        this.Sname = Sname;
        this.path=path;
        this.player=AllMusic.player;
        
  
        
        
       
        
        
        
        ImageIcon start=new ImageIcon(getClass().getResource("/Images/başlat.jpg"));
        Image scaledImage2= start.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        start=new ImageIcon(scaledImage2);

        ImageIcon stop=new ImageIcon(getClass().getResource("/Images/s.jpg"));
        Image scaledImage3= stop.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        stop=new ImageIcon(scaledImage3);
        
        ImageIcon ileri= new ImageIcon(getClass().getResource("/Images/ileri.png"));
        Image scaledImage4= ileri.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ileri=new ImageIcon(scaledImage4);
        
        ImageIcon geri= new ImageIcon(getClass().getResource("/Images/geri.png"));
        Image scaledImage5= geri.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        geri=new ImageIcon(scaledImage5);
        
        ImageIcon favo= new ImageIcon(getClass().getResource("/Images/fav.jpg"));
        Image scaledImage6=favo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        favo=new ImageIcon(scaledImage6);
        
        ImageIcon okey= new ImageIcon(getClass().getResource("/Images/okey.jpg"));
        Image scaledImage7=okey.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        okey=new ImageIcon(scaledImage7);
        
         ImageIcon clma=new ImageIcon(getClass().getResource("/Images/çalmalist.jpg"));
         Image scaledImage8= clma.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
         clma=new ImageIcon(scaledImage8);
      
        play= new JFrame("Music Application");
        play.setSize(300,700);
        play.setLayout(new FlowLayout(FlowLayout.RIGHT));
        play.setContentPane(new regBackgroundPanel());
        
        ImageIcon ıcon= new ImageIcon(getClass().getResource("/Images/music.jpg"));
        Image scaledImage = ıcon.getImage().getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ıcon = new ImageIcon(scaledImage);
        JLabel musicIcon= new JLabel();
        musicIcon.setBounds(50, 120, 300, 300);
        musicIcon.setIcon(ıcon);
        play.add(musicIcon);
        
        JLabel musicName=new JLabel(this.Mname);
        musicName.setBounds(110, 120 , 200, 30);
        play.add(musicName);
        
        JLabel singerName= new JLabel(this.Sname);
        singerName.setBounds(110, 140, 200, 30);
        play.add(singerName);
        
        play.addWindowListener(new WindowListener(){

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(AllMusic.player!=null){
                    AllMusic.player.stop();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
   
            
        });
        
        
        double timedo=Double.parseDouble(Duration);
        int time=(int)timedo;
        int timecal=(time*60)+(int)((timedo-time)*100); 
        
        JProgressBar duration=new JProgressBar(0,timecal);
        duration.setBorderPainted(true);
        duration.setBorderPainted(true);
        duration.setBounds(50, 400, 200, 30);
        play.add(duration);
        
        JLabel tim3= new JLabel();
        tim3.setBounds(50, 380, 100, 30);
        play.add(tim3);
        
        
        
        Timer timer= new Timer(1000,e->{ 
            int value= duration.getValue();
           
           if(value<timecal){
           duration.setValue(value+1);
           int minutes = value / 60;
           int seconds = value % 60;
            tim3.setText(String.format("%02d.%02d", minutes, seconds));
   
           }
           else{
               ((Timer) e.getSource()).stop();
           }
        });
        timer.start();
        
        
        
        
        JLabel time2 =new JLabel(this.Duration);
        time2.setBounds(220, 380, 100, 30);
        play.add(time2);
        
        
        
        
        JButton startbutton=new JButton();
        startbutton.setIcon(start);
        startbutton.setBounds(135, 430, 30, 30);
        startbutton.setVisible(false);
        play.add(startbutton);
        
        JButton stopbutton=new JButton();
        stopbutton.setIcon(stop);
        stopbutton.setBounds(135, 430, 30, 30);
        play.add(stopbutton);
        
      
        
        JButton ileribut= new JButton();
        ileribut.setIcon(ileri);
        ileribut.setBounds(170, 430, 30, 30);
        play.add(ileribut);
        
        JButton geribut= new JButton();
        geribut.setIcon(geri);
        geribut.setBounds(100, 430, 30, 30);
        play.add(geribut);
        
        JButton favbut= new JButton();
        favbut.setIcon(favo);
        favbut.setBounds(210, 430, 30, 30);
        play.add(favbut);
        
        JButton clm= new JButton();
        clm.setBounds(60, 430, 30, 30);
        clm.setIcon(clma);
        play.add(clm);

        JButton last= new JButton("Next Song");
        last.setBounds(150, 480, 100, 30);
        play.add(last);
        
        JButton previous= new JButton("Previous Song");
        previous.setBounds(30, 480, 130, 30);
        play.add(previous);
        
        
 
        
        stopbutton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                timer.stop();
                startbutton.setVisible(true);
                stopbutton.setVisible(false);
               AllMusic.player.pause();
                startbutton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                     AllMusic.player.play();
                     timer.start();
                startbutton.setVisible(false);
                stopbutton.setVisible(true);
                    }  
                });
                
                }
            
        });
        
        favbut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {  

                
                String insertQuery = "INSERT INTO  favorite_songs(username,song_name,singer_name,song_path,duration) VALUES (?, ?, ?,?,?)";
    try (Connection connection = DriverManager.getConnection(RegisterPage.URL);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

                preparedStatement.setString(1, LoginPage.Username);
                preparedStatement.setString(2, Mname);
                preparedStatement.setString(3, Sname);
                 preparedStatement.setString(4, path);
                preparedStatement.setString(5, Duration);
                

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
                
           
                 favbut.setEnabled(false);
                
             
                JOptionPane.showMessageDialog(null, "this music add in favorite list");
            }
            
        });
       
        
        clm.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String insertQuery = "INSERT INTO  playlist_songs(username,song_name,singer_name,song_path,duration) VALUES (?, ?, ?,?,?)";
    try (Connection connection = DriverManager.getConnection(RegisterPage.URL);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

                preparedStatement.setString(1, LoginPage.Username);
                preparedStatement.setString(2, Mname);
                preparedStatement.setString(3, Sname);
                 preparedStatement.setString(4, path);
                preparedStatement.setString(5, Duration);
                

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
                
                clm.setEnabled(false);
                JOptionPane.showMessageDialog(null, "this music add in your playlist");
            }
            
        });
        
       
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        scheduler.scheduleAtFixedRate(()->{
            if(this.isSkip==true){
                currentime+=15000;
                isSkip=false;
            }else{
                currentime+=100;
            }
     
        }, 0, 100, TimeUnit.MILLISECONDS);

        ileribut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(duration.getValue()<=timecal-15){
                duration.setValue(duration.getValue()+15);}
                else{ duration.setValue(timecal);
                } 
               
                
                AllMusic.player.skipForward();
                
                
            }
            
        });
        
        geribut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(duration.getValue()>15){
                duration.setValue(duration.getValue()-15);}
                else{ duration.setValue(0);
                }
                AllMusic.player.skipBackward();
            }
            
        });

        last.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(AllMusic.player!=null){
                    AllMusic.player.stop();}
                
                 if(AllMusic.Music!=null&&AllMusic.Music.isVisible()){
                if(AllMusic.player!=null){
                   AllMusic.player.stop();}
                
                if (AllMusic.selectedRow ==AllMusic.rowCount-1) {
                    AllMusic.selectedRow = 0; 
            } else {
                AllMusic.selectedRow++; 
            }
                if(PlayPage.play!=null){
                    
                    PlayPage.play.setVisible(false);
                }
                
             new PlayPage((String)AllMusic.model.getValueAt(AllMusic.selectedRow, 0),
             (String)AllMusic.model.getValueAt(AllMusic.selectedRow, 2),
             (String)AllMusic.model.getValueAt(AllMusic.selectedRow, 1),
             (String)AllMusic.model.getValueAt(AllMusic.selectedRow, 3),
             player);

                
                AllMusic.player.load((String)AllMusic.model.getValueAt(AllMusic.selectedRow,3));
               AllMusic.player.play();
            }
           else if(PlaylistPage.playlist!=null&&PlaylistPage.playlist.isVisible()){
                if(PlaylistPage.player!=null){
                    PlaylistPage.player.stop();}
                
                if (PlaylistPage.selectedRow ==PlaylistPage.rowCount-1) {
                    PlaylistPage.selectedRow = 0; 
            } else {
                PlaylistPage.selectedRow++; 
            }
                if(PlayPage.play!=null){
                   AllMusic.player.stop();
                    PlayPage.play.setVisible(false);
                }
                
             new PlayPage((String)PlaylistPage.model3.getValueAt(PlaylistPage.selectedRow, 0),
             (String)PlaylistPage.model3.getValueAt(PlaylistPage.selectedRow, 2),
             (String)PlaylistPage.model3.getValueAt(PlaylistPage.selectedRow, 1),
             (String)PlaylistPage.model3.getValueAt(PlaylistPage.selectedRow, 3),
             player);

                
               AllMusic.player.load((String)PlaylistPage.model3.getValueAt(PlaylistPage.selectedRow,3));
                AllMusic.player.play();
            }
           else if(FavoritePage.favorite!=null&&FavoritePage.favorite.isVisible()){
                if(FavoritePage.player!=null){
                    FavoritePage.player.stop();}
                
                if (FavoritePage.selectedRow ==FavoritePage.rowCount-1) {
                    FavoritePage.selectedRow = 0; 
            } else {
                FavoritePage.selectedRow++; 
            }
                if(PlayPage.play!=null){
                   AllMusic.player.stop();
                    PlayPage.play.setVisible(false);
                }
                
             new PlayPage((String)FavoritePage.model.getValueAt(FavoritePage.selectedRow, 0),
             (String)FavoritePage.model.getValueAt(FavoritePage.selectedRow, 2),
             (String)FavoritePage.model.getValueAt(FavoritePage.selectedRow, 1),
             (String)FavoritePage.model.getValueAt(FavoritePage.selectedRow, 3),
             player);

                
               AllMusic.player.load((String)FavoritePage.model.getValueAt(FavoritePage.selectedRow,3));
               AllMusic.player.play();
            }
            
            }
                
           
            
        });
        
        
        
        previous.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(AllMusic.Music != null && AllMusic.Music.isVisible()){
                if(AllMusic.player!=null){
                    AllMusic.player.stop();
                PlayPage.play.setVisible(false);}
                
              
                
                        if (AllMusic.selectedRow ==0) {
                            AllMusic.player.stop();
                    AllMusic.selectedRow = AllMusic.rowCount-1; 
            } else {
                AllMusic.selectedRow--; 
                        }
               new PlayPage((String)AllMusic.model.getValueAt(AllMusic.selectedRow, 0),
             (String)AllMusic.model.getValueAt(AllMusic.selectedRow, 2),
             (String)AllMusic.model.getValueAt(AllMusic.selectedRow, 1),
             (String)AllMusic.model.getValueAt(AllMusic.selectedRow, 3),
             player);
                
               String path=(String)(AllMusic.model.getValueAt(AllMusic.selectedRow,3));
                
                AllMusic.player.load(path);
                AllMusic.player.play();
            }
           else if(PlaylistPage.playlist!=null&&PlaylistPage.playlist.isVisible()){
                if(player!=null){
                    player.stop();}
                
                if (PlaylistPage.selectedRow ==0) {
                    PlaylistPage.selectedRow = PlaylistPage.rowCount-1; 
            } else {
                PlaylistPage.selectedRow--; 
            }
                if(PlayPage.play!=null){
                  AllMusic.player.stop();
                    PlayPage.play.setVisible(false);
                }
                
             new PlayPage((String)PlaylistPage.model3.getValueAt(PlaylistPage.selectedRow, 0),
             (String)PlaylistPage.model3.getValueAt(PlaylistPage.selectedRow, 2),
             (String)PlaylistPage.model3.getValueAt(PlaylistPage.selectedRow, 1),
             (String)PlaylistPage.model3.getValueAt(PlaylistPage.selectedRow, 3),
             player);

                
               AllMusic.player.load((String)PlaylistPage.model3.getValueAt(PlaylistPage.selectedRow,3));
                AllMusic.player.play();
            }
           else if(FavoritePage.favorite.isVisible()){
                if(player!=null){
                    player.stop();
                PlayPage.play.setVisible(false);}
                
                 new PlayPage((String)FavoritePage.model.getValueAt(FavoritePage.selectedRow, 0),
             (String)FavoritePage.model.getValueAt(FavoritePage.selectedRow, 2),
             (String)FavoritePage.model.getValueAt(FavoritePage.selectedRow, 1),
             (String)FavoritePage.model.getValueAt(FavoritePage.selectedRow, 3),
             player);
                
                if (FavoritePage.selectedRow ==0) {
                    FavoritePage.selectedRow = FavoritePage.rowCount-1; 
            } else {
                FavoritePage.selectedRow--; 
            }
                
               AllMusic.player.load((String)FavoritePage.model.getValueAt(FavoritePage.selectedRow,3));
               AllMusic.player.play();
            }
            
            }
                
        
            
        });
        
    

        play.setLayout(null);
        play.setVisible(true);
        
        
    }
        
                
    
        

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMname() {
        return Mname;
    }

    public void setMname(String Mname) {
        this.Mname = Mname;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String Duration) {
        this.Duration = Duration;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String Sname) {
        this.Sname = Sname;
    }
    
    
    
    
   
    
}
