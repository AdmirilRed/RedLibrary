/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package updatemanager;

import java.awt.Cursor;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipException;
import net.lingala.zip4j.core.ZipFile;

/**
 *
 * @author Joseph Manahan
 */
public class UpdateGUI extends javax.swing.JFrame {

    private final String root = "update/";
    private final File updateFile = new File("update.zip");
    
    /**
     * Creates new form UpdateGUI
     */
    public UpdateGUI() {
        initComponents();
    }
    
    
    protected boolean downloadFile(String link) throws MalformedURLException, IOException {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        output.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        URL url = new URL(link);
        URLConnection conn = url.openConnection();
        try (InputStream stream = conn.getInputStream()) {
            long max = conn.getContentLength();
            progress_bar.setMaximum((int)max);
            output.setText(output.getText()+"Downloding file from "+link+"...\nUpdate Size(compressed): "+max+" Bytes");
            try (BufferedOutputStream fOut = new BufferedOutputStream(new FileOutputStream(updateFile))) {
                byte[] buffer = new byte[32 * 1024];
                int bytesRead;
                int in = 0;
                while ((bytesRead = stream.read(buffer)) != -1) {
                    in += bytesRead;
                    
                    progress_bar.setValue(in);
                    progress_bar.setString(String.format("%d/%d KB (%d%%)",(int)in/1000,(int)max/1000,(int)(in*100/max)));
                    progress_bar.setStringPainted(true);
                    
                    fOut.write(buffer, 0, bytesRead);
                }
                fOut.flush();
            } catch(Exception ex) {
                this.write(ex.toString());
                return false;
            }
        } catch(Exception ex) {
            this.write(ex.toString());
            return false;
        }
        output.setText(output.getText()+"\nDownload Complete!");
        return true;
    }
    
    protected boolean unzip() throws IOException {
        return unzip(updateFile.getAbsolutePath(), root);
    }
    
    protected boolean unzip(String zippedFile, String dest) throws IOException {
        output.setText(output.getText()+"\nExtracting files...");
        try {
            ZipFile zipFile = new ZipFile(zippedFile);
            zipFile.extractAll(dest);
            
        } catch (net.lingala.zip4j.exception.ZipException ex) { 
            Logger.getLogger(UpdateGUI.class.getName()).log(Level.SEVERE, null, ex);
            this.write(ex.toString());
            return false;
        }
        output.setText(output.getText()+"\nFiles extracted!");
        return true;        
    }
    
    protected void copyFiles() throws IOException {
        this.write(new File("").getAbsolutePath().substring(0,new File("").getAbsolutePath().lastIndexOf('/')));
        copyFiles(new File(root),new File("").getAbsolutePath());
    }
    
    protected void copyFiles(File f,String dir) throws IOException {
        File[]files = f.listFiles();
        for(File ff:files)
        {
            if(ff.isDirectory()){
                new File(dir+"/"+ff.getName()).mkdir();
                copyFiles(ff,dir+"/"+ff.getName());
            }
            else
            {
                copy(ff.getAbsolutePath(),dir+"/"+ff.getName());
            }
        }
    }
    
    private void copy(String srFile, String dtFile) throws FileNotFoundException, IOException {
        File f1 = new File(srFile);
        File f2 = new File(dtFile);
          
        OutputStream out;
        try (InputStream in = new FileInputStream(f1)) {
            out = new FileOutputStream(f2);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
        }
        out.close();
    }
    
    protected void cleanup() {
        output.setText(output.getText()+"\nPreforming clean up...");
        File f = new File("update.zip");
        f.delete();
        remove(new File(root));
        new File(root).delete();
    }
    
    private void remove(File f) {
        File[]files = f.listFiles();
        for(File ff:files)
        {
            if(ff.isDirectory())
            {
                remove(ff);
                ff.delete();
            }
            else
            {
                ff.delete();
            }
        }
    }


    
    protected void write(String txt) {
        output.setText(output.getText()+"\n"+txt);
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
        output = new javax.swing.JTextArea();
        progress_bar = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        output.setColumns(20);
        output.setRows(5);
        jScrollPane1.setViewportView(output);

        progress_bar.setString("50%");

        jLabel1.setText("Progress");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addComponent(progress_bar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progress_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(UpdateGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new UpdateGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea output;
    private javax.swing.JProgressBar progress_bar;
    // End of variables declaration//GEN-END:variables
}
