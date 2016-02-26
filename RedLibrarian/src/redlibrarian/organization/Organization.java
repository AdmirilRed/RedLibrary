/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.organization;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import redlibrarian.music.Library;
import redlibrarian.music.Performance;

/**
 *
 * @author admir
 */
@Entity
public class Organization implements Serializable {
    
    @Id
    private long id;
    
    @ManyToMany
    private Set<Performance> performances = new TreeSet<>();
    @ManyToMany
    private Set<Library> libraries = new TreeSet<>();
    @ManyToMany
    private Set<OrgUser> users = new TreeSet<>();
    @ManyToMany
    private Set<OrgUser> admins = new TreeSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    
    
}
