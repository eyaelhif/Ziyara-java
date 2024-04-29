package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class Dashboard {

    @FXML
    private BorderPane mainContainer;

    @FXML
    public void  listeReservationsShow() {
        try {
            Node listRS = FXMLLoader.load(getClass().getResource("/Front/Guide/AfficherReservations.fxml"));
            mainContainer.setCenter(listRS);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }
    @FXML
    public void  listeGuidesShow() {
        try {
            Node listRS = FXMLLoader.load(getClass().getResource("/Back/Guide/affichageGuide.fxml"));
            mainContainer.setCenter(listRS);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }


    @FXML
    public void AjoutGuideDisplay() {
        try {
            Node displayQuestsAdd = FXMLLoader.load(getClass().getResource("/Back/Guide/ajoutGuide.fxml"));
            mainContainer.setCenter(displayQuestsAdd);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }

    @FXML
    public void showHome() {
        try {
            Node home = FXMLLoader.load(getClass().getResource("/Back/HomeView.fxml"));
            mainContainer.setCenter(home);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }

    @FXML
    private void initialize() {
        showHome();
    }
}
