/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.ini4j.Ini;
import redlibrarian.GUI.UserInterface;

/**
 *
 * @author Joseph
 */
public class RedLibrarian {

    public static SessionFactory sessionFactory;
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws InterruptedException, MalformedURLException {  
        
        
        UserInterface window = new UserInterface(new URL("config.ini"));
        window.load();
        window.setVisible(true);  
        
    }


    
}
