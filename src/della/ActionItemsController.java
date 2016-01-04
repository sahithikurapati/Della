/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package della;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;

/**
 *
 * @author anuroop
 */
public class ActionItemsController implements Initializable,ControlledScreen{
    /**
     * variable names that links 
     *  to fxml files.
     */

    @FXML
    private Label date;
    //--------------------------
    @FXML
    private ComboBox list;
    @FXML
    private ComboBox inclusionFactor;
    @FXML
    private ComboBox SortingDirection;
    @FXML
    private ComboBox fSortingFactor;
    @FXML
    private ComboBox sSortingFactor;
    @FXML
    private ComboBox status; 
    @FXML
    private ComboBox team;
    @FXML
    private ComboBox member;
    
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
    @FXML
    private Button update;
    @FXML
    private Button clear;
    @FXML
    private Button create;
    @FXML
    private Button Delete;
    //--------------------------   
    /**
     * creating variables to store 
     *  values in combo boxes and
     *  utility variables
     */
    ScreenController myController;
    
    public  String screen1ID = "main";
    public  String screen1File = "console.fxml";
    public  String screen2ID = "screen2";
    public  String screen2File = "ActionItems.fxml";
    public  String screen3ID = "screen3";
    public  String screen3File = "Members.fxml";
    public  String screen4ID = "screen4";
    public  String screen4File = "Teams.fxml";
    ArrayList<ActionItemNode> items = new ArrayList<>();
    ArrayList<String> statusData = new ArrayList<>();
    ArrayList<String> sortingDirection = new ArrayList<>();
    ArrayList<String> fSortingFactorData = new ArrayList<>();
    ArrayList<String> inclusionFactorData = new ArrayList<>();
    ArrayList<String> membersData = new ArrayList<>();
    ArrayList<String> teamsData = new ArrayList<>();
    ArrayList<String> listData = new ArrayList<>();
    DBPack.ActionDb object;
    //--------------------------
    String errorHead = "";
    String errorCause = "";
    String errorHandling = "";
    String mname = "";
    //--------------------------
    /**
     * checks weather date is valid compared to present or creation date.
     * @param dueDate : takes date entered in text box is passed here
     * @return boolean value weather valid(TRUE) or invalid(FALSE)
     */
    private boolean checkDate(String dueDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(dueDate);
            Date todayDate = new Date();
            if(todayDate.compareTo(date) < 0) return true;
            else return false;
        } catch (ParseException ex) {
            Logger.getLogger(ActionItemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    /**
     * a validation weather all the fields in form are filled or left empty.
     * @return boolean value weather valid(TRUE) or invalid(FALSE)
     */
    private boolean check() {
        boolean flag = true;
        if(name.getText().equals("")){flag = false;errorHandling+="name"+"\n";}
        if(description.getText().equals("")){flag = false;errorHandling+="description"+"\n";}
        if(resolution.getText().equals("")){flag = false;errorHandling+="resolution"+"\n";}
        if(due.getText().equals("")){flag = false;errorHandling+="due"+"\n";}
        if(team.getValue().toString().equals("-no team selected-") && member.getValue().toString().equals("-no member selected-")){
            flag = false;errorHandling+="must assign action item to team or member"+"\n";
        }
                
        if(!flag) {
            errorHead = "unable to update/create Action Item";
            errorCause = "fill empty fields.";
            new AlertMessage(errorHead,errorCause,errorHandling);
        }
        return flag;
    }
    /**
     * updates combobox of action item list with retrieving the data from database 
     */
    private void updateList() {
        listData.clear();
        ArrayList<ActionItemNode> result = object.getValues();
        //System.out.println("-----------------"+result.get(0));
        for(ActionItemNode x : result) {
            listData.add(x.name);
        }
        list.getItems().clear();
        list.getItems().addAll(listData);
    }
     public void setScreenParent(ScreenController screenParent){
        myController = screenParent;
    }
    //--------------------------
    @FXML
    private void goToConsole(ActionEvent event){
        try {//ScreenController mainContainer = new ScreenController();
            DellaActionItems.mainContainer.loadScreen(screen1ID, screen1File);
        }catch(Exception e){}
        //mainContainer.loadScreen();} 
        myController.setScreen(screen1ID);
    }
    @FXML
    private void goToMembers(ActionEvent event){
       myController.setScreen(screen3ID);
    }
    @FXML
    private void goToTeams(ActionEvent event){
       myController.setScreen(screen4ID);
    }
    @FXML
    /**
     * method to CREATE an action item. 
     * Event listener linked to create button.
     */
    private void createAction(ActionEvent event) {
        boolean flag = false;
        
            for(ActionItemNode x : items ) {
                if(x.name.equals(name.getText())){
                    flag = true;
                    break;
                }
            }
        
        if(flag) {
            errorHead = "unable to update/create Action Item";
            errorCause = "duplicate anction name.";
            errorHandling = "choose new action Name";
            new AlertMessage(errorHead,errorCause,errorHandling);
            return;
        } 
        else if(check())
        {
            String idate = "";
            ActionItemNode temp = new ActionItemNode();
            temp.name = name.getText();
            temp.description = description.getText();
            temp.resolution = resolution.getText();
            temp.status = status.getValue().toString();
            idate = due.getText();
            if(checkDate(idate)){
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                temp.creation = formatter.format(new Date());
                temp.due = idate;
            }
            else{
                errorHead = "unable to update/create Action Item";
                errorCause = "invalid due date";
                errorHandling = "choose new action Name";
                new AlertMessage(errorHead,errorCause,errorHandling);
                return;
            }
            if(team.getValue().toString().equals("-no team selected-"))temp.team = "none";
            else temp.team = team.getValue().toString();
            if(member.getValue().toString().equals("-no member selected-")) temp.member = "none";
            else temp.member = member.getValue().toString();
            items.add(temp);
            //listData.add(temp.name);
            object.addActionItems(temp);
            updateList();
            System.out.println(temp);
           
        }
        
    }
    
    @FXML
    /**
     * handles the clicked selection from 
     *  list and fills the corresponding data.
     */
    private void listEvent(ActionEvent event) {
        //code to be implemented when user is oline
        String value = list.getValue().toString();
        mname = value;
        ActionItemNode temp = object.getValues(value);
        
        name.setText(temp.name);
        description.setText(temp.description);
        resolution.setText(temp.resolution);
        
        due.setText(temp.due);
        if(temp.team.equals("none")) team.setValue("-no team selected-");
        else team.setValue(temp.team);
        if(temp.member.equals("none")) member.setValue("-no member selected-");
        else member.setValue(temp.member);
    }
    @FXML
    private void updateAction(ActionEvent event) {
        /*boolean cflag = false;
        if(cflag == false){
            errorHead = "unable to update/create Action Item";
            errorCause = "presently offline.";
            errorHandling = "there is no proper conection with database!.\n try again!";
            new AlertMessage(errorHead,errorCause,errorHandling);
            return;
        }*/
        ActionItemNode temp = new ActionItemNode();
        
        String value = mname;
        boolean flag = true;//false;
        boolean nflag = false;
        
        value = name.getText();
        for(String iterator : listData) {
            if(iterator.equals(value)) {
                nflag = true;
               // temp = iterator;
                break;
            }
        }
        if(!flag) {
             errorHead = "unable to update/create Action Item";
            errorCause = "Action Item not found.";
            errorHandling = "choose correct action item from list to update";
            new AlertMessage(errorHead,errorCause,errorHandling);
            return;
        }
        if(nflag) {
            errorHead = "unable to update/create Action Item";
            errorCause = "Action Item already exists.";
            errorHandling = "choose correct action item from list to update";
            new AlertMessage(errorHead,errorCause,errorHandling);
        }
        else  if(check()) {
            String idate = "";
            temp.name = name.getText();
            temp.description = description.getText();
            temp.resolution = resolution.getText();
            idate = due.getText();
            if(checkDate(idate)){
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                //temp.creation = formatter.format(new Date());
                temp.due = idate;
            }
            else{
                errorHead = "unable to update/create Action Item";
                errorCause = "invalid due date";
                errorHandling = "choose new action Name";
                new AlertMessage(errorHead,errorCause,errorHandling);
                return;
            }
            if(team.getValue().toString().equals("-no team selected-"))temp.team = "none";
            else temp.team = team.getValue().toString();
            if(member.getValue().toString().equals("-no member selected-")) temp.member = "none";
            else temp.member = member.getValue().toString();
            
            object.updateActions(temp, mname);
            updateList();
            System.out.println(temp);
        }
        
       
    }
     @FXML
    private void clearAction(ActionEvent event) {
        name.setText("");
        description.setText("");
        resolution.setText("");
        due.setText("");
        //temp.team = team.getValue().toString();
        //temp.member = member.getValue().toString();
          
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        object = new DBPack.ActionDb();
        
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
        team.getItems().addAll(teamsData);
        member.getItems().addAll(membersData);
        status.getItems().addAll(statusData);
        inclusionFactor.getItems().addAll(inclusionFactorData);
        SortingDirection.getItems().addAll(sortingDirection);
        fSortingFactor.getItems().addAll(fSortingFactorData);
        sSortingFactor.getItems().addAll(fSortingFactorData);
        member.setValue("-no member selected-");
        team.setValue("-no team selected-");
        inclusionFactor.setValue("All action Items");
        status.setValue("open");
        SortingDirection.setValue("small to large");
        fSortingFactor.setValue("none");
        sSortingFactor.setValue("none");
        // wiil be implemented when the fxml document is inititalized.
        //i think we can call other methods or objects of class from here. 
        date.setText(Integer.toString(dt.getDate())+"-"+Integer.toString(dt.getMonth())+"-"+Integer.toString(1900+dt.getYear()));
        
    }    
    
}
