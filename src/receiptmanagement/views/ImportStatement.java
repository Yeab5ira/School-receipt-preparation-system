package receiptmanagement.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import receiptmanagement.models.Payment;
import receiptmanagement.services.CustomMethods;
import receiptmanagement.services.PaymentServices;

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
    TableColumn tcDate = new TableColumn("Date");
    TableColumn tcReason = new TableColumn("Reason");
    TableColumn tcStat = new TableColumn("Stat");
    
    //populate the table with the list of payment
    public void populatePaymentsTable(File file){
        Date curDate = new Date();
        SimpleDateFormat curDateFormat = new SimpleDateFormat("mm/DD/yyyy");
        ObservableList<Payment> paymentList = FXCollections.observableArrayList();
        
        
        try {
            //read the csv file
            Scanner sc = new Scanner(file);
            while(sc.hasNext()){
                String payment = sc.nextLine();
                //split the fields in the row
                //trim the strings to remove leading or trailing whitespaces
                String[] paymentFields = payment.split(",");
                String payer = paymentFields[1].trim() + " " + paymentFields[2].trim() + " " + paymentFields[3].trim();
                int grade  = Integer.parseInt(paymentFields[4].trim());
                String sec = paymentFields[5].trim();
                float amount = Float.parseFloat(paymentFields[6].trim());
                String reason = payment.substring(payment.lastIndexOf(",")+1);
                String currentDate = paymentFields[7].trim();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                
                
                try {
                    //replace the curDate with the date we get from the csv file, if this fails then it will use the current date.
                    curDate = formatter.parse(currentDate);
                } catch (ParseException ex) {
                    Logger.getLogger(ImportStatement.class.getName()).log(Level.SEVERE, null, ex);
                }
                Payment paymentObj = new Payment(payer,reason , "cashier", sec, grade, -1, 1, amount, false, false,curDate);
                //Get the payerId if the student exists
                int payerId = cmMaster.getPayerId(paymentObj);
                //check if the student exists before showing it in the table.
//                if(cmMaster.checkStudentExist(paymentObj)){
//                    paymentList.add(paymentObj);
//                }
                //if the payerId returned is -1, it means the student doesn't exist;
                if(payerId!=-1){
                    paymentObj.setPayerId(payerId);
                }
                paymentList.add(paymentObj);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tcId.setCellValueFactory(new PropertyValueFactory<>("payerId"));
        tcPayer.setCellValueFactory(new PropertyValueFactory<>("payer"));
        tcGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        tcSection.setCellValueFactory(new PropertyValueFactory<>("sec"));
        tcAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        
        tvPayments.setItems(paymentList);
        
        tcStat.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Payment, String>, TableCell<Payment, String>> cellFactory
                = 
                (final TableColumn<Payment, String> param) -> {
                    final TableCell<Payment, String> cell = new TableCell<Payment, String>() {
                        
                        final Button btn = new Button("Remove");
                        @Override
                        public void updateItem(String item, boolean empty) {
                            btn.setStyle("-fx-background-color: red; -fx-text-fill:white");
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                Payment payment = getTableView().getItems().get(getIndex());
                                if(payment.getPayerId() == -1){
                                    setGraphic(btn);
                                    setText(null);
                                }
                                btn.setOnAction(event -> {
                                    //remove the entry from the table
                                    tvPayments.getItems().remove(tvPayments.getItems().get(getIndex()));
                                });
                            }
                        }
                    };
                    return cell;
        };

        tcStat.setCellFactory(cellFactory);
        
    }
    //root container 
    BorderPane root = new BorderPane();
    
    public ImportStatement(Stage primaryStage) {
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
        
        
        Button btnDelete = cmMaster.faButton("TRASH", "1em", "WHITE", "Remove From Table");
        btnDelete.getStyleClass().add("btn-primary");
        
        btnDelete.setOnAction(e-> {
            //remove the item from the table
            tvPayments.getItems().remove(tvPayments.getSelectionModel().getSelectedIndex());
        });
        
        AnchorPane.setRightAnchor(btnDelete, 0.00);
        
        //add the navigation bar nodes to the navbar Anchorpane we created
        apNavbar.getChildren().addAll(btnBack,btnDelete);
        
        
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
        //file chooser
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
        btnFilePicker.setOnAction(e -> {
            fileStatement = fcStatementFile.showOpenDialog(primaryStage);
            //assign the label with the file name if a file is selected
            if(fileStatement != null){
                lblFileName.setText(fileStatement.getName());
            }
            
//            TODO: check the csv format and validate it 
            
        });
        
        //call te populate method
        btnPreview.setOnAction(e ->{
            if(fileStatement != null){
                populatePaymentsTable(fileStatement);
//                tvPayments.getItems().
            }
        });
        
        btnImport.setOnAction(e->{
            if(fileStatement != null){
//                tvPayments.getItems().clear();
//                populatePaymentsTable(fileStatement);
                ObservableList<Payment> pm = tvPayments.getItems();
                pm.forEach(payment ->{
                    
                    PaymentServices paymentService = new PaymentServices();
                    //prepare the receipot if the student exists
                    if(cmMaster.checkStudentExist(payment)){
                        //call the prepare receipt method
                        paymentService.prepareReceipt(payment.getPayerId(), payment.getPayer(), payment.getGrade(), payment.getSec(), payment.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), payment.getReason(),
                        payment.getAmount(), payment.getCashier());
                    }
                    
                    String[] stMonthList = {"sept","oct","nov","dece","jan","feb","mar","apr","may","jun","jul","aug"};
                    LinkedList<String> stSelectedMonthList = new LinkedList<>();
                    for (String month : stMonthList) {
                        if(payment.getReason().contains(month)){
                            stSelectedMonthList.push(month);
                        }
                    }
                    paymentService.setMonthlyPaymentStat(payment.getPayerId(),stSelectedMonthList);
//                    cmMaster.checkStudentExist(payment);
                });
                
            }
        });
        
        //align import vbox
        AnchorPane.setTopAnchor(vbImportStatement, 10.00);
        AnchorPane.setLeftAnchor(vbImportStatement, 10.00);
        
        //add nodes to the import vbox
        vbImportStatement.getChildren().addAll(lblImportBankStatement,new Separator(),lblFile,hbFileChooserContainer,new Separator(), btnPreview, btnImport);

        //add the columns to the table view
        tvPayments.getColumns().addAll(tcId,tcPayer,tcGrade,tcSection,tcAmount,tcDate,tcReason,tcStat);
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
