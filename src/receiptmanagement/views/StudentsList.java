package receiptmanagement.views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.stage.Stage;
import receiptmanagement.models.Student;
import receiptmanagement.ReceiptManagement;
import receiptmanagement.models.Payment;
import receiptmanagement.services.CustomMethods;
import receiptmanagement.services.Database;
import receiptmanagement.services.StudentServices;

public final class StudentsList {
    
    //create instance of custom methods 
    CustomMethods cmMaster = new CustomMethods();
    //create instance of services
    StudentServices studentService = new StudentServices();
    
    //Define the table view for students list
    TableView tvStudents = new TableView();
    
    //make the table columns for the table view
    TableColumn tcId = new TableColumn("ID");
    TableColumn tcFirstName = new TableColumn("First Name");
    TableColumn tcMiddleName = new TableColumn("Middle Name");
    TableColumn tcLastName = new TableColumn("Last Name");
    TableColumn tcGender = new TableColumn("Gender");
    TableColumn tcGrade = new TableColumn("Grade");
    TableColumn tcSection= new TableColumn("Section");
    TableColumn tcRemaining = new TableColumn("Remaining");
    
    
    //create a new table view for previous payments
    TableView tvPreviousPayments = new TableView();
    TableColumn tcPrevSerial = new TableColumn("Serial");
    TableColumn tcPrevReason = new TableColumn("Reason");
    
    //create the labels for student info card
    Label lblStudentInfoTitle = new Label("Student Info.");
    Label lblFullNameTitle = new Label("Full Name");
    Label lblFullName = new Label("Full Name");
    Label lblGradeTitle = new Label("Grade");
    Label lblGrade = new Label("Grade");
    
    
    //set the student info card with info got from click listener
    public void setSelectedStudent(Student student){
        lblFullName.setText(student.getFn()+" "+student.getMn()+" "+student.getLn());
        lblGrade.setText(student.getGrade()+student.getSec());
    }
    
