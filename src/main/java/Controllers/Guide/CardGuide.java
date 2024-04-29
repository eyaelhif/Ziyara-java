package Controllers.Guide;

import Controllers.Guide.EditGuide;
import entities.Guide;
import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceGuide;
import test.MainFX;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class CardGuide {

    @FXML
    private Button btnDeleteGuide, btnModifierGuide;
    @FXML
    private AnchorPane cardPane;
    @FXML
    private Label disponibilite, langue, nomGuide, prenomGuide;
    @FXML
    private ImageView imageGuide;

    private Guide currentGuide;
    private AffichageGuide affichageGuideController;

    public void setAffichageGuideController(AffichageGuide controller) {
        this.affichageGuideController = controller;
    }

    @FXML
    public void initialize() {
        // Initialization logic, if needed
    }

    public void setGuide(Guide guide) {
        this.currentGuide = guide;
        nomGuide.setText(guide.getNomGuide());
        prenomGuide.setText(guide.getPrenomGuide());
        langue.setText(guide.getLangue());
        disponibilite.setText(guide.getDisponibilite());

        // Load the guide's image if available
        try {
            String imagePath = "/images/" + guide.getImageGuide();
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream != null) {
                Image image = new Image(imageStream);
                imageGuide.setImage(image);
            } else {
                // Consider setting a default image if none found
                imageGuide.setImage(new Image("/images/activitieicon.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleDeleteGuideAction(ActionEvent event) {
        try {
            ServiceGuide serviceGuide = new ServiceGuide();
            serviceGuide.supprimer(currentGuide); // Delete the
            System.out.println("guide deleted successfully");

            // Refresh the guide view
            if (affichageGuideController != null) {
                affichageGuideController.refreshGuidesView();
                System.out.println("Refresh method called");
            } else {
                System.out.println("affichageGuideController is null");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }
    @FXML
    private void handleEditAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Guide/EditGuide.fxml"));
            Parent root = loader.load();
            EditGuide controller = loader.getController();
            controller.setGuide(this.currentGuide);
            MainFX.setCenterView(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleReserveButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Guide/AjoutReservation.fxml")); // Ensure correct path
            Parent root = loader.load();
            AjoutReservation controller = loader.getController();
            controller.setGuideId(currentGuide.getId());

            Stage stage = new Stage();
            stage.setTitle("Make a Reservation");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
