/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.ArrayList;
import java.util.Collections;
import redlibrarian.music.Library;
import redlibrarian.music.Organization;
import redlibrarian.music.Song;

/**
 *
 * @author admir
 */
public class Search {
    
    Organization organization;
    String query;
    
    public Search(String query, Organization org) {
        this.query = query;
        this.organization = org;
    }
    
    
    private ArrayList<Library> getLibraryResults() {
        
        ArrayList<Library> results = new ArrayList<>();
        for(Library lib:organization.getLibraries()) {
            
            if(this.applyQuery(lib))
                results.add(lib);
        }
        return results;
    }
    
    public ArrayList<Song> getSongResults() {
        
        ArrayList<Song> results = new ArrayList<>();
        for(Library lib:organization.getLibraries()) {
            for(Song song:lib.getContents()) {
                
                if(this.applyQuery(song))
                    results.add(song);
            }
        }
        Collections.sort(results);
        return results;
    }
    
    private boolean applyQuery(Song song) {
        
        if(query.equals("*"))
            return true;
        
        if(song.getTitle().toLowerCase().contains(query) || song.getComposer().toLowerCase().contains(query))
            return true;
        
        if(song.getDescription() != null && song.getDescription().toLowerCase().contains(query))
            return true;
        
        try {
            if(song.getPseudoId() == Integer.parseInt(query))
                return true;
        }
        catch(Exception ex) {

        }
        
        return false;
        
    }
    
    private boolean applyQuery(Library lib) {
        
        if(lib.getName().toLowerCase().contains(query))
            return true;
        
        return lib.getDescription() != null && lib.getDescription().toLowerCase().contains(query);
    }
    
}
