/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import redlibrarian.music.Library;
import redlibrarian.music.Song;

/**
 *
 * @author admir
 */
public class LibraryPane extends javax.swing.JPanel {

    /**
     * Creates new form LibraryPane
     */
    public LibraryPane() {
        initComponents();
    }

    public LibraryPane(Library lib) {
        
        this();
        
        LibraryTableModel model = (LibraryTableModel) table.getModel();
        
        for(Song song : lib.getContents())
            model.addRow(song);
    }
    
    public void addSongs(Library lib) {
        LibraryTableModel model = (LibraryTableModel) table.getModel();
        for(Song s:lib.getContents())
            model.addRow(s);
    }
    
    public void addSongs(List<Song> songs) {
        LibraryTableModel model = (LibraryTableModel) table.getModel();
        for(Song s:songs)
            model.addRow(s);
    }
    
    public void updateSong(Song target, Song item) {
        LibraryTableModel model = (LibraryTableModel) table.getModel();
        model.updateRow(model.getSongRow(target), item);
        model.selectRow(model.getSongRow(item));
        this.superUpdate(item);
    }
    
    public void removeSong(Song target) {
        LibraryTableModel model = (LibraryTableModel) table.getModel();
        
        int row = model.getSongRow(target);
        model.removeRow(row);
        
        if(row == model.getRowCount())
            row-=1;
        
        model.renderRowColors();
        if(model.getRowCount() > 0)
            this.superUpdate(model.selectRow(row, true));
    }
    
    @Override
    public void removeAll() {
        LibraryTableModel model = (LibraryTableModel) table.getModel();

        while(model.getRowCount()>0)
            model.removeRow(0);
    }
    
    private void superUpdate(Song song) {
        try{
            Object superParent = this.getParent().getParent().getParent().getParent().getParent().getParent();
            if(superParent.getClass().toString().equals(UserInterface.class.toString())) {
                UserInterface userInterface = (UserInterface) superParent;
                userInterface.updateSelection(song);
            }
        }    
        catch(Exception ex) {
            
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        table.setModel(new LibraryTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Title", "Composer"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                LibraryTableModel model = (LibraryTableModel) table.getModel();
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(model.getRowColor(row));
                return c;
            }

        };

        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        LibraryTableModel model = (LibraryTableModel) table.getModel();

        for(int x=0; x<model.getColumnCount();x++)
        table.getColumnModel().getColumn(x).setCellRenderer(renderer);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        LibraryTableModel model = (LibraryTableModel) table.getModel();
        Song selectedSong = model.selectRow(table.getSelectedRow());
        
        this.superUpdate(selectedSong);
    }//GEN-LAST:event_tableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    private static class LibraryTableModel extends DefaultTableModel {

        private final List<Song> rows = new ArrayList<>();
        private final List<Color> rowColors = new ArrayList<>();   
        
        private int lastSelectedRow = 0;

        LibraryTableModel(Object [][] obj, String [] str){
            super(obj, str);
        }
        
        private void setRowColor(int row, Color c) {
            if(rowColors.size()>row)
                rowColors.set(row, c);
            else
                rowColors.add(c);
            
            fireTableRowsUpdated(row, row);
        }
        
        private Color getRowColor(int row) {
            if(rowColors.size()>0)
                return rowColors.get(row);
            return null;
        }
        
        private Color determineColor(Song song) {
            if(!song.isAvailable())
                return Color.RED;
            else
                return (this.getSongRow(song)%2==0)?Color.WHITE:Color.LIGHT_GRAY;
                
        }
        
        private Color determineColor(Song song, boolean isSelected) {
            if(!isSelected)
                return determineColor(song);
            if(!song.isAvailable())
                return Color.GRAY;
            return Color.YELLOW;
                
        }
        
        public void renderRowColors() {
            int row = 0;
            for(Song s:rows) {
               this.setRowColor(row++, this.determineColor(s)); 
            }
                
        }
        
        public boolean addRow(Song song) {
            super.addRow(new Object[]{song.getPseudoId(), song.getTitle(), song.getComposer()});
            boolean result = rows.add(song);
            this.setRowColor(this.getRowCount(), this.determineColor(song));
            fireTableRowsUpdated(this.getSongRow(song), this.getSongRow(song));
            return result;
        }
        
        @Override
        public void removeRow(int row) {
            super.removeRow(row);
            rows.remove(row);
            rowColors.remove(row);
            fireTableRowsUpdated(row, row);
        }
        
        public void updateRow(int row, Song song) {
            super.removeRow(row);
            super.insertRow(row, new Object[]{song.getPseudoId(), song.getTitle(), song.getComposer()});
            rows.set(row, song);
            this.setRowColor(row, this.determineColor(song));
            fireTableRowsUpdated(row, row);
        }
        
        public Song getRow(int row) {
            return rows.get(row);
        }
        
        public int getSongRow(Song song) {
            return rows.indexOf(song);
        }

        public Song selectRow(int selectedRow) {
            return selectRow(selectedRow, false);
        }
        
        public Song selectRow(int selectedRow, boolean ignorePrevious) {
            if(!ignorePrevious) {
                Song previousSong = this.getRow(lastSelectedRow);
                this.setRowColor(lastSelectedRow, this.determineColor(previousSong));
            }
            Song selectedSong = this.getRow(selectedRow);
            this.setRowColor(selectedRow, this.determineColor(selectedSong, true));
            lastSelectedRow = selectedRow;
            return selectedSong;
        }
    }
} 
