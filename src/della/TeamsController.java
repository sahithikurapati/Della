
package della;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package dellaactionitems;

import DBPack.AssociationsDB;
import DBPack.MemberDB;
import DBPack.TeamsDB;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;

/**
 *
 * @author anuroop
 */
public class TeamsController implements Initializable,ControlledScreen{
    ScreenController myController;
    public  String screen1ID = "main";
    public  String screen1File = "console.fxml";
    public  String screen2ID = "screen2";
    public  String screen2File = "ActionItems.fxml";
    public  String screen3ID = "screen3";
    public  String screen3File = "Members.fxml";
    public  String screen4ID = "screen4";
    public  String screen4File = "Teams.fxml";
    
    //--------------------------
    TeamsDB object;
    MemberDB mObject;
    String tName = "";
    String mName = "";
    String errorHead = "";
    String errorCause = "";
    String errorHandling = "";
    public ArrayList<String> listItems = new ArrayList<>();
    public ArrayList<String> teamsList = new ArrayList<>();
    public ArrayList<String> associatnList = new ArrayList<>();
    boolean connectivity = false;
    //--------------------------
    @FXML
    private ListView<String> allTeams;
    @FXML
    private ListView<String>currMem;
    @FXML
    private ListView<String>availMem;
    @FXML
    private TextField teamName;
    @FXML
    private Label team;
    @FXML
    private Label team2;
    @FXML
    private Label onof;
    //--------------------------
    @FXML
   
     public void setScreenParent(ScreenController screenParent){
        myController = screenParent;
    }
    //--------------------------
    @FXML
     private void goToActionItems(ActionEvent event){
       myController.setScreen(screen2ID);
    }
   @FXML
    private void goToConsole(ActionEvent event){
       myController.setScreen(screen1ID);
    }
    @FXML
    private void goToMembers(ActionEvent event){
        try {//ScreenController mainContainer = new ScreenController();
            DellaActionItems.mainContainer.loadScreen(screen3ID, screen3File);
        }catch(Exception e){}
       myController.setScreen(screen3ID);
    }
    @FXML
    private void addTeam(ActionEvent event){
        if(!connectivity){
            new AlertMessage("presently unavailable","offline mode running","");
            return;
        }
        tName = teamName.getText();
        if(tName == null || tName == "") {
            new AlertMessage("adding failed","enter a valid team name","");
            return;
        }
        else if(object.isPresent(tName)){
             new AlertMessage("adding failed","choose new team name","");
            return;
        }
        object.addTeam(tName);
        updateList();
    }
    @FXML
    private void removeTeam(ActionEvent event) {
        if(!connectivity){
            new AlertMessage("presently unavailable","offline mode running","");
            return;
        }
        if(tName == null){
            new AlertMessage("deleting failed","choose team to delete","");
            return;
        }
        object.removeTeam(tName);
        updateList();
        tName = null;
        team.setText("");
        team2.setText("");
    }
    
    @FXML 
    private void selectedTeam(MouseEvent event) {
        tName = allTeams.getSelectionModel().getSelectedItem();
        team.setText(tName);
        team2.setText(tName);
        updateCurMembers();
        updateAvailMembers();
    }
    @FXML
    private void selectedMember(MouseEvent event) {
        mName = availMem.getSelectionModel().getSelectedItem();
    }
    @FXML
    private void selectedMemToDelete(MouseEvent event) {
        mName = currMem.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void addAssociation(ActionEvent event){
        if(!connectivity){
            new AlertMessage("presently unavailable","offline mode running","");
            return;
        }
        tName = team.getText();
        mObject.addAssociation(mName, tName);
        updateCurMembers();
        updateAvailMembers();
    }
    @FXML
    private void removeAssociation(ActionEvent event){
        if(!connectivity){
            new AlertMessage("presently unavailable","offline mode running","");
            return;
        }
        if(mName == "" || tName == "" ) {
            new AlertMessage("select member/team","","please select a member\nand team for a \nsuccessfull remove");
            return;
        }
        mObject.removeAssociation(mName,tName);
        updateCurMembers();
        updateAvailMembers();
        mName = "";
    }
    private void updateList(){
        listItems = object.getTeams();
        ObservableList itemsData = FXCollections.observableArrayList();
        for(String strng : listItems) {
            itemsData.add(strng);
        }
        ObservableList<String> teamsList = FXCollections.observableArrayList(itemsData);
        allTeams.setItems(teamsList);
    }
    private void updateCurMembers(){
        String memname = team.getText();
       System.out.println("++++++++++++++++++++++++"+memname);
       teamsList = object.getCurMembers(memname);
       ObservableList teamListData = FXCollections.observableArrayList();
        for( String team : teamsList) {
            teamListData.add(team);
            System.out.println(team);
        }
         ObservableList<String> teamItems = FXCollections.observableArrayList(teamListData);
         currMem.setItems(teamItems);
    }
    private void updateAvailMembers() {
        String memname = team.getText();
       System.out.println("++++++++++++++++++++++++"+memname);
       teamsList = object.getAvailMembers(memname);
       ObservableList teamListData = FXCollections.observableArrayList();
        for( String team : teamsList) {
            teamListData.add(team);
            System.out.println(team);
        }
         ObservableList<String> teamItems = FXCollections.observableArrayList(teamListData);
         availMem.setItems(teamItems);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("tttttttttttttttttttttttttttttttt");
        ScheduledExecutorService executor =
        Executors.newSingleThreadScheduledExecutor();
        Runnable periodicTask = new Runnable() {
        public void run() {
            // Invoke method(s) to do the work
           connectivity = new DBPack.MainBase().getInternetStatus();
           if(connectivity){
            onof.setText("online");
        }
        else onof.setText("offline");
        }
        };
        executor.scheduleAtFixedRate(periodicTask, 0, 3, TimeUnit.SECONDS);
        object = new TeamsDB();
        AssociationsDB aObject = new AssociationsDB();
        updateList();
        tName = null;
        mObject = new MemberDB();
    }    
    
}
