package controllers;

import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Transport;
import services.TransportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AddTransportController {

    @FXML
    private Button addTransportBtn;

    @FXML
    private TextField typeField;

    @FXML
    private DatePicker dateField;


    @FXML
    private TextField prixField;

    @FXML
    private TextField descriptionField;

    private final TransportService transportService = new TransportService();

    @FXML
    private TextField imagePathField;
    @FXML
    public void addTransport(ActionEvent event) {
        // Input validation
        String typeText = typeField.getText().trim();
        if (!isValidNumericInput(typeText)) {
            showAlert("Type doit être un chiffre!");
            return;
        }
        int type = Integer.parseInt(typeText);

        // Date parsing
        Date date = java.sql.Date.valueOf(dateField.getValue());

        String prixText = prixField.getText().trim();
        if (!isValidNumericInput(prixText)) {
            showAlert("Prix doit être positif!");
            return;
        }
        double prix = Double.parseDouble(prixText);

        String description = descriptionField.getText().trim();

        // Create a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Get the selected image file path
            String imagePath = selectedFile.getAbsolutePath();

            // Update the image path field
            imagePathField.setText(imagePath);

            // Load the selected image into the ImageView
            ImageView imageView = new ImageView();
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(100); // Adjust the width of the image as needed
            imageView.setPreserveRatio(true);

            // Update any UI components to display the selected image (if needed)
            // For example, you can add the imageView to a container in your UI
        } else {
            showAlert("Veuillez sélectionner une image !");
            return;
        }

        // Validate fields
        if (description.isEmpty() || imagePathField.getText().isEmpty()) {
            showAlert("Veuillez remplir tous les champs !");
            return;
        }

        // Create a new Transport object
        Transport newTransport = new Transport();
        newTransport.setTypeTransport(type);
        newTransport.setDateTransport(date);
        newTransport.setPrixTransport(prix);
        newTransport.setDescription(description);
        newTransport.setImageTransport(imagePathField.getText()); // Set image path

        // Use TransportService to add the new transport to the database
        try {
            transportService.create(newTransport);
            showAlert("Transport added successfully!");

            // Get the stage associated with the addTransportBtn button and close it
            Stage stage = (Stage) addTransportBtn.getScene().getWindow();
            stage.close();

            // You may implement further actions here, like refreshing the view
        } catch (SQLException e) {
            showAlert("Failed to add transport: " + e.getMessage());
        }
    }


    // Validate if input is a numeric value
    private boolean isValidNumericInput(String input) {
        return input.matches("\\d+");
    }

    // Helper method to show alerts
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
