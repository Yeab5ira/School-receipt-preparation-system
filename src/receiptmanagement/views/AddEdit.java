package receiptmanagement.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.*;

import static javafx.geometry.Pos.BOTTOM_CENTER;
import static javafx.geometry.Pos.CENTER;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.WHITE;
import services.CustomMethods;
public class AddEdit {
    public BorderPane AddEdit(){
        
        CustomMethods cmMaster = new CustomMethods();
        
        //create the navigation bar
        AnchorPane apNavbar = new AnchorPane();
        apNavbar.setId("navbar");
        
        //create the navigation bar buttons
        Button btnBack = cmMaster.faButton("CHEVRON_LEFT","1em","WHITE","Back");
        //assign class name to the buttons
        btnBack.getStyleClass().add("btn-primary");
        
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
        Label grade = new Label("Grade");
        grade.setFont(Font.font("Calibri",FontWeight.BOLD, FontPosture.REGULAR,20));
        TextField fNameText = new TextField();
        fNameText.setMaxHeight(40);
        fNameText.setMaxWidth(200.00);

        TextField midNameText = new TextField();
        midNameText.setEffect(null);
        midNameText.setMaxHeight(40);
        midNameText.setMaxWidth(200.00);

        TextField lNameText = new TextField();
        lNameText.setEffect(null);
        lNameText.setMaxHeight(40);
        lNameText.setMaxWidth(200.00);
        
        Label lblGrade = new Label("Grade");
        lblGrade.setFont(Font.font("Calibri",FontWeight.BOLD, FontPosture.REGULAR,20));
        MenuButton mbGrade = new MenuButton();
        mbGrade.setPrefWidth(150.00);
        TextField tfSec = new TextField();
        tfSec.setPrefWidth(50.00);
        for(int i=1; i<13; i++){
            MenuItem miGrade = new MenuItem(i+"");
            mbGrade.getItems().addAll(miGrade);
        }
        HBox gradeContainer = new HBox(5);
        gradeContainer.getChildren().addAll(mbGrade,tfSec);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(20.00);
        gridPane.setPrefWidth(400.00);
        gridPane.add(fName,0,0);
        gridPane.add(fNameText,1,0);
        gridPane.add(midName,0,1);
        gridPane.add(midNameText,1,1);
        gridPane.add(lName,0,2);
        gridPane.add(lNameText,1,2);
        gridPane.add(lblGrade,0,3);
        gridPane.add(gradeContainer, 1, 3);

        VBox gridContainer = new VBox();
        gridContainer.getChildren().addAll(gridPane,new Separator());
        gridContainer.setSpacing(40);

        Button addBtn = new Button("Add");
        addBtn.setTextFill(WHITE);
        addBtn.setStyle("-fx-background-color: #3c11a8");
        addBtn.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
        addBtn.setMaxWidth(250);
        addBtn.setMaxHeight(20);
        addBtn.setAlignment(CENTER);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setTextFill(WHITE);
        cancelBtn.setMaxWidth(250);
        cancelBtn.setMaxHeight(20);
        cancelBtn.setStyle("-fx-background-color: #eda621");
        cancelBtn.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));

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

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(apNavbar);
        borderPane.setCenter(centerContainer);
        return borderPane;

    }

}
