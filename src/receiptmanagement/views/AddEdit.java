package receiptmanagement.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import static javafx.geometry.Pos.BOTTOM_CENTER;
import static javafx.geometry.Pos.CENTER;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.WHITE;
import javafx.stage.Stage;
import receiptmanagement.models.Student;
import receiptmanagement.services.CustomMethods;
import receiptmanagement.services.StudentServices;

public class AddEdit {
    //instantiate the custom methods
    CustomMethods cmMaster = new CustomMethods();
    
    StudentServices studentService = new StudentServices();
    
    BorderPane root = new BorderPane();
    
    
    TextField fNameText = new TextField();
    TextField midNameText = new TextField();
    TextField lNameText = new TextField();
    ComboBox cobGrade = new ComboBox();
    TextField tfSec = new TextField();
    ComboBox cobGender = new ComboBox();
    
    Button addBtn = new Button("Save");
    
    public AddEdit(Stage primaryStage){
        //create the navigation bar
        AnchorPane apNavbar = new AnchorPane();
        apNavbar.setId("navbar");
        
        //create the navigation bar buttons
        Button btnBack = cmMaster.faButton("CHEVRON_LEFT","1em","WHITE","Back");
        //assign class name to the buttons
        btnBack.getStyleClass().add("btn-primary");
        
        btnBack.setOnAction(e -> {
            cmMaster.switchView(primaryStage, "StudentsList");
        });
        
        //add the navigation bar nodes to the navbar Anchorpane we created
        apNavbar.getChildren().addAll(btnBack);
        
        //THE BODY
        //
        Text text1 = new Text("Add/Edit Student Information");
        text1.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.REGULAR,25));
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setFill(ORANGE);

        VBox titleContainer = new VBox(10);
        titleContainer.getChildren().addAll(text1,new Separator());

        Label fName = new Label("First Name");
        fName.setFont(Font.font("Calibri",FontWeight.BOLD, FontPosture.REGULAR,20));
        fName.setPrefWidth(250.00);
        Label midName = new Label("Middle Name");
        midName.setFont(Font.font("Calibri",FontWeight.BOLD, FontPosture.REGULAR,20));
        midName.setPrefWidth(250.00);
        Label lName = new Label("Last Name");
        lName.setFont(Font.font("Calibri",FontWeight.BOLD, FontPosture.REGULAR,20));
        lName.setPrefWidth(250.00);
        Label gender = new Label("Gender");
        gender.setFont(Font.font("Calibri",FontWeight.BOLD, FontPosture.REGULAR,20));
        Label grade = new Label("Grade");
        grade.setFont(Font.font("Calibri",FontWeight.BOLD, FontPosture.REGULAR,20));
        
        fNameText.setMaxHeight(40);
        fNameText.setMaxWidth(200.00);

        
        midNameText.setEffect(null);
        midNameText.setMaxHeight(40);
        midNameText.setMaxWidth(200.00);

        lNameText.setEffect(null);
        lNameText.setMaxHeight(40);
        lNameText.setMaxWidth(200.00);
        
        cobGender.getItems().addAll("F","M");
        
        Label lblGrade = new Label("Grade");
        lblGrade.setFont(Font.font("Calibri",FontWeight.BOLD, FontPosture.REGULAR,20));
        
        cobGrade.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        cobGrade.setPrefWidth(150.00);
        
        tfSec.setPrefWidth(50.00);
        
        HBox gradeContainer = new HBox(5);
        gradeContainer.getChildren().addAll(cobGrade,tfSec);

        //add the user inputs to the gridpane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(20.00);
        gridPane.setPrefWidth(400.00);
        gridPane.add(fName,0,0);
        gridPane.add(fNameText,1,0);
        gridPane.add(midName,0,1);
        gridPane.add(midNameText,1,1);
        gridPane.add(lName,0,2);
        gridPane.add(lNameText,1,2);
        gridPane.add(gender, 0, 3);
        gridPane.add(cobGender, 1,3);
        gridPane.add(lblGrade,0,4);
        gridPane.add(gradeContainer, 1, 4);

        VBox gridContainer = new VBox();
        gridContainer.getChildren().addAll(gridPane,new Separator());
        gridContainer.setSpacing(40);

        
        addBtn.setTextFill(WHITE);
        addBtn.setStyle("-fx-background-color: #3c11a8");
        addBtn.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
        addBtn.setMaxWidth(250);
        addBtn.setMaxHeight(20);
        addBtn.setAlignment(CENTER);
        
        // add btn on action function
        addBtn.setOnAction(e->{
            //Call the add student function 
            int Result = studentService.addStudent(fNameText.getText(),midNameText.getText(),lNameText.getText(),cobGender.getValue().toString(),Integer.parseInt(cobGrade.getValue().toString()),tfSec.getText());
            //switch after adding the student
            cmMaster.switchView(primaryStage, "StudentsList");
            
            //TODO:
            //add input validation and show add status
        });
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setTextFill(WHITE);
        cancelBtn.setMaxWidth(250);
        cancelBtn.setMaxHeight(20);
        cancelBtn.setStyle("-fx-background-color: #eda621");
        cancelBtn.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));

        //cancel btn on action function
        cancelBtn.setOnAction(e->{
            cmMaster.switchView(primaryStage, "StudentsList");
        });
        
        VBox buttonContainer = new VBox();
        buttonContainer.getChildren().addAll(addBtn,cancelBtn);
        buttonContainer.setAlignment(BOTTOM_CENTER);
        buttonContainer.setSpacing(30);

        VBox centerContainer = new VBox();
        centerContainer.setSpacing(20);
        centerContainer.getChildren().addAll(titleContainer,gridContainer,buttonContainer);
        centerContainer.setStyle("-fx-background-color: #ffffff");
        centerContainer.setMaxHeight(550);
        centerContainer.setMaxWidth(500);
        centerContainer.setPadding(new Insets(50,50,50,50));

        root.setTop(apNavbar);
        root.setCenter(centerContainer);
    };
    public BorderPane AddEdit(){
        return root;
    }
    public BorderPane edit(Stage primaryStage, Student student){
        fNameText.setText(student.getFn());
        midNameText.setText(student.getMn());
        lNameText.setText(student.getLn());
        cobGender.setValue(student.getGender());
        cobGrade.setValue(student.getGrade());
        tfSec.setText(student.getSec());
        //change the action of the save button for the edit
        addBtn.setOnAction(e->{
            studentService.editStudent(student.getId(),fNameText.getText(), midNameText.getText(), lNameText.getText(), cobGender.getValue().toString(), Integer.parseInt(cobGrade.getValue().toString()), tfSec.getText());
            cmMaster.switchView(primaryStage, "StudentsView");
        });
        return root;
    }

}
