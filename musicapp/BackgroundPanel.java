
package musicapp;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author sena
 */
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;
    public BackgroundPanel() {
        backgroundImage = new ImageIcon(getClass().getResource("/Images/beginPage.png")).getImage();
           
    }
    protected void paintComponent(Graphics grap){
        super.paintComponent(grap);
        grap.drawImage(backgroundImage, 0, 0,getWidth(),getHeight(), this);
    }
    
}

