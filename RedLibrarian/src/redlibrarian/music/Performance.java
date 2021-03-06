/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Joseph Manahan
 */
@Entity
public class Performance implements Serializable {
   
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private long id; 
            
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar concertDate;
    private String title;
    private String description;
    
    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    private List<Song> songs = new ArrayList<>();
    
    public Performance() {
        this.concertDate = Calendar.getInstance();
    }
    
    public Performance(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }
        
    public Performance(String title, String description, Calendar date) {
        this(title, description);
        this.concertDate = date;
    }
    
    public Performance(String title, String description, Calendar date, List<Song> songs) {
        this(title, description, date);
        for(Song song:songs)
            this.addSong(song);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public Calendar getDate() {
        return concertDate;
    }
    
    public void setDate(Calendar newDate) {
        this.concertDate = newDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
    
    public boolean addSong(Song item) {
        boolean result = songs.add(item);
        Collections.sort(songs);
        return result;
    }
    
    public boolean removeSong(Song target) {
        return songs.remove(target);
    }   
    
    public boolean removeSong(long targetId) {
        for(Song song:songs)
            if(song.getUniqueId()==targetId)
                return removeSong(song);
        return false;
    }
    
    public void removeLibrary(Library lib) {
        Stack<Song> removeList = new Stack<>();
        for(Song song:songs) {
            if(song.getLibrary().equals(lib))
                removeList.push(song);
        }
        while(removeList.size() > 0)
            removeSong(removeList.pop());
        
    }
    
    public List<Song> getPlaylist() {
        return songs;
    }
    
    @Override
    public String toString() {
        return String.format("%s %tF", title, concertDate);
    }

    public void removeAllSongs() {
        songs = new ArrayList<>();
    }
}
