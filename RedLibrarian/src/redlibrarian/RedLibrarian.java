/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.hibernate.SessionFactory;
import redlibrarian.GUI.UserInterface;

/**
 *
 * @author Joseph
 */
public class RedLibrarian {

    public static SessionFactory sessionFactory;
    public static Image icon;
    
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {  
        
        setIcon("RedLibrarian_Icon.png");
        
        UserInterface window = new UserInterface();
        window.load();
        window.setVisible(true);  
        
    }

    public static void setIcon(String file) {
        try {
            icon = new ImageIcon(ImageIO.read(RedLibrarian.class.getResourceAsStream(file))).getImage();
        } catch(IOException e) {
            System.out.println("WARN: Could not load icon!");
        }
    }

    
}
