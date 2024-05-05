package controllers.Visite;

import models.Visite;
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
import services.VisiteService;

import java.io.IOException;
import java.util.List;

public class ListeVisiteUserfController {

    @FXML
    private GridPane grid;

    @FXML
    private TextField search;

    @FXML
    void searchVisites() {
        updateVisites(search.getText());
    }
/*
    @FXML
    void Myres(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/MyReservation.fxml"));
            Parent root = loader.load();
            MyReservationController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    public void initialize() {
        updateVisites("");
    }

    private void updateVisites(String filter) {
        VisiteService visiteService = new VisiteService();
            List<Visite> filteredVisites = visiteService.searchByTitrefavoris(filter);

        grid.getChildren().clear();

        int columnIndex = 0;
        int rowIndex = 1;

        for (Visite visite : filteredVisites) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Visite/CardVisiteFXML.fxml"));
                Node cardVisiteNode = loader.load();

                CardVisiteController cardController = loader.getController();
                cardController.displayVisiteData(visite);

                grid.add(cardVisiteNode, columnIndex, rowIndex);

                columnIndex++;
                if (columnIndex == 2) {
                    columnIndex = 0;
                    ++rowIndex;
                }
                GridPane.setMargin(cardVisiteNode, new Insets(30.0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void Myres(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the reservation list view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Visite/ListeReservationVisiteUserFXML.fxml"));
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


    public void gotoback(ActionEvent actionEvent) {
        try {
        // Load the FXML file for the reservation list view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ListeVisiteFXML.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);


        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();


        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }


    public void listevisite(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the reservation list view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Visite/ListeVisiteUserFXML.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);


            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();


            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToNavigate(ActionEvent actionEvent) {
    }
}
