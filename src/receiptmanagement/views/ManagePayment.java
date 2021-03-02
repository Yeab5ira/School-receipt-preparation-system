package receiptmanagement.views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import receiptmanagement.models.Student;
import receiptmanagement.ReceiptManagement;
import services.CustomMethods;
import services.Database;

public final class ManagePayment {
    
    //create instance of custom methods 
    CustomMethods cmMaster = new CustomMethods();
    
    //Define the table view for students list
    TableView tvStudents = new TableView();
    
    //make the table columns for the table view
    TableColumn tcId = new TableColumn("ID");
    TableColumn tcFirstName = new TableColumn("First Name");
    TableColumn tcMiddleName = new TableColumn("Middle Name");
    TableColumn tcLastName = new TableColumn("Last Name");
    TableColumn tcGrade = new TableColumn("Grade");
    TableColumn tcSection= new TableColumn("Section");
    TableColumn tcRemaining = new TableColumn("Remaining");
    
    
    public void populateStudentsTable(int grade) {
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        Database dbController = new Database();
        
        Connection con = dbController.createCon();
        String studentsQuery = "SELECT * FROM students WHERE grade=?";
        
        
        try {
            PreparedStatement psStudentQuery = con.prepareStatement(studentsQuery);
            psStudentQuery.setInt(1, grade);
            ResultSet rs = psStudentQuery.executeQuery();
            while(rs.next()){
                studentList.add(new Student(rs.getInt("id"),rs.getInt("grade"),rs.getString("fn"),rs.getString("mn"),rs.getString("ln"),rs.getString("gender"),rs.getString("sec"),rs.getFloat("remaining"),rs.getBoolean("left_school")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("fn"));
        tcMiddleName.setCellValueFactory(new PropertyValueFactory<>("mn"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("ln"));
        tcGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        tcSection.setCellValueFactory(new PropertyValueFactory<>("sec"));
        tcRemaining.setCellValueFactory(new PropertyValueFactory<>("remaining"));
        
        tvStudents.getItems().clear();
        tvStudents.setItems(studentList);
    }
    
    public void switchPaymentStat(ActionEvent event){
        
    }
    
    BorderPane root = new BorderPane();
    
    
    public ManagePayment(Stage primaryStage){
        
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
        
        
        // *************************** Body ************************************ //
        
        //Create the body
        AnchorPane apBody = new AnchorPane();
        apBody.setId("body");
        
        
        //make the table change sizes on different screens
        AnchorPane.setLeftAnchor(tvStudents, 10.00);
        AnchorPane.setRightAnchor(tvStudents, 350.00);
        AnchorPane.setTopAnchor(tvStudents, 10.00);
//        AnchorPane.setBottomAnchor(tvStudents, 02.00);
        
        
        //add the columns to the table view
        tvStudents.getColumns().addAll(tcId,tcFirstName,tcMiddleName,tcLastName,tcGrade,tcSection,tcRemaining);
        
        //populate the table
        populateStudentsTable(1);
        
        //create previous payments table container
        VBox vbMonthlyStatus = new VBox(10);
        vbMonthlyStatus.setPrefWidth(310.00);
        vbMonthlyStatus.setStyle("-fx-background-color: white; -fx-padding:10px;");
        vbMonthlyStatus.setAlignment(Pos.TOP_CENTER);
        
        AnchorPane.setRightAnchor(vbMonthlyStatus, 15.00);
        AnchorPane.setTopAnchor(vbMonthlyStatus, 15.00);
//        AnchorPane.setBottomAnchor(vbMonthlyStatus, 15.00);
        
        //create the labels for monthly payment stat
        Label lblMonthlyStat = new Label("Monthly Payment Stat.");
        lblMonthlyStat.getStyleClass().add("header-lbl-md");
        
        
        //month buttons
        ArrayList<Button> alMonthButtons = new ArrayList<>();
        String[] stMonthList = {"sept","oct","nov","dece","jan","feb","mar","apr","may","jun","jul","aug"};
        for (String month : stMonthList) {
            Button btnMonth = new Button(month);
            btnMonth.setOnAction((ActionEvent event) -> {
                switchPaymentStat(event);
            });
            btnMonth.setPrefWidth(250.00);
            btnMonth.getStyleClass().add("mBtn");
            alMonthButtons.add(btnMonth);
            
        }
        
        //add nodes to the month status button container
        vbMonthlyStatus.getChildren().addAll(lblMonthlyStat, new Separator());
        vbMonthlyStatus.getChildren().addAll(alMonthButtons);
        
        //add nodes to the body container
        apBody.getChildren().addAll(tvStudents, vbMonthlyStatus);
        
        // ****************************** footer for grade buttons *************************************** //
        
        //create an hbox for the bottom grade buttons container
        HBox hbBottom = new HBox(15);
        hbBottom.setPadding(new Insets(0,0,10,20));
//        hbBottom.setAlignment(Pos.CENTER);
        
        //create an array list to store the buttons for the grade selectors and loop through them to save repetition of code
        ArrayList<Button> alGradeButtons = new ArrayList<>();
        for(int i=1; i<13; i++){
            final int grade = i;
            Button btnGrade = new Button("G-"+i);
            btnGrade.setOnAction((ActionEvent event)->{
                populateStudentsTable(grade);
            });
            alGradeButtons.add(btnGrade);
        }
        alGradeButtons.forEach((btn)-> {
            btn.getStyleClass().add("gButton");
        });
        hbBottom.getChildren().addAll(alGradeButtons);
        
        // *********************************************************** //
        
//        resize the tables according to the stage size
        primaryStage.heightProperty().addListener((obs)->{
            tvStudents.setPrefHeight(primaryStage.getHeight()- 200.00);
            vbMonthlyStatus.setPrefHeight(primaryStage.getHeight()-420.00);
        });
        
        //listen to table row click 
        tvStudents.getSelectionModel().selectedItemProperty().addListener((ObservableValue observableValue, Object oldValue, Object newValue) -> {
            if(tvStudents.getSelectionModel().getSelectedItem() != null){
                Student st = (Student) tvStudents.getSelectionModel().getSelectedItem();
            }
        });
        
        
        //add the nodes to the root
        root.setTop(apNavbar);
        root.setCenter(apBody);
        root.setBottom(hbBottom);
        
    }
    
    public BorderPane getManagePaymentView(){
        return this.root;
    }
}
