package test;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.sql.SQLException;

import static javafx.application.Application.launch;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/ziyarae/Menu.fxml"));


        Scene scene = new Scene(root);
        primaryStage.setTitle("ziyara");

        // Définir la scène dans la fenêtre principale
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
