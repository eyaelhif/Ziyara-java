package controllers;

import Entities.Visite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CardVisiteController {

    @FXML
    private ImageView imageColumn;

    @FXML
    private Button addres;

    @FXML
    private Button Share;

    @FXML
    private Label titre;

    @FXML
    private Label description_visite;

    @FXML
    private Label date_visite;

    @FXML
    private Label prix;

    @FXML
    private Label imeagv;
    private int vid;

    public void displayVisiteData(Visite visite) {
        titre.setText(visite.getTitre());
        description_visite.setText(visite.getDescription_visite());
        date_visite.setText(visite.getDate_visite().toString()); // Assuming getDate_visite() returns a LocalDate
        prix.setText(String.valueOf(visite.getPrix()));
        this.vid=visite.getId();

        String imagePath = visite.getImagev();

        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageColumn.setImage(image);
            } else {
                System.out.println("L'image n'existe pas : " + imagePath);
            }
        } else {
            System.out.println("Le chemin de l'image est vide.");
        }
    }

    public void addres(ActionEvent actionEvent) {
        // Assuming you have the visiteid available, replace "yourVisiteId" with the actual visiteid value


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutReservationUserFXML.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            AddReservationUserController controller = loader.getController();

            // Pass the visiteId to the controller
            controller.setVisiteId(vid);

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
