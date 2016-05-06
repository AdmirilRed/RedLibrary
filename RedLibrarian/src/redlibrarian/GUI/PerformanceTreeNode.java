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
public class PerformanceTreeNode extends DefaultMutableTreeNode implements Comparable {

    Performance performance;
    
    public PerformanceTreeNode(Performance perf) {
        super(perf);
        this.performance = perf;
    }
    
    public Performance getPerformance() {
        return performance;
    } 

    @Override
    public int compareTo(Object o) {
        if(o.getClass().toString().equals(this.getClass().toString())) {
            PerformanceTreeNode otherNode = (PerformanceTreeNode) o;
            return otherNode.getPerformance().getDate().compareTo(this.performance.getDate());
        }
            
        return this.hashCode()>o.hashCode()?1:this.hashCode()==o.hashCode()?0:-1;
    }
   
    
    
    
}
