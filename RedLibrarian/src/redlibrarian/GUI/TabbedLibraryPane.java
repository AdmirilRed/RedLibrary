/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;
import redlibrarian.music.Library;
import redlibrarian.music.Organization;

/**
 *
 * @author admir
 */
public class TabbedLibraryPane extends JTabbedPane {
    
    ArrayList<Library> tabs = new ArrayList<>();
    Organization organization;
    
    public TabbedLibraryPane(Organization org) {
        super();
        this.organization = org;
        for (Library lib : org.getLibraries()) {
            LibraryPane pane = new LibraryPane(lib);
            this.addLibrary(lib, pane);
        }
        
    }
    
    public TabbedLibraryPane(List<Library> libraries) {
        super();
        for (Library lib : libraries) {
            LibraryPane pane = new LibraryPane(lib);
            this.addLibrary(lib, pane);
        }
    }
    
    public void addLibrary(Library lib, LibraryPane pane) {
        super.addTab(lib.getName(), pane);
        tabs.add(lib);
    }
    
    public Library getLibrary(int index) {
        return tabs.get(index);
    }
    
    public int getIndex(Library lib) {
        return tabs.indexOf(lib);
    }
    
    public void refresh(Library lib) {
        System.out.println("refresh()");
        ((LibraryPane) this.getComponent(this.getIndex(lib))).removeAll();
        ((LibraryPane) this.getComponent(this.getIndex(lib))).addSongs(lib);
    }
    
}
