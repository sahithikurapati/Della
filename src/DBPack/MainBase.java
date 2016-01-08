/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBPack;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.sql.*;

/**
 *
 * @author anuroop
 */
public class MainBase {
    // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "password";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 4: Execute a query
      System.out.println("Creating database...");
      stmt = conn.createStatement();
      
      String sql = "CREATE DATABASE STUDENTS";
      stmt.executeUpdate(sql);
      System.out.println("Database created successfully...");
   }catch(SQLException | ClassNotFoundException se){
   }
   System.out.println("Goodbye!");
}//end main
	public boolean getInternetStatus(){
		try{
			final String authUser = "201585103";
			final String authPassword = "msit123";
			Authenticator.setDefault(
			   new Authenticator() {
			      public PasswordAuthentication getPasswordAuthentication() {
			         return new PasswordAuthentication(
			               authUser, authPassword.toCharArray());
			      }
			   }
			);

			System.setProperty("http.proxyUser", authUser);
			System.setProperty("http.proxyPassword", authPassword);
			System.setProperty("http.proxyHost", "10.10.10.3");
            System.setProperty("http.proxyPort", "3128");
			URL url = new URL("http://www.google.co.in");
			System.out.println(url.getHost());
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.connect();
			if(con.getResponseCode() == 200){
				return true;
			}


		}catch(Exception e){}
		return false;
	}



}

