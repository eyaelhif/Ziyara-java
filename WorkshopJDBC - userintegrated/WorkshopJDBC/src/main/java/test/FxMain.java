package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxMain extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //front
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionVisite/Front/ListeVisiteUserFXML.fxml"));
        //back
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/AdminDashboard.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/login.fxml"));
//hater
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        stage.setTitle("Ziyara ");
        stage.setScene(scene);

        stage.show();

    }
}
