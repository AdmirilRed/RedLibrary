/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import redlibrarian.GUI.textVerification.DateLabelFormatter;
import redlibrarian.RedLibrarian;
import redlibrarian.music.Organization;
import redlibrarian.music.Performance;
import redlibrarian.music.Song;

/**
 *
 * @author admir
 */
public class PerformanceForm extends javax.swing.JDialog {

    private boolean saved;
    private boolean deleted;
    private boolean admin;
    
    private JDatePickerImpl datePicker;
    private Performance performance;
    private Organization organization;
    
    private final ArrayList<Song> songs = new ArrayList<>();
    
    /**
     * Creates new form PerformanceForm
     * @param org
     * @param parent
     * @param modal
     */
    public PerformanceForm(Organization org, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setIconImage(RedLibrarian.icon);
        this.organization = org;
        this.admin = org.isAdmin();
        tabbedSong_pane.add(new LibraryPane(), "Songs");
        loadDatePicker();
        
        deletePerformance_button.setEnabled(false);
    }
    
    public PerformanceForm(Organization org, Performance perf, java.awt.Frame parent, boolean modal) {
        this(org, parent, modal);
        this.performance = perf;
        Calendar date = perf.getDate();
        setDatePicker(date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.YEAR));
        
        title_field.setText(perf.getTitle());
        description_textArea.setText(perf.getDescription());
        
        LibraryPane pane = (LibraryPane) tabbedSong_pane.getComponentAt(0);
        for(Song song:perf.getPlaylist()) {
            pane.addSong(song);
            songs.add(song);
        }
        
        deletePerformance_button.setEnabled(true);
    }
    
    private void loadDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        
        tabbedDate_pane.add(datePicker, "Date");
        
        datePicker.setVisible(true);
    }
    
    private void setDatePicker(int month, int day, int year) {
        datePicker.getModel().setYear(year);
        datePicker.getModel().setMonth(month);
        datePicker.getModel().setDay(day);
        datePicker.getModel().setSelected(true);         
    }
    
    public String getDate() {
        return String.format("%tF",this.getCal());
    }
    
    public Calendar getCal() {
        DateModel model = datePicker.getModel();
        return new GregorianCalendar(model.getYear(),model.getMonth(),model.getDay());
    }
    
    private boolean save() {
        try {
            if(performance != null) {
                performance.setTitle(title_field.getText());
                performance.setDate(this.getCal());
                performance.setDescription(description_textArea.getText());
                performance.setSongs(songs);
            }
            else
                performance = new Performance(title_field.getText(), description_textArea.getText(), this.getCal(), this.songs);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
    
    public boolean wasSaved() {
        return saved;
    }
    
    public boolean wasDeleted() {
        return deleted;
    }
    
    public Performance getPerformance() {
        return performance;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        title_field = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        description_textArea = new javax.swing.JTextArea();
        addSong_button = new javax.swing.JButton();
        tabbedDate_pane = new javax.swing.JTabbedPane();
        save_button = new javax.swing.JButton();
        removeSong_button = new javax.swing.JButton();
        tabbedSong_pane = new javax.swing.JTabbedPane();
        deletePerformance_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Performance Title:");

        jLabel6.setText("Description:");

        description_textArea.setColumns(20);
        description_textArea.setRows(5);
        jScrollPane2.setViewportView(description_textArea);

        addSong_button.setText("Add Song");
        addSong_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSong_buttonActionPerformed(evt);
            }
        });

        tabbedDate_pane.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        save_button.setText("Save");
        save_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_buttonActionPerformed(evt);
            }
        });

        removeSong_button.setText("Remove Song");
        removeSong_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSong_buttonActionPerformed(evt);
            }
        });

        deletePerformance_button.setText("Delete Performance");
        deletePerformance_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePerformance_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedSong_pane)
                    .addComponent(jScrollPane2)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(title_field, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tabbedDate_pane, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addSong_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeSong_button))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(deletePerformance_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(save_button)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(title_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(tabbedDate_pane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addSong_button)
                    .addComponent(removeSong_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabbedSong_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save_button)
                    .addComponent(deletePerformance_button))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void save_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_buttonActionPerformed
        saved = save();
        this.setVisible(false);
    }//GEN-LAST:event_save_buttonActionPerformed

    private void addSong_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSong_buttonActionPerformed
        if(admin) {
            SongSelectForm form = new SongSelectForm(this.organization, (UserInterface)(this.getParent()), true);
            form.setVisible(true);
            if(form.wasSaved() && !songs.contains(form.getSong())) {
                LibraryPane pane = (LibraryPane) tabbedSong_pane.getComponentAt(0);
                pane.addSong(form.getSong());
                songs.add(form.getSong());
            }
        }
        
    }//GEN-LAST:event_addSong_buttonActionPerformed

    private void removeSong_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSong_buttonActionPerformed
        if(admin) {
            LibraryPane pane = (LibraryPane) tabbedSong_pane.getComponentAt(0);
            if(pane.getRowCount()>0) {
                Song target = pane.getSelectedSong();
                if(JOptionPane.showConfirmDialog(null,
                "Are you sure you wish to remove "+target+" from this performance?", "Remove "+target+"?", JOptionPane.YES_NO_OPTION) == 0) {
                    
                    pane.removeSong(target);
                    songs.remove(target);  
                }
                
            }
        }
    }//GEN-LAST:event_removeSong_buttonActionPerformed

    private void deletePerformance_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePerformance_buttonActionPerformed
        if(admin && performance!=null && JOptionPane.showConfirmDialog(null,
                "Are you sure you wish to delete this performance?", "Remove "+performance+"?", JOptionPane.YES_NO_OPTION) == 0) {
            deleted = true;
            this.setVisible(false);
        }
    }//GEN-LAST:event_deletePerformance_buttonActionPerformed

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
            java.util.logging.Logger.getLogger(PerformanceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PerformanceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PerformanceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PerformanceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            PerformanceForm dialog = new PerformanceForm(null, new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSong_button;
    private javax.swing.JButton deletePerformance_button;
    private javax.swing.JTextArea description_textArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton removeSong_button;
    private javax.swing.JButton save_button;
    private javax.swing.JTabbedPane tabbedDate_pane;
    private javax.swing.JTabbedPane tabbedSong_pane;
    private javax.swing.JTextField title_field;
    // End of variables declaration//GEN-END:variables
}
