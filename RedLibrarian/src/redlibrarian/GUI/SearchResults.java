/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI;

import java.util.ArrayList;
import redlibrarian.music.Library;
import redlibrarian.music.Organization;
import redlibrarian.music.Song;

/**
 *
 * @author admir
 */
public class SearchResults extends javax.swing.JFrame {

    private final Organization organization; 
    private final String query;
    LibraryPane songResults;
    TabbedLibraryPane libraryResults;

    /**
     * Creates new form SearchResults
     * @param query
     * @param org
     */
    public SearchResults(String query, Organization org) {
        initComponents();
        this.organization = org;
        this.query = query.toLowerCase();
    }
    
    public void load() {
        
        this.setTitle("Search Results - ("+query+")");
        ArrayList<Song> songResultsList = this.getSongResults();
        ArrayList<Library> libraryResultsList = this.getLibraryResults();
        
        songResults = new LibraryPane();
        songResults.addSongs(songResultsList);
        
        libraryResults = new TabbedLibraryPane(libraryResultsList);        
        
        tabbedRoot_pane.add("Songs", songResults);
        tabbedRoot_pane.add("Libraries", libraryResults);
    }
    
    private ArrayList<Library> getLibraryResults() {
        
        ArrayList<Library> results = new ArrayList<>();
        for(Library lib:organization.getLibraries()) {
            
            if(this.applyQuery(lib))
                results.add(lib);
        }
        return results;
    }
    
    private ArrayList<Song> getSongResults() {
        
        ArrayList<Song> results = new ArrayList<>();
        for(Library lib:organization.getLibraries()) {
            for(Song song:lib.getContents()) {
                
                if(this.applyQuery(song))
                    results.add(song);
            }
        }
        return results;
    }
    
    private boolean applyQuery(Song song) {
        
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedRoot_pane = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search Results");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedRoot_pane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1075, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedRoot_pane, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SearchResults("", null).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabbedRoot_pane;
    // End of variables declaration//GEN-END:variables

    
}