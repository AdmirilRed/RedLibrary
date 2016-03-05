/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian;

import java.util.Calendar;
import redlibrarian.music.Library;
import redlibrarian.music.Organization;
import redlibrarian.music.Performance;
import redlibrarian.music.Song;

/**
 *
 * @author admir
 */
public class OrgTest {
    
    public static void main(String [] args) {
        
        Organization org = new Organization("RFHS Band", "DrRicketts");
        
        Library lib1 = new Library("Marching", "Testing");
        Library lib2 = new Library("Concert", "Testing");
        
        Song song1 = new Song(220, "The Future that Awaited Me", "Animals as Leaders");
        Song song2 = new Song(15, "Mars the Warbringer", "Gustav Holst");
        Song song3 = new Song(15, "Saturn the Bringer of Old Age", "Gustav Holst");
        
        Performance perf1 = new Performance("Spring Concert", "Testing", Calendar.getInstance());
        Performance perf2 = new Performance("Patriotic Concert", "Testing", Calendar.getInstance());
        
        org.addLibrary(lib1);
        org.addLibrary(lib2);
        
        System.out.println(org);
        
        lib1.addSong(song1);
        lib1.addSong(song2);
        lib2.addSong(song2);
        lib2.addSong(song3);
        
        System.out.println(org);
    }
}
