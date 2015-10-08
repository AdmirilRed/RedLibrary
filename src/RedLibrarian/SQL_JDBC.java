package RedLibrarian;

import java.util.*;
import java.io.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL_JDBC {
    
    private static String user = "";
    private static String pass = "";
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
  public static void printQuery(ResultSet rs) throws SQLException {
      while(rs.next())
          System.out.println(rs.getString("PID")+" | "+rs.getString("UID")+" | "+rs.getString("TITLE")+" | "+rs.getString("COMPOSER")+" | "+rs.getString("LIBRARY"));
  }

  public SQL_JDBC(String username, String password) {
        login(username,password);
  }
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
  public final void login(String us, String pw) {
      user = us;
      pass = pw;
  }
  public static String ask(String prompt) throws IOException {
      Scanner kb = new Scanner(System.in);
      System.out.print(prompt+": ");
      return kb.nextLine();
  }
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