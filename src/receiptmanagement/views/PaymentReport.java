package receiptmanagement.views;

import java.sql.Date;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import receiptmanagement.models.Payment;
import receiptmanagement.services.CustomMethods;
import receiptmanagement.services.PaymentServices;

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
        Button btnManageReport = cmMaster.faButton("LIST", "1em", "WHITE", "Manage Report");
        //assign class name to the buttons
        btnBack.getStyleClass().add("btn-primary");
        btnManageReport.getStyleClass().add("btn-primary");
        
        AnchorPane.setRightAnchor(btnManageReport, 10.00);
        
        //back button action
        btnBack.setOnAction(e ->{
            cmMaster.switchView(primaryStage, "StudentsList");
        });
        btnManageReport.setOnAction(e->{
            cmMaster.switchView(primaryStage, "ManagePayment");
        });
        //add the navigation bar nodes to the navbar Anchorpane we created
        apNavbar.getChildren().addAll(btnBack,btnManageReport);
        
        
        // -------------------- Body ---------------------- //
        
        VBox vbBodyContainer = new VBox(20);
        HBox hbFilterContainer = new HBox(15);
        hbFilterContainer.setAlignment(Pos.CENTER_LEFT);
        hbFilterContainer.setPadding(new Insets(10,10,10,10));
        
        CheckBox cbGrade = new CheckBox("Grade");
        CheckBox cbDate = new CheckBox("Date");
        ComboBox cobGrade = new ComboBox();
        cobGrade.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        
        DatePicker dpDate = new DatePicker();
        
        HBox hbGrade = new HBox();
        
        hbGrade.getChildren().addAll(cbGrade,cobGrade);
        hbGrade.setAlignment(Pos.CENTER_LEFT);
        HBox hbDate = new HBox();
        hbDate.setAlignment(Pos.CENTER_LEFT);
        hbDate.getChildren().addAll(cbDate,dpDate);
        hbDate.getStyleClass().add("options-container");
        hbGrade.getStyleClass().add("options-container");
        
        VBox vbItemsContainer = new VBox(5);
        vbItemsContainer.setMaxWidth(Double.MAX_VALUE);
        
        
        Button btnFilter = cmMaster.faButton("FILTER", "1em", "BLACK", "Filter");
        
        TextField tfSearchField = new TextField();
        tfSearchField.setPromptText("Search Name");
        //add input change listener for the search input
        tfSearchField.textProperty().addListener((obs, oldVal,newVal)->{
            ArrayList<Payment> alPayments;
            alPayments = new PaymentServices().searchReport(newVal);
            //clear the table before adding the filtered data
            vbItemsContainer.getChildren().clear();
            //loop through the payments and instantiate a custom table item
            alPayments.forEach(payment -> {
                vbItemsContainer.getChildren().addAll(cmMaster.hbTableItem(String.format("%05d" , payment.getSerial()), payment.getPayer(), payment.getGrade()+"", "remaining",  payment.getSerial()+"", payment.getAmount()+"", payment.getDate()+"", payment.getReason(), payment.isDisabled()));
            });
        });
        
        hbFilterContainer.getChildren().addAll(hbGrade,hbDate, btnFilter,tfSearchField);
        
        //filter button action 
        btnFilter.setOnAction(e->{
            ArrayList<Payment> alPayments;
            //if the date and grade checkboxes are checked
            if(cbDate.isSelected() && cbGrade.isSelected()){
                alPayments = new PaymentServices().fetchReport(Integer.parseInt(cobGrade.getValue().toString()), Date.valueOf(dpDate.getValue()));
            }
            else if(!cbDate.isSelected() && cbGrade.isSelected()){ //if only grade checkbox is selected
                alPayments = new PaymentServices().fetchReport(Integer.parseInt(cobGrade.getValue().toString()));
            }
            else if(cbDate.isSelected() && !cbGrade.isSelected()){ //if only date checkbox is selected
                alPayments = new PaymentServices().fetchReport(Date.valueOf(dpDate.getValue()));
                
            } else { //load all if both the filter optios are disabled
                alPayments = new PaymentServices().fetchReport();
            }
            //clear the table before adding the filtered data
            vbItemsContainer.getChildren().clear();
            //loop through the payments and instantiate a custom table item
            alPayments.forEach(payment -> {
                vbItemsContainer.getChildren().addAll(cmMaster.hbTableItem(String.format("%05d" , payment.getSerial()), payment.getPayer(), payment.getGrade()+"", "remaining",  payment.getSerial()+"", payment.getAmount()+"", payment.getDate()+"", payment.getReason(), payment.isDisabled()));
            });
            
        });
        
        
        //Container to hold the custom table
        ScrollPane spTableContainer = new ScrollPane();
        spTableContainer.setFitToWidth(true);
        
        //resize the tables according to the stage size
        primaryStage.heightProperty().addListener((obs)->{
            spTableContainer.setPrefHeight(primaryStage.getHeight() - 200.00);
        });
        
        //populate the table with the payments on initial setup
        ArrayList<Payment> alPayments = new PaymentServices().fetchReport();
        alPayments.forEach(payment -> {
            vbItemsContainer.getChildren().addAll(cmMaster.hbTableItem(String.format("%05d" , payment.getSerial()), payment.getPayer(), payment.getGrade()+"", "remaining",  payment.getSerial()+"", payment.getAmount()+"", payment.getDate()+"", payment.getReason(), payment.isDisabled()));
        });
        
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
