package receiptmanagement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import receiptmanagement.views.StudentsList;

public class ReceiptManagement extends Application{
    @Override
    public void start(Stage primaryStage){
        
        //Scenes
        //create your own assigned scenes in a new class and return the root pane to set it in the scene. see one of the views I already made as an example
        Scene scnStudentsList = new Scene(new StudentsList(primaryStage).getStudentsListView(), 1000,800);
        
        
        //set the scene and stage size
        scnStudentsList.getStylesheets().add("receiptmanagement/css/styles.css");
        primaryStage.setScene(scnStudentsList);
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
