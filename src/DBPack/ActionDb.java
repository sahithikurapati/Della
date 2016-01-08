/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import della.ActionItemNode;
/**
 *
 * @author anuroop
 */
public class ActionDb {
    private String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
  private String MYSQL_URL = "jdbc:mysql://localhost:3306/test";

  private Connection con;
  private Statement st;
  private ResultSet rs;

  public ActionDb() {

    try {
      Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,"root","password");
      System.out.println("Connected to the database....");
      st = con.createStatement();
      int c =st.executeUpdate("CREATE TABLE ActionItems IF NOT EXISTS ("
              + "Name VARCHAR(30)"
              + ",Description VARCHAR(50)"
              + ",Resolution VARCHAR(50)"
              + ",Team VARCHAR(10)"
              + ",Member VARCHAR(10),status VARCHAR(10));");
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
  public void addActionItems(ActionItemNode temp)  {
      try {
          System.out.println("adding items..");
          String x = "INSERT INTO ActionItems (Name,Description,Resolution,Team,Member) VALUES (\"?\",\"?\",\"?\",\"?\",\"?\")";
          //int noRows = st.executeUpdate(x);
          int noRows = st.executeUpdate("INSERT INTO ActionItems (Name,Description,Resolution,Team,Member,Status,created,dued) VALUES ("+"\""+temp.name+"\""+","+
                                        "\""+temp.description+"\""+","+"\""+temp.resolution+"\""+","+
                                        "\""+temp.team+"\""+","+
                                        "\""+temp.member+"\""+","+
                                        "\""+temp.status+"\""+","+
                                        "\""+temp.creation+"\""+","+
                                        "\""+temp.due+"\""+");");
          System.out.println("done adding! no of rows effected:"+noRows);
      }catch(Exception e) {e.printStackTrace();}
      
  }
  // select * from ActionItems where name = "one";
  public ActionItemNode getValues(String name) {
      try{
          ActionItemNode temp;
          temp = new ActionItemNode();
          System.out.println("SELECT * FROM ActionItems WHERE Name = "+"\""+name+"\";");
          System.out.println("retrieving data!");
          String sql = "SELECT * FROM ActionItems WHERE Name = "+"\""+name+"\";";
          //st.executeQuery(sql)
          ResultSet rs1 = st.executeQuery(sql);
              System.out.println("done retrieving and storing to collection....");
              //STEP 5: Extract data from result set
              while(rs1.next()){
              //Retrieve by column name
              System.out.println("returning data fetched...");
              
              temp.name = rs1.getNString(1);
              temp.description = rs1.getNString(2);
              temp.resolution = rs1.getNString(3);
              temp.team = rs1.getNString(4);
              temp.member = rs1.getNString(5);
              temp.status = rs1.getNString(6);
              temp.creation = rs1.getNString(7);
              temp.due = rs1.getNString(8);
              System.out.println(temp);
              }
          //STEP 5: Extract data from result set
      return temp;
      }
      catch(Exception e){e.printStackTrace();}
      return null;
  }
  
  public ArrayList<ActionItemNode> getValues() {
      try{
          ActionItemNode temp;
          ArrayList<ActionItemNode> result = new ArrayList<>();
          System.out.println("retrieving data!");
          String sql = "SELECT * FROM ActionItems order by Name";
          
          ResultSet rs = st.executeQuery(sql);
          System.out.println("done retrieving and storing to collection....");
      //STEP 5: Extract data from result set
         while(rs.next()){
         //Retrieve by column name
         System.out.println("waiting for next record..");
         temp = new ActionItemNode();
         temp.name = rs.getNString(1);
         temp.description = rs.getNString(2);
         temp.resolution = rs.getNString(3);
         temp.team = rs.getNString(4);
         temp.member = rs.getNString(5);
         temp.status = rs.getNString(6);
         temp.creation = rs.getNString(7);
         temp.due = rs.getNString(8);
         System.out.println(temp);
         result.add(temp);
      }
      rs.close();
      return result;
      }
      catch(Exception e){e.printStackTrace();}
      
      return null;
  }
  
  public ActionItemNode updateActions(ActionItemNode node,String name) {
       String sql;
       System.out.println("entering update set! and updating action item \""+name+"\"");
       sql = "update actionitems set"+
               " description='"+node.description+
               "',resolution='"+node.resolution+
               "',team='"+node.team+
               "',member='"+node.member+
               "',created='"+node.creation+
               "',dued='"+node.due+
               "',name='"+node.name+"'where name='"+name+"'";
       System.out.println(sql);
       try{st.executeUpdate(sql);}
       catch(Exception ex){ex.printStackTrace();}
      return null;
  }
  public void deleteActionItem(String name){
      try{
          
          String remove = "delete from actionitems where name ="+
                            "\""+name+"\";"; 
          st.execute(remove);
          
          System.out.println("removed successfully");
      }catch(Exception e) {e.printStackTrace();}
  }
  public ArrayList<ActionItemNode> getSorted(String firstDir, String secondDir, String order){
       try{
          ActionItemNode temp;
          ArrayList<ActionItemNode> result = new ArrayList<>();
          System.out.println("retrieving data!");
          
          String sql = "SELECT * FROM ActionItems order by ";
          if(!firstDir.equals("")){
              sql += firstDir;
          }
          if(!secondDir.equals("")){
              if(!firstDir.equals("")) sql += ",";
              sql += " "+secondDir;
          }
          sql += " "+order +";";
          System.out.println(sql);
          ResultSet rs4 = st.executeQuery(sql);
          System.out.println("done retrieving and storing to collection....");
      //STEP 5: Extract data from result set
         while(rs4.next()){
         //Retrieve by column name
         System.out.println("waiting for next record..");
         temp = new ActionItemNode();
         temp.name = rs4.getNString(1);
         temp.description = rs4.getNString(2);
         temp.resolution = rs4.getNString(3);
         temp.team = rs4.getNString(4);
         temp.member = rs4.getNString(5);
         temp.status = rs4.getNString(6);
         temp.creation = rs4.getNString(7);
         temp.due = rs4.getNString(8);
         System.out.println(temp);
         result.add(temp);
      }
      rs4.close();
      return result;
      }
      catch(Exception e){e.printStackTrace();}
      
      return null;
  }
}
