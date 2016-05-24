/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import redlibrarian.RedLibrarian;

/**
 *
 * @author admir
 */
public class Update {
    
    private final String clientVersion = RedLibrarian.getVersion();
    private final String currentVersion;
    
    private final String rootPath;
    
    
    public Update(String path) throws MalformedURLException, IOException {
        this.rootPath = path;
        this.currentVersion = requestCurrentVersion();
        
        if(isOutdated() && JOptionPane.showConfirmDialog(null,
                "You are running an outdated version, would you like to update now?", "Update detected ("+currentVersion+")", JOptionPane.YES_NO_OPTION) == 0) {
            String[] run = {"java","-jar","updater/Update.jar",rootPath+"/RedLibrarian("+currentVersion+").zip"};
            try {
                Runtime.getRuntime().exec(run);
            } catch (Exception ex) {
                Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }
        
        
    }
    
    private String requestCurrentVersion() throws MalformedURLException, IOException {
        String path = rootPath+"/Version+Control.txt";
        URL url = new URL(path);
        System.out.println("Checking current version from "+path);
        
        InputStream txt = url.openStream();
        
        int c = 0;
        StringBuilder buffer = new StringBuilder("");
        
        while(c != -1) {
            c = txt.read();
            buffer.append((char) c);
        }
        String result = buffer.toString().substring(0,buffer.length()-1);
              
        System.out.println("Current Version - "+result);
        return result;   
    }
    
    
    
    private boolean isOutdated() {
        double client = Double.parseDouble(clientVersion.substring(1));
        double current = Double.parseDouble(currentVersion.substring(1));
        
        return current > client;
    }

    
    
    
    
}
