/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI;

import javax.swing.tree.DefaultMutableTreeNode;
import redlibrarian.music.Performance;

/**
 *
 * @author admir
 */
public class PerformanceTreeNode extends DefaultMutableTreeNode {

    Performance performance;
    
    public PerformanceTreeNode(Performance perf) {
        super(perf);
        this.performance = perf;
    }
    
    public Performance getPerformance() {
        return performance;
    } 
    
    
}
