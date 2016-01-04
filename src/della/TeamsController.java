
package della;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package dellaactionitems;

import DBPack.AssociationsDB;
import DBPack.TeamsDB;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    String errorHead = "";
    String errorCause = "";
    String errorHandling = "";
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
    private void goToMembers(ActionEvent event){
       myController.setScreen(screen3ID);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("tttttttttttttttttttttttttttttttt");
        TeamsDB object = new TeamsDB();
        AssociationsDB aObject = new AssociationsDB();
    }    
    
}
