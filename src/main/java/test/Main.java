package test;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.sql.SQLException;

import static javafx.application.Application.launch;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Charger le fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/ziyarae/Menu.fxml"));

        // Définir le contenu de la scène
        Scene scene = new Scene(root);

        // Définir le titre de la fenêtre
        primaryStage.setTitle("Gestion des Voitures");

        // Définir la scène dans la fenêtre principale
        primaryStage.setScene(scene);

        // Afficher la fenêtre
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
