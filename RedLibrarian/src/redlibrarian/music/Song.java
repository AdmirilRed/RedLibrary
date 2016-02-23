 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.music;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * Entity object used for persistence mapping with Hibernate ORM.
 * Stores information about musical scores.
 * @author Joseph Manahan
 */
@Entity 
public class Song implements Comparable, Serializable {
    
    @Id
    private final long id;
    private String title;
    private String composer;
    private String description;
    private boolean available;
    @Temporal(javax.persistence.TemporalType.DATE)
    private final Calendar dateAdded;

    public Song() {
        this.id = -1l;
        this.title = null;
        this.composer = null;
        this.available = false;
        this.dateAdded = Calendar.getInstance();
    }
    
    public Song(long id, String title, String composer) {
        this.id = id;
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

    public long getId() {
        return id;
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
        return String.format("(Title: %s, Composer: %s | %s %tF)", title, composer, available?"AVAILABLE":"UNAVAILABLE", dateAdded);
    }

    public int compareTo(Song otherSong) {
        if(this.getId()!=otherSong.getId())
            return this.getId()>otherSong.getId()?1:-1;
        if(!this.getTitle().equals(otherSong.getTitle()))
            return this.getTitle().compareTo(otherSong.getTitle());
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
        if(o.getClass().equals("Song"))
            return compareTo((Song)o);
        return this.hashCode()>o.hashCode()?1:this.hashCode()==o.hashCode()?0:-1;
    }
}
