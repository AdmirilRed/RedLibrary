/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian;

import redlibrarian.GUI.UserInterface;
import org.hibernate.SessionFactory;

/**
 *
 * @author Joseph
 */
public class RedLibrarian {

    public static SessionFactory sessionFactory;
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        
        UserInterface window = new UserInterface();
        window.load();
        window.setVisible(true);
        
    }


    
}
