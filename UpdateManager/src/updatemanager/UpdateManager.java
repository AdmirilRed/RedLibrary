/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package updatemanager;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Joseph Manahan
 */
public class UpdateManager {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        String link = args[0];
        
        UpdateGUI updater = new UpdateGUI();
        updater.setVisible(true);
        try {
            if(updater.downloadFile(link)) {
                if(updater.unzip()) {
                    updater.copyFiles();
                }
            }
            
        }
        catch(Exception ex) {
            updater.write(ex.toString()+"\n"+ex.getMessage());
        }
        
    }
}
