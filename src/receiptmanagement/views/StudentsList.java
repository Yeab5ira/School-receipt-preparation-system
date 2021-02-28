package receiptmanagement.views;

import de.jensd.fx.glyphs.GlyphIconName;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Student;
import receiptmanagement.ReceiptManagement;
import services.Database;

public final class StudentsList {
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
    
    //create the labels for student info card
    Label lblStudentInfoTitle = new Label("Student Info.");
    Label lblFullNameTitle = new Label("Full Name");
    Label lblFullName = new Label("Full Name");
    Label lblGradeTitle = new Label("Grade");
    Label lblGrade = new Label("Grade");
    
    //This is a custom function to return a button with font awesome icon
    public Button faButton(String name, String size, String color, String text){
        Button btn = new Button(text);
        FontAwesomeIcon fa = new FontAwesomeIcon();
        fa.setIconName(name);
        fa.setGlyphStyle("-fx-fill: "+color);
        fa.setSize(size);
        btn.setGraphic(fa);
        
        return btn;
    }
    
    //set the student info card with info got from click listener
    public void setSelectedStudent(Student student){
        lblFullName.setText(student.getFn()+" "+student.getMn()+" "+student.getLn());
        lblGrade.setText(student.getGrade()+student.getSec());
    }
    
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
        
        tvStudents.setItems(studentList);
    }
    BorderPane root = new BorderPane();
    public StudentsList(Stage primaryStage){
        
        //create the navigation bar
        AnchorPane apNavbar = new AnchorPane();
        apNavbar.setId("navbar");
        HBox hbNavbuttonContainer = new HBox(15);
        
        //create the navigation bar buttons
        Button btnSignout = faButton("SIGN_OUT","1em","WHITE","Signout");
        Button btnReport = faButton("FILE","1em","WHITE","Report");
        Button btnReceipt = faButton("PRINT","1em","WHITE","Prepare Receipt");
        Button btnAddStudent = faButton("PLUS","1em","WHITE","Add Student");
        Button btnExport = faButton("SAVE","1em","WHITE","Export");
        //assign class name to the buttons
        btnSignout.getStyleClass().add("btn-primary");
        btnExport.getStyleClass().add("btn-primary");
        btnReceipt.getStyleClass().add("btn-primary");
        btnAddStudent.getStyleClass().add("btn-primary");
        btnReport.getStyleClass().add("btn-primary");
        
        
        //add the navigation buttons to their container
        hbNavbuttonContainer.getChildren().addAll(btnSignout,btnReport,btnReceipt,btnAddStudent,btnExport);
        
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
        
        
        //add the columns to the table view
        tvStudents.getColumns().addAll(tcId,tcFirstName,tcMiddleName,tcLastName,tcGrade,tcSection,tcRemaining);
        
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
        Button btnEdit = faButton("EDIT","2em","WHITE","");
        btnEdit.setPrefHeight(60.00);
        btnEdit.setPrefWidth(60.00);
        btnEdit.getStyleClass().add("edit-btn");
        Button btnDelete = faButton("TRASH", "2em", "WHITE","");
        btnDelete.setPrefHeight(60.00);
        btnDelete.setPrefWidth(60.00);
        btnDelete.getStyleClass().add("delete-btn");
        vbModifyButtons.getChildren().addAll(btnEdit,btnDelete);
        
        //anchor the studentinfo card to make it responsive on different sized screens
        AnchorPane.setRightAnchor(vbStudentInfo, 75.00);
        AnchorPane.setTopAnchor(vbStudentInfo, 10.00);
        
        AnchorPane.setTopAnchor(vbModifyButtons, 10.00);
        AnchorPane.setRightAnchor(vbModifyButtons, 15.00);
        
        //create previous payments table container
        VBox vbPreviousPayments = new VBox(10);
        vbPreviousPayments.setPrefWidth(310.00);
        vbPreviousPayments.setPrefHeight(200.00);
        vbPreviousPayments.setStyle("-fx-background-color: white");
        vbPreviousPayments.setAlignment(Pos.TOP_CENTER);
        
        AnchorPane.setRightAnchor(vbPreviousPayments, 15.00);
        AnchorPane.setTopAnchor(vbPreviousPayments, 225.00);
        //create a new table view for previous payments
        TableView tvPreviousPayments = new TableView();
        TableColumn tcPrevSerial = new TableColumn("Serial");
        TableColumn tcPrevReason = new TableColumn("Reason");
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
        });
        hbBottom.getChildren().addAll(alGradeButtons);
        
        // *********************************************************** //
        
        //resize the tables according to the stage size
        primaryStage.heightProperty().addListener((obs)->{
            tvStudents.setPrefHeight(primaryStage.getHeight()- 200.00);
            vbPreviousPayments.setPrefHeight(primaryStage.getHeight()-420.00);
        });
        
        //listen to table row click 
        tvStudents.getSelectionModel().selectedItemProperty().addListener((ObservableValue observableValue, Object oldValue, Object newValue) -> {
            if(tvStudents.getSelectionModel().getSelectedItem() != null){
                Student st = (Student) tvStudents.getSelectionModel().getSelectedItem();
                setSelectedStudent(st);
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
