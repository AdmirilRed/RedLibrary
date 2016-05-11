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
import redlibrarian.music.Song;

/**
 *
 * @author admir
 */
public class TabbedLibraryPane extends JTabbedPane {
    
    private UserInterface updateTarget;
    private ArrayList<Library> tabs = new ArrayList<>();
    private Organization organization;
    
    public TabbedLibraryPane(Organization org, UserInterface target) {
        super();
        this.organization = org;
        this.updateTarget = target; 
        for (Library lib : org.getLibraries()) {
            LibraryPane pane = new LibraryPane(lib, updateTarget);
            this.addLibrary(lib, pane);
        }       
    }
    
    public TabbedLibraryPane(List<Library> libraries, UserInterface target) {
        super();
        this.updateTarget = target;
        for (Library lib : libraries) {
            LibraryPane pane = new LibraryPane(lib, updateTarget);
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
        this.setTitleAt(this.getIndex(lib), lib.getName());
        ((LibraryPane) this.getComponent(this.getIndex(lib))).removeAll();
        ((LibraryPane) this.getComponent(this.getIndex(lib))).addSongs(lib);
    }
    
    public void selectSong(Song song) {
        this.setSelectedIndex(this.getIndex(song.getLibrary()));
        LibraryPane pane = (LibraryPane) this.getSelectedComponent();
        pane.selectSong(song);
    }
    
    public void selectFirstSong() {
        LibraryPane pane = (LibraryPane) this.getSelectedComponent();
        if(pane.getRowCount() > 0)
            pane.selectSong(0);
    }

    public Library getCurrentLibrary() {
        return this.getLibrary(this.getSelectedIndex());
    }
    
    public void newTabSelected() {
        LibraryPane pane = (LibraryPane) this.getSelectedComponent();
        if(pane != null && pane.getRowCount() > 0)
            this.selectSong(pane.getSelectedSong());
    }
    
}
