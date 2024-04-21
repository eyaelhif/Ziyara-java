package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Transport;
import services.TransportService;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateTransportController {

    @FXML
    private TextField transportIdField;

    @FXML
    private TextField typeField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button imagePathField;
    @FXML
    private ImageView imageView;
    private Transport selectedTransport;

    private ShowTransportController showTransportController;

    public void setSelectedTransport(Transport transport) {
        this.selectedTransport = transport;
        transportIdField.setText(String.valueOf(transport.getIdTransport()));
        typeField.setText(String.valueOf(transport.getTypeTransport()));
        descriptionField.setText(transport.getDescription());
        imagePathField.setText(transport.getImageTransport());

        displayExistingImage();


    }

    public void setShowTransportController(ShowTransportController showTransportController) {
        this.showTransportController = showTransportController;
    }

    private void displayExistingImage() {
        String existingImagePath = selectedTransport.getImageTransport();

        if (existingImagePath != null && !existingImagePath.isEmpty()) {
            // Load the existing image into the ImageView
            Image existingImage = new Image(new File(existingImagePath).toURI().toString());
            imageView.setImage(existingImage);
            imageView.setFitWidth(100); // Adjust the width of the image as needed
            imageView.setPreserveRatio(true);
        }
    }
    @FXML
    public void updateImage() {
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
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(100); // Adjust the width of the image as needed
            imageView.setPreserveRatio(true);

            // Update any UI components to display the selected image (if needed)
            // For example, you can add the imageView to a container in your UI
        } else {
            showAlert("Veuillez sélectionner une image !");
        }
    }

    @FXML
    public void updateTransport(ActionEvent event) {
        // Call the updateImage method to select and handle the image update

        if (imagePathField.getText().isEmpty()) {
            showAlert("Veuillez sélectionner une image !");
            return;
        }

        try {
            // Input validation
            String type = typeField.getText().trim();
            LocalDate date = dateField.getValue(); // Get the selected date from the DatePicker
            String prix = prixField.getText().trim();
            String description = descriptionField.getText().trim();

            // Validate fields
            if (type.isEmpty() || date == null || prix.isEmpty() || description.isEmpty()) {
                showAlert("Please fill in all required fields!");
                return;
            }

            // Use TransportService to update the transport in the database
            selectedTransport.setTypeTransport(Integer.parseInt(type));
            selectedTransport.setDateTransport(Date.valueOf(date)); // Convert LocalDate to java.sql.Date
            selectedTransport.setPrixTransport(Double.parseDouble(prix));
            selectedTransport.setDescription(description);
            selectedTransport.setImageTransport(imagePathField.getText()); // Set image path

            // Use TransportService to update the transport in the database
            TransportService transportService = new TransportService();
            transportService.update(selectedTransport);

            // Show success message
            showAlert("Transport updated successfully!");

            // Close the window
            Stage stage = (Stage) typeField.getScene().getWindow();
            stage.close();

            // Refresh the table view if showTransportController is not null
            if (showTransportController != null) {
                showTransportController.loadData();
            } else {
                System.out.println("showTransportController is null");
            }
        } catch (SQLException e) {
            showAlert("Failed to update transport: " + e.getMessage());
        }
    }



    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
