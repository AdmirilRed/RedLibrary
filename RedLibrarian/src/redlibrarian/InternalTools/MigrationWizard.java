/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.InternalTools;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import redlibrarian.music.Song;

/**
 *
 * @author admir
 */
public class MigrationWizard {
    
    public MigrationWizard(String sourceURL, String sourceUsername, String sourcePassword, String destinationURL, String destinationUsername, String destinationPassword) {
        
        Configuration sourceCfg = new Configuration();
                sourceCfg.configure("hibernate.cfg.xml"); //hibernate config xml file name
                sourceCfg.getProperties().setProperty("hibernate.connection.password", sourcePassword);
                sourceCfg.getProperties().setProperty("hibernate.connection.username", sourceUsername);
                sourceCfg.getProperties().setProperty("hibernate.connection.url", sourceURL);
        SessionFactory sourceSessionFactory = sourceCfg.buildSessionFactory();
        
        Configuration destinationCfg = new Configuration();
                destinationCfg.configure("hibernate.cfg.xml"); //hibernate config xml file name
                destinationCfg.getProperties().setProperty("hibernate.connection.password", destinationPassword);
                destinationCfg.getProperties().setProperty("hibernate.connection.username", destinationUsername);
                destinationCfg.getProperties().setProperty("hibernate.connection.url", destinationURL);
        SessionFactory destinationSessionFactory = destinationCfg.buildSessionFactory();
        
        ArrayList<Song> songList = new ArrayList<>();
        
        Session sourceSession = sourceSessionFactory.getCurrentSession();
        sourceSession.beginTransaction();   
        
        SQLQuery query = sourceSession.createSQLQuery("SELECT PID,Title,Composer FROM music ORDER BY PID");
        List<Object []> songs = query.list();
        
        for(Object [] song:songs)
        {
            songList.add(new Song((int)song[0],(String)song[1],(String)song[2], null));
        }    
         
        System.out.println(songList);
        sourceSession.getTransaction().commit();
        
        
        Session destinationSession = destinationSessionFactory.getCurrentSession();
        destinationSession.beginTransaction();
        
        for(Song song:songList)
            destinationSession.save(song);
        
        destinationSession.getTransaction().commit();
        
        
        
    }
    
}
