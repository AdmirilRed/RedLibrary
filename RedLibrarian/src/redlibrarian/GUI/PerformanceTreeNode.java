/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI;

import javax.swing.tree.DefaultMutableTreeNode;
import redlibrarian.music.Song;

/**
 *
 * @author admir
 */
public class PerformanceTreeNode extends DefaultMutableTreeNode {

    Song song;
    
    public PerformanceTreeNode(Song song) {
        super(song);
        this.song = song;
    }
    
    public Song getSong() {
        return song;
    } 
    
    
}
