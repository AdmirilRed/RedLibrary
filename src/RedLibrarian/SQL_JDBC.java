package RedLibrarian;

import java.util.*;
import java.io.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * SQL connector class
 * @author Joseph
 */
public class SQL_JDBC {
    
    private static String user = "";
    private static String pass = "";
    /**
     * Indicates the connectivity status of the connector
     */
    public static String status = "unreported";
    
  public static void main(String [] args) throws IOException, SQLException {
      user = ask("USERNAME");
      pass = ask("PASSWORD");
      if(testConnection()) {
        String sql = "";  
        while(!sql.equals("EXIT")) {
            try
            {
               sql = ask("SQL").toUpperCase().trim();
               if(!sql.equals("EXIT"))  
                if(sql.startsWith("SELECT")) {
                     printQuery(executeQuery(sql));
                  }
                  else {
                    executeStatement(sql);
                } 
            }
            catch(Exception e) {
              System.out.println(e);   
            }            
        }    
      }      
  } 
  /**
   * Prints a given ResultSet
   * @param rs The ResultSet to print
   * @throws SQLException Thrown when a connection cannot be made
   */
  public static void printQuery(ResultSet rs) throws SQLException {
      while(rs.next())
          System.out.println(rs.getString("PID")+" | "+rs.getString("UID")+" | "+rs.getString("TITLE")+" | "+rs.getString("COMPOSER")+" | "+rs.getString("LIBRARY"));
  }
  /**
   * Attempts a connection with the given username and password
   * @param username The username to attempt a connection with
   * @param password The password to attempt a connection with
   */
  public SQL_JDBC(String username, String password) {
        login(username,password);
  }
  /**
   * Executes a SQL Query with the given string
   * @param input The string to use as a SQL Query
   * @return Returns the ResultSet from the SQL Query
   */
  public static ResultSet executeQuery(String input) {
     try {
         Class.forName("com.mysql.jdbc.Driver");
         Connection database = DriverManager.getConnection("jdbc:mysql://CommStationFive.net:3306/redlibrarian?user="+user+"&password="+pass);
         Statement stmt = database.createStatement();
         return stmt.executeQuery(input);
     } 
     catch(ClassNotFoundException | SQLException e) {
        System.out.println(e);
     }
         
     return null;
  } 
  /**
   * Executes a SQL Statement
   * @param input The string to use as a SQL Statement 
   * @return Returns true if the execution was successful 
   */
  public static boolean executeStatement(String input) {
      try {
         Class.forName("com.mysql.jdbc.Driver");
         Connection database = DriverManager.getConnection("jdbc:mysql://CommStationFive.net:3306/redlibrarian?user="+user+"&password="+pass);
         Statement stmt = database.createStatement();
         return stmt.execute(input);
     } 
     catch(ClassNotFoundException | SQLException e) {
        System.out.println(e);
        
     }
         
     return false;
  }
  /**
   * Attempts connection to the server and returns the result while changing the connectivity status
   * @return Returns true if the connection is successful 
   */
  public static boolean testConnection() {
      System.out.println("-------- MySQL JDBC Connection Testing ------------");
      
      try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		System.out.println("Where is your MySQL JDBC Driver?");
                status = "driver";
	}
        
        System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;

	try {
		connection = DriverManager.getConnection("jdbc:mysql://CommStationFive.net:3306/redlibrarian?user="+user+"&password="+pass);

	} catch (SQLException e) {
		System.out.println("Connection failed!");
                if(e.toString().contains("Access denied"))
                    status = "credentials";
                else
                    status = "connectivity";
                return false;
	}

	if (connection != null) {
		System.out.println("Connection successful!");
                status = "successful";
                return true;
	} else {
		System.out.println("Failed to make connection!");
                status = "unreported";
                return false;
	}
  } 
  /**
   * Changes the client variables username and password. (Does not affect server username or password)
   * @param us The username to use
   * @param pw The password to use
   */
  public final void login(String us, String pw) {
      user = us;
      pass = pw;
  }
  /**
   * Gives a prompt for user text input using a given string
   * @param prompt The string to use as a prompt (": " is appended)
   * @return Returns the input given by the user
   * @throws IOException
   */
  public static String ask(String prompt) throws IOException {
      Scanner kb = new Scanner(System.in);
      System.out.print(prompt+": ");
      return kb.nextLine();
  }
  /**
   * Returns a String matrix of a given SQL Query
   * @param stmt The SQL String to execute 
   * @return The returned String matrix
   * @throws SQLException Thrown when a connection cannot be made to the server
   */
  public static String [][] getResultSet(String stmt) throws SQLException {
    ResultSet rs = SQL_JDBC.executeQuery(stmt);
    ArrayList<String[]> list = new ArrayList<>();
        
    while(rs.next()) {
        String [] result = {rs.getString("PID"),rs.getString("TITLE"),rs.getString("COMPOSER"),rs.getString("LIBRARY")};
        list.add(result);
    }
      
    String [][] mat = new String [list.size()][4];
    int x = 0;
    for(String [] s:list) {
        mat[x++] = s;
    }
            
    return mat;
  }
  /**
   * Retrieves all items from the database
   * @return The returned string matrix
   * @throws SQLException Thrown when a connection cannot be made to the server 
   */
  public static String [][] getAllMusic() throws SQLException {
    ResultSet rs = SQL_JDBC.executeQuery("SELECT * FROM MUSIC ORDER BY pid");    
    ArrayList<String[]> list = new ArrayList<>();
        
    while(rs.next()) {
        String [] result = {rs.getString("PID"),rs.getString("TITLE"),rs.getString("COMPOSER"),rs.getString("LIBRARY"),rs.getString("UID"),rs.getString("DATE_ADDED")};
        list.add(result);
    }
      
    String [][] mat = new String [list.size()][6];
    int x = 0;
    for(String [] s:list) {
        mat[x++] = s;
    }
            
    return mat;
  }
}