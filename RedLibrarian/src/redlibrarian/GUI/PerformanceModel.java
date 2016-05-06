/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import redlibrarian.music.Song;

/**
 *
 * @author admir
 */
public class PerformanceModel extends DefaultTreeModel {
    
    
    
    public PerformanceModel(TreeNode node) {
        super(node);
    }
    
    public void add(Song song) {
        
        
    }
    
}
