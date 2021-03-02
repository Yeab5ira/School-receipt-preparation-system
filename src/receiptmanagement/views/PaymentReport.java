package receiptmanagement.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.CustomMethods;

public class PaymentReport {
    
    
    //instantiate custom methods
    CustomMethods cmMaster = new CustomMethods();
    
    //create the root borderpane
    BorderPane root = new BorderPane();
    
    public PaymentReport(Stage primaryStage){
        //create the navigation bar
        AnchorPane apNavbar = new AnchorPane();
        apNavbar.setId("navbar");
        
        //create the navigation bar buttons
        Button btnBack = cmMaster.faButton("CHEVRON_LEFT","1em","WHITE","Back");
        //assign class name to the buttons
        btnBack.getStyleClass().add("btn-primary");
        
        //add the navigation bar nodes to the navbar Anchorpane we created
        apNavbar.getChildren().addAll(btnBack);
        
        
        // -------------------- Body ---------------------- //
        
        VBox vbBodyContainer = new VBox(20);
        HBox hbFilterContainer = new HBox(15);
        hbFilterContainer.setAlignment(Pos.CENTER_LEFT);
        hbFilterContainer.setPadding(new Insets(10,10,10,10));
        
        CheckBox cbGrade = new CheckBox("Grade");
        CheckBox cbDate = new CheckBox("Date");
        MenuButton mbGrade = new MenuButton("1");
        for(int i=1; i<13; i++){
            MenuItem miGrade = new MenuItem(i+"");
            mbGrade.getItems().addAll(miGrade);
        }
        DatePicker dpDate = new DatePicker();
        
        HBox hbGrade = new HBox();
        
        hbGrade.getChildren().addAll(cbGrade,mbGrade);
        hbGrade.setAlignment(Pos.CENTER_LEFT);
        HBox hbDate = new HBox();
        hbDate.setAlignment(Pos.CENTER_LEFT);
        hbDate.getChildren().addAll(cbDate,dpDate);
        hbDate.getStyleClass().add("options-container");
        hbGrade.getStyleClass().add("options-container");
        
        Button btnFilter = cmMaster.faButton("FILTER", "1em", "BLACK", "Filter");
        hbFilterContainer.getChildren().addAll(hbGrade,hbDate, btnFilter);
        
        
//      Container to hold the custom table
        ScrollPane spTableContainer = new ScrollPane();
        spTableContainer.setFitToWidth(true);
        
        //resize the tables according to the stage size
        primaryStage.heightProperty().addListener((obs)->{
            spTableContainer.setPrefHeight(primaryStage.getHeight() - 200.00);
        });
        
        
        VBox vbItemsContainer = new VBox(5);
        vbItemsContainer.setMaxWidth(Double.MAX_VALUE);
        vbItemsContainer.getChildren().addAll(cmMaster.hbTableItem("ID", "PAYER NAME", "GRADE", "REMAINING", "SERIAL", "AMOUNT", "REASON"));
        for(int i=0; i<50; i++){
            vbItemsContainer.getChildren().addAll(cmMaster.hbTableItem("ID", "PAYER NAME", "GRADE", "REMAINING", "SERIAL", "AMOUNT", "REASON"));
        }
        spTableContainer.setContent(vbItemsContainer);
        //add nodes to the body container
        vbBodyContainer.getChildren().addAll(hbFilterContainer,cmMaster.hbTableHeader(),spTableContainer);
        
        //add the nodes to the root border pane
        root.setTop(apNavbar);
        root.setCenter(vbBodyContainer);
    }
    
    public BorderPane getPaymentReportView(){
        return root;
    }
}
