
package della;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package dellaactionitems;


import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author anuroop
 */
public class ConsoleController implements Initializable,ControlledScreen{
    @FXML
    private Label date;
    @FXML
    private Label team;
    @FXML
    private Label member;
    @FXML
    private Label status;
    @FXML
    private Label onof;
    //--------------------------
    @FXML
    private ListView<String> list;
    @FXML
    private ComboBox inclusionFactor;
    @FXML
    private ComboBox SortingDirection;
    @FXML
    private ComboBox fSortingFactor;
    @FXML
    private ComboBox sSortingFactor;
    
    
    //--------------------------
    @FXML
    private TextField name;
    @FXML
    private TextField description;
    @FXML
    private TextField resolution;
    @FXML
    private TextField due;
    //--------------------------
    
    //--------------------------   
    DBPack.ActionDb object= new DBPack.ActionDb();
    ScreenController myController;
    public  String screen1ID = "main";
    public  String screen1File = "console.fxml";
    public  String screen2ID = "screen2";
    public  String screen2File = "ActionItems.fxml";
    public  String screen3ID = "screen3";
    public  String screen3File = "Members.fxml";
    public  String screen4ID = "screen4";
    public  String screen4File = "Teams.fxml";
    public ArrayList<ActionItemNode> items = new ArrayList<>();
    public ArrayList<String> statusData = new ArrayList<>();
    public ArrayList<String> sortingDirection = new ArrayList<>();
    public ArrayList<String> fSortingFactorData = new ArrayList<>();
    public ArrayList<String> inclusionFactorData = new ArrayList<>();
    public ArrayList<String> membersData = new ArrayList<>();
    public ArrayList<String> teamsData = new ArrayList<>();
     //= new List<>();
    //--------------------------
    String errorHead = "";
    String errorCause = "";
    String errorHandling = "";
    String order = "";
    String firstDir = "";
    String secondDir = "";
    boolean connectivity = false;
    //--------------------------
   
