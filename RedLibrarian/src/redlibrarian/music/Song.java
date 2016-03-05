 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.music;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * Entity object used for persistence mapping with Hibernate ORM.
 * Stores information about musical scores.
 * @author Joseph Manahan
 */
@Entity 
public class Song implements Comparable, Serializable {
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private long uid;
    
    private int pid;
    private String title;
    private String composer;
    private String description;
    private boolean available;
    @Temporal(javax.persistence.TemporalType.DATE)
    private final Calendar dateAdded;

    public Song() {
        this.pid = -1;
        this.title = null;
        this.composer = null;
        this.available = false;
        this.dateAdded = Calendar.getInstance();
    }
    
    public Song(int id, String title, String composer) {
        this.pid = id;
        this.title = title;
        this.composer = composer;
        this.available = true;
        this.dateAdded = Calendar.getInstance();
    }
    /**
     * Returns if the score is checked out of the library.
     * @return
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the score to be available or unavailable for checkout.
     * @param available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Returns the ID of the score.
     * @return
     */

    public long getUniqueId() {
        return uid;
    }

    public int getPseudoId() {
        return pid;
    }
    /**
     * Returns the title of the score.
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the composer of the score.
     * @return
     */
    public String getComposer() {
        return composer;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     *
     * @return
     */
    public Calendar getDateAdded() {
        return dateAdded;
    }

    /**
     * Sets the title of the score.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setPseudoId(int id) {
        this.pid = id;
    }

    /**
     * Sets the composer of the score.
     * @param composer
     */
    public void setComposer(String composer) {
        this.composer = composer;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s", title, composer, available?"AVAILABLE":"UNAVAILABLE", dateAdded);
    }

    public int compareTo(Song otherSong) {
        if(this.getUniqueId()!=otherSong.getUniqueId())
            return this.getUniqueId()>otherSong.getUniqueId()?1:-1;
        if(!this.getTitle().equals(otherSong.getTitle()))
            return this.getTitle().compareTo(otherSong.getTitle());
        if(this.getPseudoId()!=otherSong.getPseudoId())
            return this.getPseudoId()>otherSong.getPseudoId()?1:-1;
        if(!this.getComposer().equals(otherSong.getComposer()))
            return this.getComposer().compareTo(otherSong.getComposer());
        if(this.isAvailable()!=otherSong.isAvailable())
            return this.isAvailable()?1:-1;
        if(!this.getDateAdded().equals(otherSong.getDateAdded()))
            return this.getDateAdded().compareTo(otherSong.getDateAdded());
        return this.getDescription().compareTo(otherSong.getDescription());
    }

    @Override
    public int compareTo(Object o) {
        if(o.getClass().toString().equals("Song"))
            return compareTo((Song)o);
        return this.hashCode()>o.hashCode()?1:this.hashCode()==o.hashCode()?0:-1;
    }
}