    //populate the table with the selected grade    
    public void populateStudentsTable(int grade) {
        //create a list of students
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        
        Connection con = new Database().createCon();
        String studentsQuery = "SELECT * FROM students WHERE grade=?";
        
        try {
            PreparedStatement psStudentQuery = con.prepareStatement(studentsQuery);
            psStudentQuery.setInt(1, grade);
            ResultSet rs = psStudentQuery.executeQuery();
            //loop through the result set and add a student model to the student list
            while(rs.next()){
                studentList.add(new Student(rs.getInt("id"),rs.getInt("grade"),rs.getString("fn"),rs.getString("mn"),rs.getString("ln"),rs.getString("gender"),rs.getString("sec"),rs.getFloat("remaining"),rs.getBoolean("left_school")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //set the cellValueFactory of each column
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("fn"));
        tcMiddleName.setCellValueFactory(new PropertyValueFactory<>("mn"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("ln"));
        tcGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tcGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        tcSection.setCellValueFactory(new PropertyValueFactory<>("sec"));
        tcRemaining.setCellValueFactory(new PropertyValueFactory<>("remaining"));
        //set the contents of the students table with the students List
        tvStudents.setItems(studentList);
    }
    
    //populate the table with the selected grade    
    public void populatePaymentsTable(int id) {
        ObservableList<Payment> paymentList = FXCollections.observableArrayList();
        Database dbController = new Database();
        
        Connection con = dbController.createCon();
        String studentsQuery = "SELECT * FROM payments WHERE sId=?";
        
        try {
            PreparedStatement psStudentQuery = con.prepareStatement(studentsQuery);
            psStudentQuery.setInt(1, id);
            ResultSet rs = psStudentQuery.executeQuery();
            while(rs.next()){
                paymentList.add(new Payment(rs.getString("payer"), rs.getString("reason"),rs.getString("cashier"), rs.getString("sec"), rs.getInt("grade"), rs.getInt("sId"), rs.getInt("serial"),  rs.getFloat("amount"), rs.getBoolean("printed"), rs.getBoolean("disabled"), rs.getDate("date") ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        tcPrevSerial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        tcPrevReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        
        tvPreviousPayments.setItems(paymentList);
    }
    
    // create root borderpane
    BorderPane root = new BorderPane();
    
    public StudentsList(Stage primaryStage){
        
        //create the navigation bar
        AnchorPane apNavbar = new AnchorPane();
        apNavbar.setId("navbar");
        HBox hbNavbuttonContainer = new HBox(15);
        
        //create the navigation bar buttons
        Button btnSignout = cmMaster.faButton("SIGN_OUT","1em","WHITE","Signout");
        Button btnReport = cmMaster.faButton("FILE","1em","WHITE","Report");
        Button btnReceipt = cmMaster.faButton("PRINT","1em","WHITE","Prepare Receipt");
        Button btnAddStudent = cmMaster.faButton("PLUS","1em","WHITE","Add Student");
        Button btnExport = cmMaster.faButton("SAVE","1em","WHITE","Export");
        Button btnImport = cmMaster.faButton("DOWNLOAD", "1em", "WHITE", "Import");
        //assign class name to the buttons
        btnSignout.getStyleClass().add("btn-primary");
        btnExport.getStyleClass().add("btn-primary");
        btnReceipt.getStyleClass().add("btn-primary");
        btnReceipt.setDisable(true);
        btnAddStudent.getStyleClass().add("btn-primary");
        btnReport.getStyleClass().add("btn-primary");
        btnImport.getStyleClass().add("btn-primary");
        
        //add on action functions
        btnSignout.setOnAction((ActionEvent event)->{
            primaryStage.close();
        });
        btnReport.setOnAction((ActionEvent event)->{
            cmMaster.switchView(primaryStage, "PaymentReport");
        });
        btnReceipt.setOnAction((ActionEvent event)->{
            cmMaster.switchView(primaryStage, "ReceiptVoucher", (Student) tvStudents.getSelectionModel().getSelectedItem());
        });
        btnAddStudent.setOnAction((ActionEvent event)->{
            cmMaster.switchView(primaryStage, "Add");
        });
        btnExport.setOnAction((ActionEvent event)->{
            cmMaster.switchView(primaryStage, "Export");
        });
        btnImport.setOnAction((ActionEvent event)->{
            cmMaster.switchView(primaryStage, "ImportStatement");
        });
        
        //add the navigation buttons to their container
        hbNavbuttonContainer.getChildren().addAll(btnSignout,btnImport,btnReport,btnReceipt,btnAddStudent,btnExport);
        
        //align the signout button and the other navigation buttons to opposite ends
        AnchorPane.setRightAnchor(hbNavbuttonContainer, 0.00);
        AnchorPane.setTopAnchor(hbNavbuttonContainer, 0.00);
        AnchorPane.setBottomAnchor(hbNavbuttonContainer, 0.00);
        AnchorPane.setLeftAnchor(btnSignout,0.00);
        AnchorPane.setTopAnchor(btnSignout, 0.00);
        AnchorPane.setBottomAnchor(btnSignout, 0.00);
        
        //add the navigation bar nodes to the navbar Anchorpane we created
        apNavbar.getChildren().addAll(btnSignout,hbNavbuttonContainer);
        
        
        // *************************** Body ************************************ //
        
        //Create the body
        AnchorPane apBody = new AnchorPane();
        apBody.setId("body");
        
        //make the table change sizes on different screens
        AnchorPane.setLeftAnchor(tvStudents, 10.00);
        AnchorPane.setRightAnchor(tvStudents, 350.00);
        AnchorPane.setTopAnchor(tvStudents, 10.00);
        AnchorPane.setBottomAnchor(tvStudents, 0.00);
        
        //add the columns to the table view
        tvStudents.getColumns().addAll(tcId,tcFirstName,tcMiddleName,tcLastName,tcGender,tcGrade,tcSection,tcRemaining);
        
        //populate the table
        populateStudentsTable(1);
        
        //make the student info card
        VBox vbStudentInfo = new VBox(10);
        vbStudentInfo.setAlignment(Pos.CENTER);
        vbStudentInfo.setPrefWidth(250.00);
        vbStudentInfo.setPrefHeight(200.00);
        vbStudentInfo.setId("studentInfoContainer");
        
        //add class name to the label for styling
        lblStudentInfoTitle.getStyleClass().add("header-lbl-md");
        lblFullNameTitle.getStyleClass().add("light-lbl-md");
        lblGradeTitle.getStyleClass().add("light-lbl-md");
        lblFullName.getStyleClass().add("bold-lbl-lg");
        lblGrade.getStyleClass().add("bold-lbl-lg");
        
        //create vboxes to contain the labels
        VBox vbFullnameContainer = new VBox(2);
        VBox vbGradeContainer = new VBox(2);
        
        //add the labels to the containers
        vbFullnameContainer.getChildren().addAll(lblFullNameTitle,new Separator(),lblFullName);
        vbGradeContainer.getChildren().addAll(lblGradeTitle, new Separator(), lblGrade);
        
        //add the labels to studentinfo card
        vbStudentInfo.getChildren().addAll(lblStudentInfoTitle, new Separator(),vbFullnameContainer,vbGradeContainer);
        
        //create edit and delete buttons
        VBox vbModifyButtons = new VBox(0);
        Button btnEdit = cmMaster.faButton("EDIT","2em","WHITE","");
        btnEdit.setPrefHeight(60.00);
        btnEdit.setPrefWidth(60.00);
        btnEdit.getStyleClass().add("edit-btn");
        Button btnDelete = cmMaster.faButton("TRASH", "2em", "WHITE","");
        btnDelete.setPrefHeight(60.00);
        btnDelete.setPrefWidth(60.00);
        btnDelete.getStyleClass().add("delete-btn");
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        vbModifyButtons.getChildren().addAll(btnEdit,btnDelete);
        
        //add on click actions for edit and delete button
        btnEdit.setOnAction(e -> {
            Student st = (Student) tvStudents.getSelectionModel().getSelectedItem();
            primaryStage.getScene().setRoot(new AddEdit(primaryStage).edit(primaryStage,st));
        });
        btnDelete.setOnAction(e -> {
            Student st = (Student) tvStudents.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Do You Want To Delete This Student?");

            Optional<ButtonType> result = alert.showAndWait();
            //check what the result of the alert is 
            if (result.get() == ButtonType.OK){
                studentService.deleteStudent(st.getId()); 
                tvStudents.getItems().remove(tvStudents.getSelectionModel().getSelectedItem());
            }
            
        });
        
        //anchor the studentinfo card to make it responsive on different sized screens
        AnchorPane.setRightAnchor(vbStudentInfo, 75.00);
        AnchorPane.setTopAnchor(vbStudentInfo, 10.00);
        
        AnchorPane.setTopAnchor(vbModifyButtons, 10.00);
        AnchorPane.setRightAnchor(vbModifyButtons, 15.00);
        
        //create previous payments table container
        VBox vbPreviousPayments = new VBox(10);
        vbPreviousPayments.setPrefWidth(310.00);
        VBox.setVgrow(vbPreviousPayments, Priority.ALWAYS);
        vbPreviousPayments.setStyle("-fx-background-color: white");
        vbPreviousPayments.setAlignment(Pos.TOP_CENTER);
        
        AnchorPane.setRightAnchor(vbPreviousPayments, 15.00);
        AnchorPane.setTopAnchor(vbPreviousPayments, 225.00);
        AnchorPane.setBottomAnchor(vbPreviousPayments, 0.00);
        
        //add columns to the previous payments table
        tvPreviousPayments.getColumns().addAll(tcPrevSerial,tcPrevReason);
        
        Label lblPrevHeader = new Label("Previous Payments");
        lblPrevHeader.getStyleClass().add("header-lbl-md");
        vbPreviousPayments.getChildren().addAll(lblPrevHeader, new Separator(),tvPreviousPayments);
        
        //add nodes to the body container
        apBody.getChildren().addAll(tvStudents,vbStudentInfo,vbModifyButtons, vbPreviousPayments);
        
        // ****************************** footer for grade buttons *************************************** //
        
        //create an hbox for the bottom grade buttons container
        HBox hbBottom = new HBox(15);
        hbBottom.setPadding(new Insets(10));
        
        //create an array list to store the buttons for the grade selectors and loop through them to save repetition of code
        ArrayList<Button> alGradeButtons = new ArrayList<>();
        for(int i=1; i<13; i++){
            alGradeButtons.add(new Button("G-"+i));
        }
        alGradeButtons.forEach((btn)-> {
            btn.getStyleClass().add("gButton");
            btn.setOnAction(e->{
                //what this does is split the text of the button based on the regex "-" and select the second part of the array that holds the grade number
                populateStudentsTable(Integer.parseInt(btn.getText().split("-")[1]));
            }); 
        });
        hbBottom.getChildren().addAll(alGradeButtons);
        
        // *********************************************************** //
        
        //listen to table row click 
        tvStudents.getSelectionModel().selectedItemProperty().addListener(obs -> {
            if(tvStudents.getSelectionModel().getSelectedItem() != null){
                //get the selected student
                Student st = (Student) tvStudents.getSelectionModel().getSelectedItem();
                setSelectedStudent(st);
                btnEdit.setDisable(false);
                btnDelete.setDisable(false);
                btnReceipt.setDisable(false);
                //populate the payments made by the seleced student
                populatePaymentsTable(st.getId());
                
            } else { //disable the edit and delete buttons if no student is selected
                btnEdit.setDisable(true);
                btnDelete.setDisable(true);
                btnReceipt.setDisable(true);
            }
        });

        //add the nodes to the root
        root.setTop(apNavbar);
        root.setCenter(apBody);
        root.setBottom(hbBottom);
    }
    
    public BorderPane getStudentsListView(){
        return this.root;
    }

}
