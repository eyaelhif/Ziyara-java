package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/showRtransport.fxml"));
        // Load FXML code into a scene
        Parent root = loader.load();
        // Put the FXML file in a scene
        Scene scene = new Scene(root);
        // Set the scene in the stage
        stage.setScene(scene);
        stage.setTitle("Add Transport Form");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
