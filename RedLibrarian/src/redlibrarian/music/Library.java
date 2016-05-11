/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    
    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @OrderBy("pid ASC")
    private List<Song> contents;
    
    public Library(String name, String description) {
        this.contents = new ArrayList<>();
        this.name = name;
        this.description = description;
    }
    
    public Library() {
        this.contents = new ArrayList<>();
        
    }    
   
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    protected boolean addSong(Song item) {
        boolean result = contents.add(item);
        item.setLibrary(this);
        Collections.sort(this.contents);
        return result;
    }
    
    protected boolean removeSong(Song target) {
        System.out.println("Pre-remove song: "+target.getLibrary());
        target.setLibrary(null);
        System.out.println("Post-remove song: "+target.getLibrary());
        return contents.remove(target);
    }
    
    protected boolean removeSong(long targetId) {
        for(Song song:contents)
            if(song.getUniqueId()==targetId)
                return removeSong(song);
        return false;        
    }
    
    /**
     *
     * @return
     */
    public List<Song> getContents() {
        return contents;
    }
            
    @Override
    public String toString() {
        return String.format("%s, %s %s", name, description, contents);
    }
}
