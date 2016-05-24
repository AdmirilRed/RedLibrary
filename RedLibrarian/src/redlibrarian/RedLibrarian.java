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
import redlibrarian.utility.Update;

/**
 *
 * @author Joseph
 */
public class RedLibrarian {

    public static SessionFactory sessionFactory;
    public static Image icon;
    
    private final static String version = "v2.00";
    
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, IOException {  
        
        setIcon("RedLibrarian_Icon.png");
        
        //UserInterface window = new UserInterface();
        //window.load();
        
        Update update = new Update("https://s3.amazonaws.com/redlibrarian-versioning");
        
        //window.setVisible(true);  
        
    }

    public static void setIcon(String file) {
        try {
            icon = new ImageIcon(ImageIO.read(RedLibrarian.class.getResourceAsStream(file))).getImage();
        } catch(IOException e) {
            System.out.println("WARN: Could not load icon!");
        }
    }
    
    public static String getVersion() {
        return version;
    }

    
}