     public void setScreenParent(ScreenController screenParent){
        myController = screenParent;
        
    }
      @FXML
   private void setOrder(ActionEvent event){
       if(SortingDirection.getValue().toString().equals("small to large")){
           order = "asc";
       }
       else{
           order = "desc";
       }
   }
   @FXML
   private void setFirstDir(ActionEvent event){
       String value = fSortingFactor.getValue().toString();
       switch(value){
           case "none":{
               firstDir = "";
               break;
           }
           case "created date":{
            firstDir = "created"; 
            break;
           }
           case "due date":{
               firstDir = "dued";
               break;
           }
           case "assigned member":{
               firstDir = "Member";
               break;
           }
           case "assigned team":{
               firstDir = "Team";
               break;
           }
               
       }
       updateList(true);
   }
    @FXML
   private void setSecondDir(ActionEvent event){
       String value = sSortingFactor.getValue().toString();
       switch(value){
           case "none":{
               secondDir = "";
               break;
           }
           case "created date":{
            secondDir = "created"; 
            break;
           }
           case "due date":{
               secondDir = "dued";
               break;
           }
           case "assigned member":{
               secondDir = "Member";
               break;
           }
           case "assigned team":{
               secondDir = "Team";
               break;
           }
               
       }
       updateList(true);
   }
    @FXML
    public void updateList() {
        System.out.println("==>updating list!");
        items = object.getValues();
        ObservableList listData = FXCollections.observableArrayList();
       listData.clear();
        for(ActionItemNode iterator : items) {
            listData.add(iterator.name);
        }
        ObservableList<String> aitems = FXCollections.observableArrayList(listData); //aitems.addAll(listData);
        list.setItems(aitems);
        
        System.out.println("==>done updating list!");
    }
    @FXML
    public void updateList(boolean bx) {
        String temp = "";
        System.out.println("==>updating list!");
        items = object.getValues();
        ObservableList listData = FXCollections.observableArrayList();
       listData.clear();
        for(ActionItemNode x : items) {
            switch(firstDir){
           case "none":{
               
               break;
           }
           case "created":{
            temp += x.creation+":"; 
            break;
           }
           case "dued":{
               temp += x.due+":";
               break;
           }
           case "Member":{
               temp += x.member+":";
               break;
           }
           case "Team":{
               temp += x.team;
               break;
           }            
           }
            switch(secondDir){
           case "none":{
               
               break;
           }
           case "created":{
            temp += x.creation+":"; 
            break;
           }
           case "dued":{
               temp += x.due+":";
               break;
           }
           case "Member":{
               temp += x.member+":";
               break;
           }
           case "Team":{
               temp += x.team;
               break;
           }
            }
           temp += x.name;
            listData.add(temp);
            temp = "";
        }
        ObservableList<String> aitems = FXCollections.observableArrayList(listData); //aitems.addAll(listData);
        list.setItems(aitems);
        
        System.out.println("==>done updating list!");
    }
    //--------------------------
    @FXML
    private void goToActionItems(ActionEvent event){
       myController.setScreen(screen2ID);
    }
    @FXML
    private void goToTeams(ActionEvent event){
       myController.setScreen(screen4ID);
    }
    @FXML
    private void goToMembers(ActionEvent event){
       myController.setScreen(screen3ID);
    }
    //--------------------------
    @FXML
    private void setFields(MouseEvent  event) {
        String nname = list.getSelectionModel().getSelectedItem();
        StringTokenizer st = new StringTokenizer(nname,":");
        while(st.hasMoreTokens()) {nname = st.nextToken();}
        System.out.println("selected name is:"+nname);
        ActionItemNode temp = null;
        for(ActionItemNode iterator : items) {
            if(iterator.name.equals(nname)){
                temp = iterator;
                break;
            }
        }
        name.setText(temp.name);
        description.setText(temp.description);
        resolution.setText(temp.resolution);
        date.setText(temp.creation);
        due.setText(temp.due);
        System.out.println(temp.due);
        team.setText(temp.team);
        status.setText(temp.status);
        member.setText(temp.member);
    }
    //--------------------------
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("ccccccccccccccccccccccccccccccccccc");
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
        updateList();
        Date dt = new Date();
        statusData.add("open");
        statusData.add("close");
        sortingDirection.add("large to small");
        sortingDirection.add("small to large");
        fSortingFactorData.add("none");
        fSortingFactorData.add("created date");
        fSortingFactorData.add("due date");
        fSortingFactorData.add("assigned member");
        fSortingFactorData.add("assigned team");
        inclusionFactorData.add("All action Items");
        inclusionFactorData.add("Open Action Items");
        inclusionFactorData.add("Closed Action Items");
        teamsData.add("-no team selected-");
        teamsData.add("sample");
        membersData.add("-no member selected-");
        membersData.add("sample");
        
        inclusionFactor.getItems().addAll(inclusionFactorData);
        SortingDirection.getItems().addAll(sortingDirection);
        fSortingFactor.getItems().addAll(fSortingFactorData);
        sSortingFactor.getItems().addAll(fSortingFactorData);
        
        inclusionFactor.setValue("All action Items");
        SortingDirection.setValue("small to large");
        fSortingFactor.setValue("none");
        sSortingFactor.setValue("none");
        
        // wiil be implemented when the fxml document is inititalized.
        //i think we can call other methods or objects of class from here. 
       // date.setText(Integer.toString(dt.getDate())+"-"+Integer.toString(dt.getMonth())+"-"+Integer.toString(1900+dt.getYear()));
        
    }    
    
}

/*import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Angie
 */
/*public class ConsoleController implements Initializable , ControlledScreen {

     public  String screen1ID = "main";
    public  String screen1File = "ActionItems.fxml";
    public  String screen2ID = "screen2";
    public  String screen2File = "console.fxml";
    public  String screen3ID = "screen3";
    public  String screen3File = "Screen3.fxml";
    ScreenController myController;
    /**
     * Initializes the controller class.
     */
   /* @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setScreenParent(ScreenController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreen1(ActionEvent event){
       myController.setScreen(screen1ID);
    }
    
    @FXML
    private void goToScreen3(ActionEvent event){
       myController.setScreen(screen3ID);
    }
}
*/