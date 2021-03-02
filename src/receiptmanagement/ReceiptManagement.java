package receiptmanagement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import receiptmanagement.views.AddEdit;
import receiptmanagement.views.ImportStatement;
import receiptmanagement.views.ManagePayment;
import receiptmanagement.views.PaymentReport;
import receiptmanagement.views.ReceiptVoucher;
import receiptmanagement.views.StudentsList;

public class ReceiptManagement extends Application{
    @Override
    public void start(Stage primaryStage){
        
        //Scenes
        //create your own assigned scenes in a new class and return the root pane to set it in the scene. see one of the views I already made as an example
        Scene scnStudentsList = new Scene(new StudentsList(primaryStage).getStudentsListView(), 1000,800);
        Scene scnAddEdit = new Scene(new AddEdit().AddEdit());
        Scene scnImportStatement =  new Scene(new ImportStatement(primaryStage).getImportStatementView());
        Scene scnManagePayment = new Scene(new ManagePayment(primaryStage).getManagePaymentView());
        Scene scnPaymentReport = new Scene(new PaymentReport(primaryStage).getPaymentReportView());
        Scene scnReceiptVoucher = new Scene(new ReceiptVoucher(primaryStage).getReceiptVoucherView());
        
        //set the scene and stage size
        scnStudentsList.getStylesheets().add("receiptmanagement/css/styles.css");
        scnAddEdit.getStylesheets().add("receiptmanagement/css/styles.css");
        scnImportStatement.getStylesheets().add("receiptmanagement/css/styles.css");
        scnManagePayment.getStylesheets().add("receiptmanagement/css/styles.css");
        scnPaymentReport.getStylesheets().add("receiptmanagement/css/styles.css");
        scnReceiptVoucher.getStylesheets().add("receiptmanagement/css/styles.css");
        
        primaryStage.setScene(scnReceiptVoucher);
        primaryStage.setMinWidth(800.00);
        primaryStage.setMinHeight(600.00);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Receipt Preparation System");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
