/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity object used for persistence mapping with Hibernate ORM.
 * Stores information about musical scores.
 * @author Joseph Manahan
 */
@Entity
public class MusicDetails {
    
    @Id 
    int id;
    private String title;
    private String composer;
    private String library;
    private boolean available;

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
    public int getId() {
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

    /**
     * Returns the library the score is in.
     * @return
     */
    public String getLibrary() {
        return library;
    }

    /**
     * Sets the ID of the score.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
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

    /**
     * Sets the library of the score.
     * @param library
     */
    public void setLibrary(String library) {
        this.library = library;
    }
    
    
}
