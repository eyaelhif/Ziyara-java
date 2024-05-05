package controllers;

import controllers.User.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.User;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class BackController {



    @FXML
    private Button btnTransport;

    @FXML
    private Button btnRTransport;



    @FXML
    private Label emailLabel;



    @FXML
    private Button btnUtilisateur;




    private Connection connection;

    User currentUser = LoginController.getCurrentUser();


    @FXML
    private void initialize() {

        btnUtilisateur.setOnAction(this::handleBtnUtilisateur);
    }



    private void closeOpenStages() {
        // Get all open stages
        List<Stage> openStages = new ArrayList<>();
        for (Window window : Window.getWindows()) {
            if (window instanceof Stage) {
                openStages.add((Stage) window);
            }
        }

        // Close each open stage
        for (Stage stage : openStages) {
            stage.close();
        }
    }



    private void handleBtnUtilisateur(ActionEvent actionEvent) {
        System.out.println("Redirecting to gestion utilisateur");
        loadScene("/Back/User/showUser.fxml");
    }

    public void transport(ActionEvent actionEvent) {
        System.out.println("Redirecting to gestion transport");
        loadScene("/Back/Transport/Showtransport.fxml");

    }

    public void Rtransport(ActionEvent actionEvent) {
        System.out.println("Redirecting to reservation transport");
        loadScene("/Back/Transport/ShowRtrans.fxml");
    }




    // Load the desired FXML file and show it in a new stage
    private void loadScene(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) btnUtilisateur.getScene().getWindow(); // Get the current stage
            stage.setTitle("Ziyara");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void logout(ActionEvent actionEvent) {
        System.out.println("Logout");
        closeOpenStages();

        // Clear the current user session
        currentUser = null;
    }

    public void displayEmail(String email) {
        emailLabel.setText("Welcome: " + email);
    }


    @FXML
    public void goToVisites() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ListeVisiteFXML.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void goToReservationVisites() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ListeReservationVisiteFXML.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
