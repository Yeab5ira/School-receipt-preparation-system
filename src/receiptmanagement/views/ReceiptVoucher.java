package receiptmanagement.views;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import services.CustomMethods;

public class ReceiptVoucher {
    
    //create the root borderpane
    BorderPane root = new BorderPane();
    CustomMethods cmMaster = new CustomMethods();
    
    public ReceiptVoucher(Stage primaryStage){
        //create the navigation bar
        AnchorPane apNavbar = new AnchorPane();
        apNavbar.setId("navbar");
        HBox hbNavbuttonContainer = new HBox(15);
        
        //create the navigation bar buttons
        Button btnBack = cmMaster.faButton("CHEVRON_LEFT","1em","WHITE","Back");
        //assign class name to the buttons
        btnBack.getStyleClass().add("btn-primary");
        
        //add the navigation buttons to their container
        hbNavbuttonContainer.getChildren().addAll(btnBack);
        
        //align the signout button and the other navigation buttons to opposite ends
        AnchorPane.setRightAnchor(hbNavbuttonContainer, 0.00);
        AnchorPane.setTopAnchor(hbNavbuttonContainer, 0.00);
        AnchorPane.setBottomAnchor(hbNavbuttonContainer, 0.00);
        
        //add the navigation bar nodes to the navbar Anchorpane we created
        apNavbar.getChildren().addAll(btnBack,hbNavbuttonContainer);
        
        
        //************************* body ----------------------------------
        
        VBox vbBodyContainer = new VBox(5);
        vbBodyContainer.setAlignment(Pos.TOP_CENTER);
        Label lblReceiptHeader = new Label("CASH RECEIPT VOUCHER");
        lblReceiptHeader.setStyle("-fx-font-size: 5em; -fx-font-weight: bolder; -fx-text-fill: #aaaaaa");
        
        //receipt inputs container
        VBox vbInputsContainer = new VBox(10.00);
        //serial input
        HBox hbSerialContainer = new HBox(5);
        Label lblSerial = new Label("Serial No.");
        TextField tfSerial = new TextField();
        hbSerialContainer.getChildren().addAll(lblSerial,tfSerial);
        //date input
        HBox hbDateContainer = new HBox(5);
        Label lblDate = new Label("Date");
        DatePicker dpDate = new DatePicker();
        hbDateContainer.getChildren().addAll(lblDate,dpDate);
        //from input
        HBox hbFromContainer = new HBox(5);
        Label lblFrom = new Label("Received From");
        TextField tfFrom = new TextField();
        hbFromContainer.getChildren().addAll(lblFrom,tfFrom);
        //sum input
        HBox hbSumContainer = new HBox(5);
        Label lblSum = new Label("The Sum Of Birr");
        TextField tfSum = new TextField();
        hbSumContainer.getChildren().addAll(lblSum,tfSum);
        //sum input
        HBox hbForContainer = new HBox(5);
        Label lblFor = new Label("For");
        TextField tfFor = new TextField();
        hbForContainer.getChildren().addAll(lblFor,tfFor);
        //monthly payment input
        HBox hbMonthlyPaymentContainer = new HBox(5);
        Label lblMonthly = new Label("Monthly Payment");
        FlowPane fpMonthsContainer = new FlowPane(Orientation.HORIZONTAL);
        fpMonthsContainer.setPrefWidth(800.00);
        //month buttons
        ArrayList<CheckBox> alMonthButtons = new ArrayList<>();
        String[] stMonthList = {"sept","oct","nov","dece","jan","feb","mar","apr","may","jun","jul","aug"};
        for (String month : stMonthList) {
            CheckBox cbMonth = new CheckBox(month);
            cbMonth.setOnAction((ActionEvent event) -> {
                
            });
            cbMonth.setPadding(new Insets(10,10,10,10));
            alMonthButtons.add(cbMonth);
            
        }
        fpMonthsContainer.getChildren().addAll(alMonthButtons);
        hbMonthlyPaymentContainer.getChildren().addAll(lblMonthly,fpMonthsContainer);
        //grades list
        HBox hbGradeContainer = new HBox();
        Label lblGrade = new Label("Grade");
        MenuButton mbGrade = new MenuButton("1");
        mbGrade.setPrefWidth(150.00);
        TextField tfSec = new TextField();
        tfSec.setPrefWidth(50.00);
        for(int i=1; i<13; i++){
            MenuItem miGrade = new MenuItem(i+"");
            mbGrade.getItems().addAll(miGrade);
        }
        hbGradeContainer.getChildren().addAll(lblGrade,mbGrade);
        
        
        //add inputs to the container
        vbInputsContainer.getChildren().addAll(hbSerialContainer,hbDateContainer,hbFromContainer,hbSumContainer,hbForContainer,hbMonthlyPaymentContainer,hbGradeContainer);
        
        vbBodyContainer.getChildren().addAll(lblReceiptHeader,vbInputsContainer);
        
    
        //add the nodes to the root
        root.setTop(apNavbar);
        root.setCenter(vbBodyContainer);
    }
    
    
    public BorderPane getReceiptVoucherView(){
        return root;
    }
}
