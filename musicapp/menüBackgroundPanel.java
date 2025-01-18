/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package musicapp;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author sena
 */
public class menüBackgroundPanel extends JPanel {
    private Image menüBackgroundPanel;

    public menüBackgroundPanel() {
        menüBackgroundPanel= new ImageIcon(getClass().getResource("/Images/menü.png")).getImage();
    }
    protected void paintComponent(Graphics grap){
        super.paintComponent(grap);
        grap.drawImage(menüBackgroundPanel, 0, 0,getWidth(),getHeight(), this);
    }
}
