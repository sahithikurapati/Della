/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBPack;
import della.MemberNode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author anuroop
 */
public class AssociationsDB {
    private String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
  private String MYSQL_URL = "jdbc:mysql://localhost:3306/test";

  private Connection con;
  private Statement st;
  private ResultSet rs;
  
  public AssociationsDB() {
       try {
      Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
      int c =0;/*st.executeUpdate("CREATE TABLE associations ("
              + "mname VARCHAR(30) REFERENCES  members (Name)"
              + "tname VARCHAR(30) REFERENCES  teams (Name)"
              +");");*/
      System.out.println("Table for associations(affiliations) have been created.");
      System.out.println(c+" Row(s) have been affected");
      con.close();

    } catch(ClassNotFoundException ex) {
       System.out.println("ClassNotFoundException:\n"+ex.toString());
       ex.printStackTrace();

    } catch(SQLException ex) {
        System.out.println("SQLException:\n"+ex.toString());
        ex.printStackTrace();
    }
  }
  
  
  
}
