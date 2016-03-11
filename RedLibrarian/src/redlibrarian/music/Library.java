/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

/**
 *
 * @author Joseph Manahan
 */
@Entity
public class Library implements Serializable {

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private long id;
    
    private String name;
    private String description;
    
    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @OrderBy("pid ASC")
    private Set<Song> contents;
    
    public Library(String name, String description) {
        this.contents = new TreeSet<>();
        this.name = name;
        this.description = description;
    }
    
    public Library() {
        this.contents = new TreeSet<>();
        
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
            if(song.getUniqueId()==targetId)
                return contents.remove(song);
        return false;        
    }
    
    /**
     *
     * @return
     */
    public Set<Song> getContents() {
        return contents;
    }
            
    @Override
    public String toString() {
        return String.format("%s, %s %s", name, description, contents);
    }
}
