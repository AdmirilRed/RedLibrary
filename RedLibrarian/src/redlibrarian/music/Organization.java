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
import java.util.Stack;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import redlibrarian.login.ContactDetails;
import redlibrarian.login.Password;

/**
 *
 * @author admir
 */
@Entity
public class Organization implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    
    private String name; 
    private final byte [] passwordHash;
    private final byte [] salt;  
    
    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @OrderBy("date ASC")
    private final List<Performance> performances;
    
    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @OrderBy("name ASC")
    private final List<Library> libraries;
    
    @OneToOne
    private ContactDetails contact;
    
    @Transient
    private boolean verifiedAdmin;
    
    public Organization(String name, String password) {
        this.libraries = new ArrayList<>();
        this.performances = new ArrayList<>();
        this.name = name;
        this.salt = Password.getNextSalt();
        this.passwordHash = Password.hash(password.toCharArray(), salt);
    }
    
    public Organization() {
        this.libraries = new ArrayList<>();
        this.performances = new ArrayList<>();
        this.name = "";
        this.salt = Password.getNextSalt();
        String temp = Password.generateRandomPassword(10);
        this.passwordHash = Password.hash(temp.toCharArray(), salt);
        System.out.println("GENERATED KEY: "+temp);
    }

    public long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        for(Library lib:libraries)
            for(Song song:lib.getContents())
                songs.add(song);
        Collections.sort(songs);
        return songs;
    }
    

    
    public ContactDetails getContactInfo() {
        return contact;
    }
    
    public boolean addPerformance(Performance item) {
        return performances.add(item);
    }
    
    public boolean removePerformance(Performance target) {
        return performances.remove(target);
    }
    
    public boolean removePeformance(long targetId) {
        for(Performance perf:performances)
            if(perf.getId()==targetId)
                return removePerformance(perf);
        return false;        
    }
    
    public List<Performance> getPerformances() {
        return performances;
    }
    
    public boolean addSong(Song song) {
        if(verifiedAdmin)
            return song.getLibrary().addSong(song);
        return false;            
    }

    public boolean removeSong(Song song) {
        if(verifiedAdmin) {
            for(Performance perf:performances)
                perf.removeSong(song);
            return song.getLibrary().removeSong(song);    
        }
            
        return false;
    }
    
    public void removeAllSongs(Library lib) {
        if(verifiedAdmin) {
            Stack<Song> removeList = new Stack<>();
            for(Song song:lib.getContents())
                removeList.push(song);
            while(removeList.size() > 0)
                removeSong(removeList.pop());
        }
    }
    
    public boolean addLibrary(Library item) {
        return libraries.add(item);
    }
    
    public boolean removeLibrary(Library target) { 
        removeAllSongs(target);
        return libraries.remove(target);
    }
    
    public boolean removeLibrary(long targetId) {
        for(Library lib:libraries)
            if(lib.getId()==targetId)
                return removeLibrary(lib);
        return false;        
    }
    
    public List<Library> getLibraries() {
        return libraries;
    }
    
    public boolean verifyPassword(String password) {
        this.verifiedAdmin = Password.isExpectedPassword(password.toCharArray(), salt, passwordHash);
        return verifiedAdmin;
    }
    
    public boolean isAdmin() {
        return verifiedAdmin;
    }
    
    @Override
    public String toString() {
        return String.format("ORGANIZATION< %s | LIBRARIES%s>", name, libraries);
    }

    public void setContact(ContactDetails contact) {
        this.contact = contact;
        contact.setOrganization(this);
    }
    
}
