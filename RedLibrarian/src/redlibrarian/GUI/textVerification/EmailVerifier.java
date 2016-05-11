/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI.textVerification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author admir
 */
public class EmailVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        
        String text = ((JTextField) input).getText().toLowerCase();
        Pattern pattern = Pattern.compile("[\\w]+@[\\w]+.(com|org|net|gov|edu)");
        Matcher matcher = pattern.matcher(text);
        
        return matcher.find();
    }
    
}
