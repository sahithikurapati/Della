/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dellaactionitems;

import java.util.Date;

/**
 *
 * @author anuroop
 */

public class ActionItemNode {
    public String name;
    public String description;
    public String resolution;
   public  String status;
    public String team;
    public String member;
    public String due;
    public String creation;
    
    @Override
    public String toString() {
       return name+","+description+","+resolution+","+status+","+team+","+member; 
    }
}
