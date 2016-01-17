
package della;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package dellaactionitems;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import DBPack.MemberDB;
import DBPack.AssociationsDB;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
/**
 *
 * @author anuroop
 */
public class MembersController implements Initializable,ControlledScreen{
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
    MemberDB object;
    String mName = "";
    String tName = "";
    String errorHead = "";
    String errorCause = "";
    String errorHandling = "";
    boolean connectivity = false;
    public ArrayList<String> listItems = new ArrayList<>();
     public ArrayList<String> teamsList = new ArrayList<>();
     public ArrayList<String> associatnList = new ArrayList<>();
    //--------------------------
    @FXML
    private ListView<String> allMembers;
    @FXML
    private ListView<String> curteams;
    @FXML
    private ListView<String> availteams;
    @FXML
    private TextField memname;
    @FXML
    private Label name;
    @FXML
    private Label name2;
    @FXML
    private Label onof;
    //--------------------------
   
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
    private void goToTeams(ActionEvent event){try {//ScreenController mainContainer = new ScreenController();
            DellaActionItems.mainContainer.loadScreen(screen4ID, screen4File);
        }catch(Exception e){}
        
       myController.setScreen(screen4ID);
    }
   
    @FXML
    private void addMember(ActionEvent event) {
        if(!connectivity){
            new AlertMessage("presently unavailable","offline mode running","");
            return;
        }
        mName = memname.getText();
        if(mName == null || mName.equals(null) || mName.equals("")){
            new AlertMessage("unable to add member","enter some name","");
            return;
        }
        if(!object.isPresent(mName) && mName != null){
            object.addMember(mName);
            updateList();
        }
        else {
            new AlertMessage("unable to add member","choose new name","");
        }
    }
   @FXML
    private void selectedMem(MouseEvent event) {
        mName = allMembers.getSelectionModel().getSelectedItem();
        name.setText(mName);
        name2.setText(mName);
        tName = null;
        availTeams();
        updateCurTeams();
    }
    @FXML
    private void selectedTeam(MouseEvent event) {
        tName = availteams.getSelectionModel().getSelectedItem();
    }
    @FXML
    private void selectedTeamToRemove(MouseEvent event) {
        tName = curteams.getSelectionModel().getSelectedItem();
    }
    @FXML
    private void addAssociation(ActionEvent event) {
        if(!connectivity){
            new AlertMessage("presently unavailable","offline mode running","");
            return;
        }
        if(mName == "" || tName == "" || tName == null) {
            new AlertMessage("select member/team","","please select a member\nand team for a \nsuccessfull remove");
            return;
        }
        object.addAssociation(mName, tName);
        updateCurTeams();
        availTeams();
        tName = null;
    }
    @FXML
    private void removeAssociation(ActionEvent event) {
        if(!connectivity){
            new AlertMessage("presently unavailable","offline mode running","");
            return;
        }
        if(mName == "" || tName == "" || tName == null) {
            new AlertMessage("select member/team","","please select a member\nand team for a \nsuccessfull remove");
            return;
        }
        object.removeAssociation(mName,tName);
        updateCurTeams();
        availTeams();
        tName = null;
    }
    @FXML
    private void removeMember(ActionEvent event) {
        if(!connectivity){
            new AlertMessage("presently unavailable","offline mode running","");
            return;
        }
        if(mName == "" || mName == null){
            new AlertMessage("delete uncessful","select a member","");
            return;
        }
        object.removeMember(mName);
        updateList();
        name.setText("");
        name2.setText("");
        availteams.getItems().clear();
        mName = null;
    }
    public void availTeams() {
       String memname = name.getText();
       System.out.println("++++++++++++++++++++++++"+memname);
       teamsList = object.getAvailTeams(mName);
       ObservableList teamListData = FXCollections.observableArrayList();
        for( String team : teamsList) {
            teamListData.add(team);
            System.out.println(team);
        }
         ObservableList<String> teamItems = FXCollections.observableArrayList(teamListData);
         availteams.setItems(teamItems);
   }
    private void updateList() {
        
        listItems = object.getMembers();
        ObservableList itemsData = FXCollections.observableArrayList();
        for(String strng : listItems) {
            itemsData.add(strng);
        }
        ObservableList<String> teamsList = FXCollections.observableArrayList(itemsData);
        allMembers.setItems(teamsList);
    }
    private void updateCurTeams(){
        String memname = name.getText();
       System.out.println("++++++++++++++++++++++++"+memname);
       teamsList = object.getCurTeams(mName);
       ObservableList teamListData = FXCollections.observableArrayList();
        for( String team : teamsList) {
            teamListData.add(team);
            System.out.println(team);
        }
         ObservableList<String> teamItems = FXCollections.observableArrayList(teamListData);
         curteams.setItems(teamItems);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
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
        
        object = new MemberDB();
        updateList();
    }    
    
}
