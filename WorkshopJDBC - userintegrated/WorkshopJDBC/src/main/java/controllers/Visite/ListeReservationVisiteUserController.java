package controllers.Visite;


import models.ReservationVisite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import services.ReservationVisiteService;

import java.io.IOException;
import java.util.List;

public class ListeReservationVisiteUserController {

    @FXML
    private GridPane grid;

    @FXML
    private TextField search;

    @FXML
    void searchReservations() {
        updateReservations(search.getText());
    }

    public void initialize() {
        updateReservations("");
    }

    private void updateReservations(String filter) {
        ReservationVisiteService reservationService = new ReservationVisiteService();
        List<ReservationVisite> filteredReservations = reservationService.searchByTitre("");

        grid.getChildren().clear();

        int columnIndex = 0;
        int rowIndex = 1;

        for (ReservationVisite reservation : filteredReservations) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Visite/CardReservationVisiteFXML.fxml"));
                Node cardReservationNode = loader.load();

                CardReservationController cardController = loader.getController();
               // cardController.initialize();
                cardController.displayReservationData(reservation);

                grid.add(cardReservationNode, columnIndex, rowIndex);

                columnIndex++;
                if (columnIndex == 2) {
                    columnIndex = 0;
                    ++rowIndex;
                }
                GridPane.setMargin(cardReservationNode, new Insets(30.0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void goToMyVisistes(ActionEvent actionEvent) {

        try {
            // Load the FXML file for the reservation list view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Visite/ListeVisiteUserFXML.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            //ListeReservationUserController controller = loader.getController();

            // Create a new scene with the loaded FXML root
            Scene scene = new Scene(root);

            // Get the stage from the action event
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene to the stage and show it
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToNavigate(ActionEvent actionEvent) {
    }
}
