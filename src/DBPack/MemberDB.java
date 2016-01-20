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
public class MemberDB {
  private String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
  private String MYSQL_URL = "jdbc:mysql://localhost:3306/test";

  private Connection con;
  private Statement st;
  private ResultSet rs;
  
  public MemberDB() {
       try {
      Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
      int c = 0;/*st.executeUpdate("CREATE TABLE members ("
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
   * create sql statement to retrieve all members 
   * @return 
   */
  public ArrayList<String> getMembers(){
      ArrayList<String> temp = new ArrayList<String>();
      try {   
         Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement(); 
        String query = "SELECT * from members order by name";
       ResultSet rslt = st.executeQuery(query);
       while(rslt.next()) {
           temp.add(rslt.getNString(1));
       }
       System.out.println("in getmembers");
      } catch(Exception e) {e.printStackTrace();}      
      return temp;
  }
  /**
   * adds a member to table
   * @param name 
   */
  public void addMember(String name) {
      try {
          Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
         String query = "insert into members(Name) values("+
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
        String query = "SELECT * from members where name="+"'"+name+"'"+";";
       ResultSet rslt = st.executeQuery(query);
       if(rslt.next()) return true;
       else return false;
      
      } catch(Exception e) {e.printStackTrace();}
      return false;
  }
 
  public void removeMember(String dname) {
      try{
          
          String remove = "delete from members where name ="+
                            "\""+dname+"\";"; 
          st.execute(remove);
          remove = "delete from associations where mname ="+
                            "\""+dname+"\";"; 
          st.execute(remove);
          System.out.println("removed successfully");
      }catch(Exception e) {e.printStackTrace();}
  }
  
  public ArrayList<String> getAvailTeams(String name){
      ArrayList<String> tem = new ArrayList<String>();
      try{
       Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
       String query = " select * from teams" +
        "    where name" +
        "    not in" +
        "    (select tname from associations " +
        "where mname="+ 
                       "'"+name+"')" +";";
       System.out.println(query);
        ResultSet rslt = st.executeQuery(query);
        while(rslt.next()) {
           tem.add(rslt.getNString(1));
        }
       }catch (Exception e) {e.printStackTrace();}
       return tem;  
  }
  
  public ArrayList<String> getCurTeams(String name){
      ArrayList<String> tem = new ArrayList<String>();
      try{
       Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
       String query = "select tname from associations " +
        "where mname="+ 
                       "'"+name+"'" +";";
       System.out.println(query);
        ResultSet rslt = st.executeQuery(query);
        while(rslt.next()) {
           tem.add(rslt.getNString(1));
        }
       }catch (Exception e) {e.printStackTrace();}
       return tem;  
  }
  
  public void addAssociation(String mname,String tname) {
      try {
          System.out.println("in association");
          Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
         String query = "insert into associations(mname,tname) values("+
                        "\""+mname+"\" "+ ",\""+tname+"\""+");"; 
         st.executeUpdate(query);
         System.out.println("association added successfully");
                 
    }catch(Exception e) {e.printStackTrace();}
  }
  
   public ArrayList<String> getAssociation(String member) {
      ArrayList<String> temp = new ArrayList<String>();
      try {   
          Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
        String query = "SELECT tname from associations where mname = "+ 
                       "\""+member+"\" " +";";
       ResultSet rslt = st.executeQuery(query);
       while(rslt.next()) {
           temp.add(rslt.getNString(1));
       }
       System.out.println("in getassociations");
      } catch(Exception e) {e.printStackTrace();}      
      return temp;
  }
   public void removeAssociation(String mname,String tname){
        try {
          System.out.println("in association");
          Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
         String query = "delete from associations where tname='"+tname+"'"+"and mname='"+mname+"';"; 
         st.executeUpdate(query);
         System.out.println("association removed successfully");
                 
    }catch(Exception e) {e.printStackTrace();}
   }
}
