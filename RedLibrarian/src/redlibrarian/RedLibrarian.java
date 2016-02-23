/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import redlibrarian.login.LoginPrompt;
import redlibrarian.music.Library;
import redlibrarian.music.Performance;
import redlibrarian.music.Song;

/**
 *
 * @author Joseph
 */
public class RedLibrarian {

    public static SessionFactory sessionFactory;
    private static String UserName,Password,URL;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            String URL = "jdbc:mysql://commstationfive.net:3306/RedLibrary_Test";
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml"); //hibernate config xml file name
            cfg.getProperties().setProperty("hibernate.connection.password", "rootbeer"); //REMOVE
            cfg.getProperties().setProperty("hibernate.connection.username", "root"); //REMOVE
            cfg.getProperties().setProperty("hibernate.connection.url", URL);
            sessionFactory = cfg.buildSessionFactory();
            System.out.println("SUCCESS");
        } catch (HibernateException hibernateException) {
            System.out.println("FAILURE\n"+hibernateException);
            System.exit(999);     
        }
         
        Session session = sessionFactory.openSession();
        
        
        session.beginTransaction();
        
        Library marching = new Library();
        Library concert = new Library();
        Performance perf1 = new Performance(1l,"Patriotic Concert","Combined choir and band",Calendar.getInstance());
        Performance perf2 = new Performance(2l,"Christmas Concert","",Calendar.getInstance());
        Song song1 = new Song(199l,"King, My Queen","Followed by Ghosts");
        Song song2 = new Song(300l,"Tempting Time","Animals as Leaders");
        Song song3 = new Song(999l,"Symphony No. 5","Dmitry Shostakovich");
        
        marching.setId(50l);
        marching.setName("Marching");
        marching.setDescription("A test of the new library system");
        marching.addSong(song1);
        marching.addSong(song2);
        
        concert.setId(1l);
        concert.setName("Concert");
        concert.setDescription("A test of the new library system");
        concert.addSong(song1);
        concert.addSong(song3);
        
        perf1.addSong(song1);
        perf1.addSong(song2);
        perf2.addSong(song2);
        perf2.addSong(song3);
        
        session.save(marching);
        session.save(concert);
        session.save(perf1);
        session.save(perf2);
        session.save(song1);
        session.save(song2);
        session.save(song3);
        
        session.getTransaction().commit();
        session.close();
        
        System.out.println("DONE");
        System.out.println(concert);
        System.out.println(marching);
        System.out.println(perf1);
        System.out.println(perf2);
        
        session = sessionFactory.openSession();
        session.beginTransaction();
        
        Library test1 = (Library) session.get(Library.class,1l);
        Performance test2 = (Performance) session.get(Performance.class,1l);
        
        session.getTransaction().commit();
        session.close();
        
        System.out.println("\n"+test1);
        System.out.println(test2);
        System.exit(0);
    }

    private static void login(java.awt.Frame parent, boolean modal) {
        
        LoginPrompt prompt = new LoginPrompt(parent, modal);
        
        while(!prompt.isLoggedIn()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(RedLibrarian.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
