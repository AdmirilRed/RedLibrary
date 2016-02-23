/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.music;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Joseph Manahan
 */
@Entity
public class Performance implements Serializable {
   
    @Id
    private long id; 
            
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar date;
    private String title;
    private String description;
    
    @ManyToMany (fetch = FetchType.EAGER)
    private Set<Song> songs = new TreeSet<>();
    
    public Performance() {
        this.id = -1l;
        this.date = Calendar.getInstance();
    }
    
    public Performance(long id, String title, String description, Calendar date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean addSong(Song item) {
        return songs.add(item);
    }
    
    public boolean removeSong(Song target) {
        return songs.remove(target);
    }   
    
    public boolean removeSong(long targetId) {
        for(Song song:songs)
            if(song.getId()==targetId)
                return songs.remove(song);
        return false;
    }
    
    public Set<Song> getPlaylist() {
        return songs;
    }
    
    @Override
    public String toString() {
        return String.format("PERFORMANCE<%s, %s | %tF | %s>", title, description, date, songs);
    }
}
