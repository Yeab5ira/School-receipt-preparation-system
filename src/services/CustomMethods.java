package services;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CustomMethods {
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
    
    //This is a custom table item header
    public AnchorPane hbTableHeader(){
        HBox hbTableItem = new HBox(10);
        AnchorPane apTableItemContainer = new AnchorPane();
        HBox.setHgrow(hbTableItem, Priority.ALWAYS);
        hbTableItem.setPrefHeight(50.00);
        apTableItemContainer.setStyle("-fx-background-color:#333333; -fx-font-weight: bolder; -fx-font-size:15px;");
        
        Label lblId = new Label("ID");
        lblId.setStyle("-fx-text-fill: white");
        lblId.setPrefWidth(75.00);
        Label lblFullName = new Label("PAYER FULL NAME");
        lblFullName.setStyle("-fx-text-fill: white");
        lblFullName.setPrefWidth(300.00);
        Label lblGrade = new Label("GRADE");
        lblGrade.setStyle("-fx-text-fill: white");
        lblGrade.setPrefWidth(60.00);
        Label lblRemaining = new Label("REMAINING");
        lblRemaining.setStyle("-fx-text-fill: white");
        lblRemaining.setPrefWidth(90.00);
        Label lblAmount = new Label("AMOUNT");
        lblAmount.setStyle("-fx-text-fill: white");
        lblAmount.setPrefWidth(75.00);
        Label lblReason = new Label("REASON");
        lblReason.setStyle("-fx-text-fill: white");
        lblRemaining.setMaxWidth(500.00);
        
        AnchorPane.setLeftAnchor(hbTableItem, 10.00);
        
        apTableItemContainer.getChildren().addAll(hbTableItem);
        hbTableItem.getChildren().addAll(lblId,new Separator(Orientation.VERTICAL),lblFullName,new Separator(Orientation.VERTICAL),lblGrade,new Separator(Orientation.VERTICAL),lblRemaining,new Separator(Orientation.VERTICAL),lblAmount,new Separator(Orientation.VERTICAL),lblReason);
        hbTableItem.setAlignment(Pos.CENTER_LEFT);
        hbTableItem.setPadding(new Insets(10.00));
        
        return apTableItemContainer;
    }
    //This is a custom table item
    public AnchorPane hbTableItem(String Id, String FullName, String Grade, String Remaining, String Serial, String Amount, String Reason){
        HBox hbTableItem = new HBox(10);
        AnchorPane apTableItemContainer = new AnchorPane();
        HBox.setHgrow(hbTableItem, Priority.ALWAYS);
        hbTableItem.setPrefHeight(50.00);
        apTableItemContainer.setStyle("-fx-background-color:white");
        
        Label lblId = new Label(Id);
        lblId.setPrefWidth(75.00);
        Label lblFullName = new Label(FullName);
        lblFullName.setPrefWidth(300.00);
        Label lblGrade = new Label(Grade);
        lblGrade.setPrefWidth(60.00);
        Label lblRemaining = new Label(Remaining);
        lblRemaining.setPrefWidth(90.00);
        Label lblAmount = new Label(Amount);
        lblAmount.setPrefWidth(75.00);
        Label lblReason = new Label(Reason);
        lblRemaining.setMaxWidth(500.00);
        Button btnPrint = faButton("PRINT", "1em", "BLACK", "");
        Button btnDisable = faButton("MINUS_CIRCLE", "1em", "RED", "");
        
        AnchorPane.setLeftAnchor(hbTableItem, 10.00);
        AnchorPane.setRightAnchor(btnPrint, 10.00);
        AnchorPane.setBottomAnchor(btnPrint, 5.00);
        AnchorPane.setTopAnchor(btnPrint, 5.00);
        AnchorPane.setRightAnchor(btnDisable, 50.00);
        AnchorPane.setBottomAnchor(btnDisable, 5.00);
        AnchorPane.setTopAnchor(btnDisable, 5.00);
        
        apTableItemContainer.getChildren().addAll(hbTableItem,btnPrint,btnDisable);
        hbTableItem.getChildren().addAll(lblId,new Separator(Orientation.VERTICAL),lblFullName,new Separator(Orientation.VERTICAL),lblGrade,new Separator(Orientation.VERTICAL),lblRemaining,new Separator(Orientation.VERTICAL),lblAmount,new Separator(Orientation.VERTICAL),lblReason);
        hbTableItem.setAlignment(Pos.CENTER_LEFT);
        hbTableItem.setPadding(new Insets(10.00));
        
        return apTableItemContainer;
    }
    
}
