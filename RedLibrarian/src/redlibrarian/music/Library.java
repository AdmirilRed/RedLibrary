/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.music;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Joseph Manahan
 */
@Entity
public class Library implements Serializable {

    @Id
    private long id;
    private String name;
    private String description;
    
    /**
     *
     */
    @ManyToMany (fetch = FetchType.EAGER)
    public Set<Song> contents = new TreeSet<>();
    
    
    public Library() {
        this.id = -1l;
    }    
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDesciption() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean addSong(Song item) {
        return contents.add(item);
    }
    
    public boolean removeSong(Song target) {
        return contents.remove(target);
    }
    
    public boolean removeSong(long targetId) {
        for(Song song:contents)
            if(song.getId()==targetId)
                return contents.remove(song);
        return false;        
    }
    
    public Set<Song> getContents() {
        return contents;
    }
            
    @Override
    public String toString() {
        return String.format("LIBRARY<%s, %s %s>", name, description, contents);
    }
}
