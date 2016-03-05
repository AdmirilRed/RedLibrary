/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.music;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private Set<Performance> performances;
    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    private Set<Library> libraries;
    
    public Organization(String name, String password) {
        this.libraries = new HashSet<>();
        this.performances = new HashSet<>();
        this.name = name;
        this.salt = Password.getNextSalt();
        this.passwordHash = Password.hash(password.toCharArray(), salt);
    }
    
    public Organization() {
        this.libraries = new HashSet<>();
        this.performances = new HashSet<>();
        this.name = "";
        this.salt = Password.getNextSalt();
        String temp = Password.generateRandomPassword(10);
        this.passwordHash = Password.hash(temp.toCharArray(), salt);
        System.out.println("GENERATED KEY: "+temp);
    }

    public long getId() {
        return id;
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
                return performances.remove(perf);
        return false;        
    }
    
    public Set<Performance> getPerformances() {
        return performances;
    }
    
    public boolean addLibrary(Library item) {
        return libraries.add(item);
    }
    
    public boolean removeLibray(Library target) {
        return libraries.remove(target);
    }
    
    public boolean removeLibrary(long targetId) {
        for(Library lib:libraries)
            if(lib.getId()==targetId)
                return libraries.remove(lib);
        return false;        
    }
    
    public Set<Library> getLibraries() {
        return libraries;
    }
    
    public boolean verifyPassword(String password) {
        return Password.isExpectedPassword(password.toCharArray(), salt, passwordHash);
    }
    
    @Override
    public String toString() {
        return String.format("ORGANIZATION< %s | LIBRARIES%s>", name, libraries);
    }
}
