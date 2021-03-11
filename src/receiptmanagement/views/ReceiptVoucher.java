package receiptmanagement.views;


import java.util.ArrayList;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import receiptmanagement.models.Student;
import receiptmanagement.services.CustomMethods;
import receiptmanagement.services.PaymentServices;

public class ReceiptVoucher {
    
    //create the root borderpane
    BorderPane root = new BorderPane();
    
    CustomMethods cmMaster = new CustomMethods();
    PaymentServices paymentService = new PaymentServices();
    
    TextField tfFrom = new TextField();
    ComboBox cobGrade = new ComboBox();
    TextField tfSec = new TextField();
    
    public ReceiptVoucher(Stage primaryStage, Student student){
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
        
        
        //************************* body ----------------------------------
        
        VBox vbBodyContainer = new VBox(5);
        vbBodyContainer.setAlignment(Pos.CENTER);
        Label lblReceiptHeader = new Label("CASH RECEIPT VOUCHER");
        lblReceiptHeader.setStyle("-fx-font-size: 5em; -fx-font-weight: bolder; -fx-text-fill: #aaaaaa");
        
        //receipt inputs container
        VBox vbInputsContainer = new VBox(10.00);
        vbInputsContainer.getStyleClass().add("inputs-container");
        //date input
        HBox hbDateContainer = new HBox(5);
        Label lblDate = new Label("Date");
        DatePicker dpDate = new DatePicker();
        hbDateContainer.getChildren().addAll(lblDate,dpDate);
        hbDateContainer.setAlignment(Pos.CENTER_RIGHT);
        //from input
        Label lblFrom = new Label("Received From");
        //sum input
        Label lblSum = new Label("The Sum Of Birr");
        TextField tfSum = new TextField();
        //for input
        Label lblFor = new Label("For");
        TextField tfFor = new TextField();
        //monthly payment input
        Label lblMonthly = new Label("Monthly Payment");
        FlowPane fpMonthsContainer = new FlowPane(Orientation.HORIZONTAL);
        fpMonthsContainer.setPrefWidth(800.00);
        //month buttons
        ArrayList<CheckBox> alMonthButtons = new ArrayList<>();
        String[] stMonthList = {"sept","oct","nov","dece","jan","feb","mar","apr","may","jun","jul","aug"};
        LinkedList<String> stSelectedMonthList = new LinkedList<>();
        for (String month : stMonthList) {
            CheckBox cbMonth = new CheckBox(month);
            cbMonth.setOnAction(e -> {
                if(cbMonth.isSelected()){
                    if(!tfFor.getText().isEmpty()){
                        tfFor.setText(tfFor.getText()+", "+cbMonth.getText());
                    } else {
                        tfFor.setText(tfFor.getText()+""+cbMonth.getText());
                    }
                    stSelectedMonthList.push(cbMonth.getText());
                } else {
                    tfFor.setText(tfFor.getText().replace(", "+cbMonth.getText(),""));
                    tfFor.setText(tfFor.getText().replace(cbMonth.getText()+", ", ""));
                    tfFor.setText(tfFor.getText().replace(cbMonth.getText(), ""));
                    stSelectedMonthList.remove(cbMonth.getText());
                }
            });
            cbMonth.setPadding(new Insets(10,10,10,10));
            alMonthButtons.add(cbMonth);
        }
        fpMonthsContainer.getChildren().addAll(alMonthButtons);
        
        //grades list
        Label lblGrade = new Label("Grade");
        cobGrade.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        HBox hbGrade = new HBox(3);
        hbGrade.getChildren().addAll(cobGrade,tfSec);
        
        //due, extra input
        HBox hbDueExtra = new HBox(15);
        RadioButton rbDue = new RadioButton("Due");
        RadioButton rbPayed = new RadioButton("Extra");
        TextField tfDueExtra = new TextField();
        hbDueExtra.getChildren().addAll(rbDue,rbPayed,tfDueExtra);
        hbDueExtra.setAlignment(Pos.CENTER_RIGHT);
        
        //add from, sum, and for inputs to grid
        GridPane gpInputsContainer = new GridPane();
        gpInputsContainer.setVgap(15.00);
        //constraints to set the width of the columns
        ColumnConstraints cc = new ColumnConstraints();
        ColumnConstraints cc2 = new ColumnConstraints();
        cc.setMinWidth(150.00);
        cc2.setHgrow(Priority.ALWAYS);
        gpInputsContainer.getColumnConstraints().addAll(cc,cc2);
        gpInputsContainer.addRow(0, lblFrom, tfFrom);
        gpInputsContainer.addRow(1, lblSum, tfSum);
        gpInputsContainer.addRow(2, lblFor, tfFor);
        gpInputsContainer.addRow(3, lblMonthly, fpMonthsContainer);
        gpInputsContainer.addRow(4, lblGrade, hbGrade);
        
        //Submit Buton
        HBox hbSubmitContainer = new HBox();
        Button btnSubmit = new Button("Submit");
        hbSubmitContainer.setAlignment(Pos.CENTER_RIGHT);
        hbSubmitContainer.getChildren().addAll(btnSubmit);
        btnSubmit.getStyleClass().add("import-btn");
        
        //submit button action
        btnSubmit.setOnAction(e->{
            //TODO: setup cashier name later
            paymentService.prepareReceipt(student.getId(),tfFrom.getText(),Integer.parseInt(cobGrade.getValue().toString()), tfSec.getText(),dpDate.getValue(),tfFor.getText(),Float.parseFloat(tfSum.getText()),"cashier");
            paymentService.setMonthlyPaymentStat(student.getId(),stSelectedMonthList);
        });

        //add inputs to the container
        vbInputsContainer.getChildren().addAll(hbDateContainer,gpInputsContainer,hbDueExtra);
        vbBodyContainer.getChildren().addAll(lblReceiptHeader,vbInputsContainer,hbSubmitContainer);
        
        //set the payer name and grade 
        tfFrom.setText(student.getFn()+" "+student.getMn()+" "+ student.getLn());
        cobGrade.setValue(student.getGrade());
        tfSec.setText(student.getSec());
    
        //add the nodes to the root
        root.setTop(apNavbar);
        root.setCenter(vbBodyContainer);
    }
    
    
    public BorderPane getReceiptVoucherView(){
        return root;
    }
}
