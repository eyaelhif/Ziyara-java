package controllers;

import Entities.Visite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.io.File;
import java.net.MalformedURLException;

public class ModifVisiteController {

    @FXML
    private TextField visitNameField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField priceField;

    @FXML
    private ImageView imageView;
    private String imageURL;
    private Visite visite;
    private int idvisite;
    private String url;

    @FXML
    void handleModifVisite(ActionEvent event) {
        // Handle the modification of the visit here
        String visitName = visitNameField.getText();
        String description = descriptionArea.getText();
        String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "";
        String price = priceField.getText();

        // Form validation
        if (visitName.isEmpty() || description.isEmpty() || date.isEmpty() || price.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Check if the image URL is empty, retain the existing imageURL
        if (imageURL == null || imageURL.isEmpty()) {
            imageURL = url; // Assuming existingImageURL is already set
        }

        // Validate visitName and description
        if (!visitName.matches("[a-zA-Z]+")) {
            showAlert("Le titre ne doit contenir que des lettres.");
            return;
        }

        if (!description.matches("[a-zA-Z]+")) {
            showAlert("La description ne doit contenir que des lettres.");
            return;
        }

        // Validate price
        double prix;
        try {
            prix = Double.parseDouble(price);
            if (prix <= 0) {
                showAlert("Le prix doit être un nombre positif.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Le prix doit être un nombre valide.");
            return;
        }

        // Implement the logic to update the visit in the database
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish the database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ziyarajava", "root", "");

            // Prepare the SQL statement for updating the visit
            String updateQuery = "UPDATE visite SET titre = ?, description_visite = ?, date_visite = ?, prix = ?, imagev = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, visitName);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, price);
            preparedStatement.setString(5, imageURL);
            preparedStatement.setInt(6, idvisite);

            // Execute the update statement
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Visit updated successfully!");

                // Load the ListeVisiteFXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeVisiteFXML.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                // Get the Stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Failed to update visit.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            // Close resources in the finally block
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    void importerImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            imageURL = selectedFile.getAbsolutePath();
            Image image = new Image("file:" + selectedFile.getAbsolutePath());
            imageView.setImage(image);
        }
    }
    public void initData(Visite visite) throws MalformedURLException {
        this.visite = visite;
        this.idvisite = visite.getId();
        // Populate the fields with the data of the selected visit
        visitNameField.setText(visite.getTitre());
        descriptionArea.setText(visite.getDescription_visite());
        datePicker.setValue(visite.getDate_visite());
        priceField.setText(String.valueOf(visite.getPrix()));
        url = visite.getImagev();
        File file = new File(visite.getImagev());
        String imageUrl = file.toURI().toURL().toString();

// Create the Image object using the imageUrl
        Image image = new Image(imageUrl);
        imageView.setImage(image);
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Système de gestion des visites");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleVisitesButtonClicked(ActionEvent event) {
        try {
            // Load the Visite list FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeVisiteFXML.fxml"));
            Parent root = loader.load();
            // Display the Visite list scene
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleReservationVisitesButtonClicked(ActionEvent event) {
        try {
            // Load the Reservation-Visite list FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeReservationVisiteFXML.fxml"));
            Parent root = loader.load();
            // Display the Reservation-Visite list scene
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

