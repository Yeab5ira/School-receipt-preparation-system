package receiptmanagement.views;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import receiptmanagement.ReceiptManagement;
import receiptmanagement.models.Payment;
import receiptmanagement.models.Student;
import services.CustomMethods;
import services.Database;

public class ImportStatement {
    
    //create instance of custom methods 
    CustomMethods cmMaster = new CustomMethods();
    
    //create the file object for upload
    File fileStatement = null;
    
    //Define the table view for students list
    TableView tvPayments = new TableView();
    
    //make the table columns for the table view
    TableColumn tcId = new TableColumn("ID");
    TableColumn tcPayer = new TableColumn("Payer");
    TableColumn tcGrade = new TableColumn("Grade");
    TableColumn tcSection= new TableColumn("Section");
    TableColumn tcAmount = new TableColumn("Amount");
    TableColumn tcReason = new TableColumn("Reason");
    
    public void populatePaymentsTable(File file){
        int idIndex = 0;
        Date curDate = new Date();
        SimpleDateFormat curDateFormat = new SimpleDateFormat("mm/MM/yyyy");
        ObservableList<Payment> paymentList = FXCollections.observableArrayList();
        
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNext()){
                String payment = sc.nextLine();
                String[] paymentFields = payment.split(",");
                String payer = paymentFields[1].trim() + " " + paymentFields[2].trim() + " " + paymentFields[3].trim();
                int grade  = Integer.parseInt(paymentFields[4].trim());
                String sec = paymentFields[5].trim();
                float amount = Float.parseFloat(paymentFields[6].trim());
                String reason = paymentFields[7].trim();
                Payment paymentObj = new Payment(payer, curDateFormat.format(curDate), reason, "-", sec, idIndex++, grade, amount, false);
                
                paymentList.add(paymentObj);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcPayer.setCellValueFactory(new PropertyValueFactory<>("payer"));
        tcGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        tcSection.setCellValueFactory(new PropertyValueFactory<>("sec"));
        tcAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tcReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        
        tvPayments.setItems(paymentList);
        
    }
    
    BorderPane root = new BorderPane();
    
    public ImportStatement(Stage primaryStage) {
        //create the navigation bar
        AnchorPane apNavbar = new AnchorPane();
        apNavbar.setId("navbar");
        
        //create the navigation bar buttons
        Button btnBack = cmMaster.faButton("CHEVRON_LEFT","1em","WHITE","Back");
        //assign class name to the buttons
        btnBack.getStyleClass().add("btn-primary");
        
        //add the navigation bar nodes to the navbar Anchorpane we created
        apNavbar.getChildren().addAll(btnBack);
        
        
//        ************* body *****************
        
        //create the body container
        AnchorPane apBody = new AnchorPane();
        
        //create the import bank statement card
        VBox vbImportStatement = new VBox(10);
        vbImportStatement.setPadding(new Insets(10,10,10,10));
        vbImportStatement.setStyle("-fx-background-color: WHITE");
        vbImportStatement.setAlignment(Pos.CENTER);
        Label lblImportBankStatement = new Label("Import Bank Statement");
        lblImportBankStatement.getStyleClass().add("header-lbl-md");
        Label lblFile = new Label("Choose File");
        lblFile.getStyleClass().add("light-lbl-md");
        HBox hbFileChooserContainer = new HBox(5);
        hbFileChooserContainer.setStyle("-fx-background-color: #dddddd");
        hbFileChooserContainer.setPrefWidth(250.00);
        Button btnFilePicker = cmMaster.faButton("UPLOAD","1em","BLACK","");
        Label lblFileName = new Label("Select a .csv File");
        hbFileChooserContainer.getChildren().addAll(btnFilePicker,lblFileName);
        FileChooser fcStatementFile = new FileChooser();
        fcStatementFile.setTitle("Choose Statement");
        fcStatementFile.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
        
        Button btnPreview = new Button("Preview");
        Button btnImport = new Button("Import");
        btnPreview.setPrefWidth(250.00);
        btnImport.setPrefWidth(250.00);
        btnPreview.getStyleClass().add("preview-btn");
        btnImport.getStyleClass().add("import-btn");
        
        //add on action event handler to the file chooser
        btnFilePicker.setOnAction((ActionEvent event) -> {
            fileStatement = fcStatementFile.showOpenDialog(primaryStage);
            
            if(fileStatement != null){
//                Desktop desktop = Desktop.getDesktop();
//                try {
//                    desktop.open(fileStatement);
//                } catch (IOException ex) {
//                    Logger.getLogger(ImportStatement.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                populatePaymentsTable(fileStatement);
                lblFileName.setText(fileStatement.getName());
            }
        });
        
        btnPreview.setOnAction((ActionEvent event)->{
            if(fileStatement != null){
                populatePaymentsTable(fileStatement);
            }
        });
        
        //align import vbox
        AnchorPane.setTopAnchor(vbImportStatement, 10.00);
        AnchorPane.setLeftAnchor(vbImportStatement, 10.00);
        
        //add nodes to the import vbox
        vbImportStatement.getChildren().addAll(lblImportBankStatement,new Separator(),lblFile,hbFileChooserContainer,new Separator(), btnPreview, btnImport);

        //add the columns to the table view
        tvPayments.getColumns().addAll(tcId,tcPayer,tcGrade,tcSection,tcAmount,tcReason);
        tcPayer.setPrefWidth(150.00);
        tcReason.setPrefWidth(200.00);
        
        //make the table responsive
        AnchorPane.setRightAnchor(tvPayments, 10.00);
        AnchorPane.setLeftAnchor(tvPayments, 320.00);
        AnchorPane.setTopAnchor(tvPayments, 10.00);
        AnchorPane.setBottomAnchor(tvPayments, 10.00);
        
        apBody.getChildren().addAll(vbImportStatement,tvPayments);

        //add navbar to top
        root.setTop(apNavbar);
        root.setCenter(apBody);
    }
    
    public BorderPane getImportStatementView() {
        return root;
    }
}
