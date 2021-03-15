package receiptmanagement.services;

import com.mysql.jdbc.Statement;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import receiptmanagement.models.Payment;
import receiptmanagement.models.Student;
import receiptmanagement.views.AddEdit;
import receiptmanagement.views.ImportStatement;
import receiptmanagement.views.ManagePayment;
import receiptmanagement.views.PaymentReport;
import receiptmanagement.views.ReceiptVoucher;
import receiptmanagement.views.StudentsList;

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
        Label lblDate = new Label("DATE");
        lblDate.setStyle("-fx-text-fill: white");
        lblDate.setPrefWidth(75.00);
        Label lblReason = new Label("REASON");
        lblReason.setStyle("-fx-text-fill: white");
        lblRemaining.setMaxWidth(500.00);
        
        AnchorPane.setLeftAnchor(hbTableItem, 10.00);
        
        apTableItemContainer.getChildren().addAll(hbTableItem);
        hbTableItem.getChildren().addAll(lblId,new Separator(Orientation.VERTICAL),lblFullName,new Separator(Orientation.VERTICAL),lblGrade,new Separator(Orientation.VERTICAL),lblAmount,new Separator(Orientation.VERTICAL),lblDate,new Separator(Orientation.VERTICAL),lblReason);
        hbTableItem.setAlignment(Pos.CENTER_LEFT);
        hbTableItem.setPadding(new Insets(10.00));
        
        return apTableItemContainer;
    }
    //This is a custom table item
    public AnchorPane hbTableItem(String Id, String FullName, String Grade, String Remaining, String Serial, String Amount, String Date, String Reason,Boolean disabled){
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
        Label lblAmount = new Label(Amount);
        lblAmount.setPrefWidth(75.00);
        Label lblDate = new Label(Date);
        lblDate.setPrefWidth(75.00);
        Label lblReason = new Label(Reason);
        Button btnPrint = faButton("PRINT", "1em", "BLACK", "");
        Button btnDisable = faButton("MINUS_CIRCLE", "1em", "RED", "");
        
        //set the print and void buttons to the right
        AnchorPane.setLeftAnchor(hbTableItem, 10.00);
        AnchorPane.setRightAnchor(btnPrint, 10.00);
        AnchorPane.setBottomAnchor(btnPrint, 5.00);
        AnchorPane.setTopAnchor(btnPrint, 5.00);
        AnchorPane.setRightAnchor(btnDisable, 50.00);
        AnchorPane.setBottomAnchor(btnDisable, 5.00);
        AnchorPane.setTopAnchor(btnDisable, 5.00);
        
        //add the nodes to their container
        apTableItemContainer.getChildren().addAll(hbTableItem,btnPrint,btnDisable);
        hbTableItem.getChildren().addAll(lblId,new Separator(Orientation.VERTICAL),lblFullName,new Separator(Orientation.VERTICAL),lblGrade,new Separator(Orientation.VERTICAL),lblAmount,new Separator(Orientation.VERTICAL),lblDate,new Separator(Orientation.VERTICAL),lblReason);
        hbTableItem.setAlignment(Pos.CENTER_LEFT);
        hbTableItem.setPadding(new Insets(10.00));
        
        //used to store the void status of a receipt 
        boolean isVoid = disabled;
        
        //change background color based on the void status
        if(isVoid){
            apTableItemContainer.setStyle("-fx-background-color: red");
        } else {
            apTableItemContainer.setStyle("-fx-background-color: white");
        }
        System.out.println(isVoid);
        
        
        //void button action
        btnDisable.setOnAction(e->{
            //check the background color of the table item and change it accordingly 
            if(apTableItemContainer.getBackground().getFills().get(0).getFill().equals(Color.RED)){
                apTableItemContainer.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
            } else {
                apTableItemContainer.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,Insets.EMPTY)));
            }
            //call the void receipt method
            new PaymentServices().disableReceipt(Serial);
        });
        
        //TODO:
        //add the print method
        
        return apTableItemContainer;
    }
    
    //switch views
    public void switchView(Stage primaryStage, String viewName){
        Scene scnMain = new Scene(new StudentsList(primaryStage).getStudentsListView());
        scnMain.getStylesheets().add("receiptmanagement/css/styles.css");
        //check what view is sent 
        //change the root of the primaryStage with the appropriate view
        switch(viewName) {
            case "StudentsList": 
                primaryStage.getScene().setRoot(new StudentsList(primaryStage).getStudentsListView());
                break;
            case "Add" :
                primaryStage.getScene().setRoot(new AddEdit(primaryStage).AddEdit());
                break;
            case "ImportStatement" :
                primaryStage.getScene().setRoot(new ImportStatement(primaryStage).getImportStatementView());
                break;
            case "ManagePayment" :
                primaryStage.getScene().setRoot(new ManagePayment(primaryStage).getManagePaymentView());
                break;
            case "PaymentReport" :
                primaryStage.getScene().setRoot(new PaymentReport(primaryStage).getPaymentReportView());
                break;
            default : 
                primaryStage.getScene().setRoot(new StudentsList(primaryStage).getStudentsListView());
                break;
                
        }
        
    }
    //switch view function with added student parameter to send the student data accross views
    public void switchView(Stage primaryStage, String viewName, Student student){
        Scene scnMain = new Scene(new StudentsList(primaryStage).getStudentsListView());
        scnMain.getStylesheets().add("receiptmanagement/css/styles.css");
        
        switch(viewName) {
            case "ReceiptVoucher" :
                primaryStage.getScene().setRoot(new ReceiptVoucher(primaryStage,student).getReceiptVoucherView());
                break;
            default : 
                primaryStage.getScene().setRoot(new StudentsList(primaryStage).getStudentsListView());
                break;
                
        }
        
    }
    
    //check if the student info sent is correct in the database
    public boolean checkStudentExist(Payment payment){
        String[] names = payment.getPayer().split(" ");
        int grade = payment.getGrade();
        String sec = payment.getSec();
        
        String sql = "SELECT * FROM students WHERE fn=? AND mn=? AND ln=? AND grade=? AND sec=?";
        Connection con = new Database().createCon();
        try {
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1, names[0].trim());
            ps.setString(2, names[1].trim());
            ps.setString(3, names[2].trim());
            ps.setInt(4, grade);
            ps.setString(5, sec.trim());
            
            ResultSet rs = ps.executeQuery();
            //return true or false depending on the query result
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getPayerId(Payment payment) {
        String[] names = payment.getPayer().split(" ");
        int grade = payment.getGrade();
        String sec = payment.getSec();
        
        String sql = "SELECT * FROM students WHERE fn=? AND mn=? AND ln=? AND grade=? AND sec=?";
        Connection con = new Database().createCon();
        try {
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1, names[0].trim());
            ps.setString(2, names[1].trim());
            ps.setString(3, names[2].trim());
            ps.setInt(4, grade);
            ps.setString(5, sec.trim());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                return rs.getInt("id");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return -1 if the student doesn't exist
        return -1;
    }
    
}
