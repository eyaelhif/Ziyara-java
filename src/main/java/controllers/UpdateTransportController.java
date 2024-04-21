package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.CategorieTransport;
import models.Transport;
import services.CategorieService;
import services.TransportService;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.sql.Date;
import java.time.ZoneId;
import java.util.List;

public class UpdateTransportController {

    @FXML
    private TextField transportIdField;

    @FXML
    private ComboBox<CategorieTransport> categorieComboBox;

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
        descriptionField.setText(transport.getDescription());
        imagePathField.setText(transport.getImageTransport());
        prixField.setText(String.valueOf(transport.getPrixTransport()));

        // Set the dateField value
        java.util.Date utilDate = new java.util.Date(transport.getDateTransport().getTime());
        LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Set the value of dateField
        dateField.setValue(localDate);

        // Load categories from the database and set the ComboBox value
        loadCategories();

        // Set the initial value of the ComboBox to the category of the selected transport
        if (transport.getTypeTransport() != null) {
            categorieComboBox.setValue(transport.getTypeTransport());
        }

        // Display the existing image
        displayExistingImage();
    }



    private void loadCategories() {
        // Initialize the CategorieService with the connection
        CategorieService categorieService = new CategorieService();

        // Get the list of categories from the service
        List<CategorieTransport> categories = CategorieService.readAll();

        ObservableList<CategorieTransport> categorieObservableList = FXCollections.observableArrayList(categories);
        categorieComboBox.setItems(categorieObservableList);

        // Define how selected items are shown in the ComboBox dropdown
        categorieComboBox.setCellFactory(param -> new ListCell<CategorieTransport>() {
            @Override
            protected void updateItem(CategorieTransport item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNomCategorieTransport() == null) {
                    setText(null);
                } else {
                    setText(item.getNomCategorieTransport());
                }
            }
        });

        // Define how selected items are shown in the ComboBox
        categorieComboBox.setButtonCell(new ListCell<CategorieTransport>() {
            @Override
            protected void updateItem(CategorieTransport item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNomCategorieTransport() == null) {
                    setText(null);
                } else {
                    setText(item.getNomCategorieTransport());
                }
            }
        });
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
            CategorieTransport selectedCategory = categorieComboBox.getValue(); // Get the selected category from the ComboBox
            if (selectedCategory == null) {
                showAlert("Veuillez sélectionner une catégorie !");
                return;
            }
            LocalDate date = dateField.getValue(); // Get the selected date from the DatePicker
            String prix = prixField.getText().trim();
            String description = descriptionField.getText().trim();

            // Validate fields
            if (date == null || prix.isEmpty() || description.isEmpty()) {
                showAlert("Please fill in all required fields!");
                return;
            }

            // Use TransportService to update the transport in the database
            selectedTransport.setTypeTransport(selectedCategory); // Set the selected category
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
            Stage stage = (Stage) transportIdField.getScene().getWindow();
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
