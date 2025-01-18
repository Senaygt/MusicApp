
package musicapp;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;



public class MusicPlayer {
  
    protected Clip clip;
    protected boolean isPlaying;
    private long pausePosition; 
    private static final int SKIP_TIME = 15 * 1000000;//15 second

  
    public void load(String filePath) {
    try {
        File audioFile = new File(filePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource(filePath));

        clip = AudioSystem.getClip();
        clip.open(audioStream);
        System.out.println("Müzik dosyası yüklendi: " + filePath);
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        System.err.println("Müzik dosyası yüklenemedi: " + e.getMessage());
        e.printStackTrace();
    }
}

    // Müziği başlatma
    public void play() {
        if (clip != null) {
            clip.setMicrosecondPosition(pausePosition); 
            clip.start();
            
        }
    }

    
    public void pause() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getMicrosecondPosition(); 
            clip.stop();
           
        }
    }

    
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            pausePosition = 0; 
            
        }
    }
    
    public void skipForward() {
    if (clip == null) {
        System.out.println("Müzik dosyası yüklenmedi!");
        return;
    }
    pausePosition = clip.getMicrosecondPosition();
    long newPosition = pausePosition + SKIP_TIME;
    if (newPosition < clip.getMicrosecondLength()) {
        clip.setMicrosecondPosition(newPosition);
    } else {
        clip.setMicrosecondPosition(clip.getMicrosecondLength());
    }
}
    
    public void skipBackward(){
       pausePosition=clip.getMicrosecondPosition();
        long newPosition=pausePosition-SKIP_TIME;
       if(newPosition>0){
           clip.setMicrosecondPosition(newPosition);
       }else{
           clip.setMicrosecondPosition(0);
       }
       
    }

    public  boolean isPlaying() {
       return clip.isRunning();
               
        
    }
    
   

}
    
   


