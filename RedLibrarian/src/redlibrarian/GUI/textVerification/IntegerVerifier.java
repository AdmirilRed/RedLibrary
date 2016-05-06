/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI.textVerification;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author admir
 */
public class IntegerVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        try{
            Integer test = Integer.parseInt(((JTextField) input).getText());
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
    
    
}
