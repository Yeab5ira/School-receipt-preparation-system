package receiptmanagement.views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import receiptmanagement.models.Monthly;
import receiptmanagement.services.CustomMethods;
import receiptmanagement.services.Database;

public final class ManagePayment {
    
    //create instance of custom methods 
    CustomMethods cmMaster = new CustomMethods();
    
    //Define the table view for students list
    TableView tvStudents = new TableView();
    
    //global monthly model
    Monthly curMonthly;
    
    
    //make the table columns for the table view
    TableColumn tcId = new TableColumn("ID");
    TableColumn tcFirstName = new TableColumn("First Name");
    TableColumn tcMiddleName = new TableColumn("Middle Name");
    TableColumn tcLastName = new TableColumn("Last Name");
    TableColumn tcGrade = new TableColumn("Grade");
    TableColumn tcSection= new TableColumn("Section");
    TableColumn tcRemaining = new TableColumn("Remaining");
    TableColumn tcSept = new TableColumn("Sept");
    TableColumn tcOct = new TableColumn("Oct");
    TableColumn tcNov = new TableColumn("Nov");
    TableColumn tcDec = new TableColumn("Dec");
    TableColumn tcJan = new TableColumn("Jan");
    TableColumn tcFeb = new TableColumn("Feb");
    TableColumn tcMar = new TableColumn("Mar");
    TableColumn tcApr = new TableColumn("Apr");
    TableColumn tcMay = new TableColumn("May");
    TableColumn tcJun = new TableColumn("Jun");
    TableColumn tcJul = new TableColumn("Jul");
    TableColumn tcAug = new TableColumn("Aug");
    
    
    
    //populate students table for the selected grade
    public void populateStudentsTable(int grade) {
        ObservableList<Monthly> monthList = FXCollections.observableArrayList();
        Database dbController = new Database();
        
        Connection con = dbController.createCon();
        String studentsQuery = "SELECT students.id, students.fn, students.mn, students.ln, students.gender, students.grade, students.sec,students.remaining, monthly.sept, "
                + "monthly.oct, monthly.nov, monthly.dece, monthly.jan, monthly.feb, monthly.mar, monthly.apr, monthly.may, monthly.jun, monthly.jul, "
                + "monthly.aug FROM monthly INNER JOIN students WHERE students.id = monthly.sId AND grade = ?;";
        
        try {
            PreparedStatement psStudentQuery = con.prepareStatement(studentsQuery);
            psStudentQuery.setInt(1, grade);
            ResultSet rs = psStudentQuery.executeQuery();
            while(rs.next()){
                monthList.add(new Monthly(rs.getInt("id"),rs.getInt("grade"),rs.getString("fn"),rs.getString("mn"),rs.getString("ln"),
                        rs.getString("gender"),rs.getString("sec"),rs.getFloat("remaining"),rs.getBoolean("sept"),rs.getBoolean("oct"),
                        rs.getBoolean("nov"),rs.getBoolean("dece"),rs.getBoolean("jan"),rs.getBoolean("feb"),rs.getBoolean("mar"),
                        rs.getBoolean("apr"),rs.getBoolean("may"),rs.getBoolean("jun"),rs.getBoolean("jul"),rs.getBoolean("aug")));
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
        tvStudents.setItems(monthList);
    }
    
    //root border pane
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
        
        btnBack.setOnAction(e -> {
            cmMaster.switchView(primaryStage, "StudentsList");
        });
        
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
        AnchorPane.setBottomAnchor(tvStudents, 25.00);
        //add the columns to the table view
        tvStudents.getColumns().addAll(tcId,tcFirstName,tcMiddleName,tcLastName,tcGrade,tcSection,tcRemaining);
        
        //populate the table
        populateStudentsTable(1);
        
        //create monthly payments table container
        VBox vbMonthlyStatus = new VBox(10);
        vbMonthlyStatus.setPrefWidth(310.00);
        vbMonthlyStatus.setStyle("-fx-background-color: white; -fx-padding:10px;");
        vbMonthlyStatus.setAlignment(Pos.TOP_CENTER);
        
        AnchorPane.setRightAnchor(vbMonthlyStatus, 15.00);
        AnchorPane.setTopAnchor(vbMonthlyStatus, 15.00);
        
        //create the labels for monthly payment stat
        Label lblMonthlyStat = new Label("Monthly Payment Stat.");
        lblMonthlyStat.getStyleClass().add("header-lbl-md");
        
        
        //month buttons
        ArrayList<Button> alMonthButtons = new ArrayList<>();
        String[] stMonthList = {"sept","oct","nov","dece","jan","feb","mar","apr","may","jun","jul","aug"};
        //add a button for each month from the list
        for (String month : stMonthList) {
            Button btnMonth = new Button(month);
            //toggle the payment status on button click
            btnMonth.setOnAction(e -> {
                changePaymentStat(((Button)e.getSource()).getText());
                setButtonStat(alMonthButtons);
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
        
        //create an array list to store the buttons for the grade selectors and loop through them to save repetition of code
        ArrayList<Button> alGradeButtons = new ArrayList<>();
        for(int i=1; i<13; i++){
            final int grade = i;
            Button btnGrade = new Button("G-"+i);
            btnGrade.setOnAction(e ->{
                populateStudentsTable(grade);
            });
            alGradeButtons.add(btnGrade);
        }
        alGradeButtons.forEach((btn)-> {
            btn.getStyleClass().add("gButton");
        });
        hbBottom.getChildren().addAll(alGradeButtons);
        
        // *********************************************************** //
        
        //listen to table row click 
        tvStudents.getSelectionModel().selectedItemProperty().addListener(obs -> {
            //change the status of payment and color of the buttons to indicate the payment status for that month
            if(tvStudents.getSelectionModel().getSelectedItem() != null){
                curMonthly = (Monthly) tvStudents.getSelectionModel().getSelectedItem();
                setButtonStat(alMonthButtons);
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

    //method to change status of monthly payment
    private void changePaymentStat(String month) {
        String sql = "UPDATE monthly set "+month+" = !"+month+" WHERE sId = ?";
        
        try {
            PreparedStatement ps = new Database().createCon().prepareStatement(sql);
            
            ps.setInt(1, curMonthly.getId());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ManagePayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //sets the background of the buttons accordingly to the payment stat
    private void setButtonStat(ArrayList<Button> alMonthButtons) {
        String sql = "SELECT * FROM monthly WHERE sId = ?";
        
        try {
            PreparedStatement ps = new Database().createCon().prepareStatement(sql);
            ps.setInt(1, curMonthly.getId());
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()){
                alMonthButtons.forEach((btn)-> {
//                    if(curMonthly.setApr()){
                        
//                    }
                    try {
                        if(rs.getBoolean(btn.getText()) == true){
                            btn.setStyle("-fx-background-color:green; -fx-text-fill: white");
                        } else {
                            btn.setStyle("-fx-background-color:red; -fx-text-fill: white");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ManagePayment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManagePayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
