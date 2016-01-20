/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBPack;
import della.TeamNode;
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
public class TeamsDB {
  private String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
  private String MYSQL_URL = "jdbc:mysql://localhost:3306/test";

  private Connection con;
  private Statement st;
  private ResultSet rs;
  
  public TeamsDB() {
       try {
      Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
      int c =0;/*st.executeUpdate("CREATE TABLE teams ("
              + "Name VARCHAR(30)"
              +");");*/
      System.out.println("Table have been created.");
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
  /**
   * create sql statement to retrieve all teams 
   * @return 
   */
  public ArrayList<String> getTeams(){
      ArrayList<String> temp = new ArrayList<String>();
      try {   
         Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement(); 
        String query = "SELECT * from teams order by name";
       ResultSet rslt = st.executeQuery(query);
       while(rslt.next()) {
           temp.add(rslt.getNString(1));
       }
       System.out.println("in getmembers");
      } catch(Exception e) {e.printStackTrace();}      
      return temp;
  }
  
 public void addTeam(String name) {
      try {
          Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
         String query = "insert into teams(Name) values("+
                        "\""+name+"\");"; 
         st.executeUpdate(query);
         System.out.println("members added successfully");
      }catch(Exception e) {e.printStackTrace();}
  }
 public boolean isPresent(String name){
      
      try {   
         Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement(); 
        String query = "SELECT * from teams where name="+"'"+name+"'"+";";
       ResultSet rslt = st.executeQuery(query);
       if(rslt.next()) return true;
       else return false;
      
      } catch(Exception e) {e.printStackTrace();}
      return false;
  }
 public void removeTeam(String dname) {
      try{
          
          String remove = "delete from teams where name ="+
                            "\""+dname+"\";"; 
          st.execute(remove);remove = "delete from associations where tname ="+
                            "\""+dname+"\";"; 
          st.execute(remove);
          
          System.out.println("removed successfully");
      }catch(Exception e) {e.printStackTrace();}
  }
 
 public ArrayList<String> getAvailMembers(String name){
      ArrayList<String> tem = new ArrayList<String>();
      try{
       Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
       String query = " select * from members" +
        "    where name" +
        "    not in" +
        "    (select mname from associations " +
        "where tname="+ 
                       "'"+name+"')" +";";
       System.out.println(query);
        ResultSet rslt = st.executeQuery(query);
        while(rslt.next()) {
           tem.add(rslt.getNString(1));
        }
       }catch (Exception e) {e.printStackTrace();}
       return tem;  
  }
  
  public ArrayList<String> getCurMembers(String name){
      ArrayList<String> tem = new ArrayList<String>();
      try{
       Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
       String query = "select mname from associations " +
        "where tname="+ 
                       "'"+name+"'" +";";
       System.out.println(query);
        ResultSet rslt = st.executeQuery(query);
        while(rslt.next()) {
           tem.add(rslt.getNString(1));
        }
       }catch (Exception e) {e.printStackTrace();}
       return tem;  
  }
}
