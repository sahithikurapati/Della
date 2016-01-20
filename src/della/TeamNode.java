/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package della;

import java.util.ArrayList;

/**
 *
 * @author anuroop
 */
public class TeamNode {
    //represnets the name of the team
    public String name;
    //represents the set of the members that were in the particular team.
    public ArrayList<String> members;
    /**
     * a constructor to initialize the team with given name
     * @param mname 
     */
    public TeamNode(String mname) {
        name = mname;
        members = new ArrayList<>();
    }
    /**
     * a blank constructor to initialize with null values.
     */
    public TeamNode(){
        name = "";
        members= new ArrayList<>();
    }
}
