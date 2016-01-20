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
   
}//end main
	public boolean getInternetStatus(){
		try{
			final String userName = "201585072";
			final String password = "msit123";
			Authenticator.setDefault(
			   new Authenticator() {
			      public PasswordAuthentication getPasswordAuthentication() {
			         return new PasswordAuthentication(
			               userName, password.toCharArray());
			      }
			   }
			);

			System.setProperty("http.proxyUser", userName);
			System.setProperty("http.proxyPassword", password);
			System.setProperty("http.proxyHost", "10.10.10.3");
                        System.setProperty("http.proxyPort", "3128");
			URL url = new URL("http://www.google.co.in");
			System.out.println(url.getHost());
			HttpURLConnection connect = (HttpURLConnection)url.openConnection();
			connect.connect();
			if(connect.getResponseCode() == 200){
                                System.out.println(connect.getResponseCode());
				return true;
			}


		}catch(Exception e){}
		return false;
	}



}

