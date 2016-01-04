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
public class MemberNode {
    //represents the name of the member
    public String name;
    //represents the set of teams presently he is in
    public ArrayList<TeamNode> teams;
    
    /**
     * constructor to initialize the name of the member
     * @param mname 
     */
    public MemberNode(String mname) {
        name = mname;
        teams = new ArrayList<>();
    }
    /**
     * a blank constructor to initialize to null values.
     */
    public MemberNode() {
        name = "";
        teams = new ArrayList<>();
    }
}
