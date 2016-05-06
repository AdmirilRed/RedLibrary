/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.utility;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import redlibrarian.GUI.UserInterface;
import redlibrarian.login.ContactDetails;

/**
 *
 * @author admir
 */
@Entity
public class BugReport implements Serializable {
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private long id;
    
    private String summary;
    private String description;
    
    @ManyToOne
    private ContactDetails contact;

    public BugReport() {
        
    }
    
    public BugReport(ContactDetails contact, String summary, String description) {
        this.summary = summary;
        this.description = description;
        this.contact = contact;
    }
    
    public boolean save(SessionFactory sessionFactory) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
        
            session.saveOrUpdate(this);
        
            session.getTransaction().commit();
            return true;
        }
        catch(HibernateException hibernateException) {
            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, hibernateException);
            return false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
