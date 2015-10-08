package RedLibrarian;

import java.sql.SQLException;

/**
 * @(#)RedLibrarian.java
 *
 * RedLibrarian application
 *
 * @author 
 * @version 1.00 2015/9/9
 */

public class RedLibrarian {
    
    public static SQL_JDBC connection;
    
    public static void main(String[] args) throws InterruptedException, SQLException {
        login();
        UserInterface GUI = new UserInterface();
        GUI.setVisible(true);
    }
    
    public static void login() throws InterruptedException{
       LoginPrompt databaseLogin = new LoginPrompt();
        
        while(!databaseLogin.loggedIn)
            Thread.sleep(100);
    }    
}
