
package musicapp;

import java.awt.*;
import javax.swing.*;


public class regBackgroundPanel extends JPanel {
    private Image regBackgroundImage;
    public regBackgroundPanel() {
        regBackgroundImage= new ImageIcon(getClass().getResource("/Images/kayıt.png")).getImage();
    }
    protected void paintComponent(Graphics grap){
        super.paintComponent(grap);
        grap.drawImage(regBackgroundImage, 0, 0,getWidth(),getHeight(), this);
    }
    
}
