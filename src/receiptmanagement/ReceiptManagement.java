package receiptmanagement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import receiptmanagement.views.StudentsList;

public class ReceiptManagement extends Application{
    @Override
    public void start(Stage primaryStage){
        
        //create the main scene
        Scene scnMain = new Scene(new StudentsList(primaryStage).getStudentsListView());
        
        scnMain.getStylesheets().add("receiptmanagement/css/styles.css");
        
        primaryStage.setMinWidth(800.00);
        primaryStage.setMinHeight(600.00);
        primaryStage.setScene(scnMain);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Receipt Preparation System");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
