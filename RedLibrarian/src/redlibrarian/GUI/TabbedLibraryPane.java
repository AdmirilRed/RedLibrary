/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI;

import java.util.ArrayList;
import javax.swing.JTabbedPane;
import redlibrarian.music.Library;

/**
 *
 * @author admir
 */
public class TabbedLibraryPane extends JTabbedPane {
    
    ArrayList<Library> tabs = new ArrayList<>();
    
    public TabbedLibraryPane() {
        super();
        System.out.println("BEING CALLED!");
    }
    
    public void addLibrary(Library lib, LibraryPane pane) {
        super.addTab(lib.getName(), pane);
        tabs.add(lib);
    }
    
    public Library getLibrary(int index) {
        return tabs.get(index);
    }
    
}
