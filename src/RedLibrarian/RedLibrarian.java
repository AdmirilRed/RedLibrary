package RedLibrarian;

import java.sql.SQLException;

/**
 *
 * Main class of the RedLibrarian package
 *
 * @author Joseph Manahan
 * @version 1.00 2015/9/9
 */

public class RedLibrarian {
    
    public static SQL_JDBC connection;
    
    /**
     * Main method of the application. Creates new LoginPrompt and UserInterface.
     * @param args External arguments.
     * @throws InterruptedException Thrown when interrupted while thread is sleeping.
     * @throws SQLException Thrown when the method cannot contact the server.
     */
    public static void main(String[] args) throws InterruptedException, SQLException {
        login();
        UserInterface GUI = new UserInterface();
        GUI.setVisible(true);
    }
    
    /**
     * Creates new LoginPrompt and waits until connected to the server
     * @throws InterruptedException Thrown when interrupted while thread is sleeping.
     */
    public static void login() throws InterruptedException{
       LoginPrompt databaseLogin = new LoginPrompt();
        
        while(!databaseLogin.loggedIn)
            Thread.sleep(100);
    }    
}
