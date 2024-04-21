package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.CategorieTransport;
import models.Transport;
import services.CategorieService;
import services.TransportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AddTransportController {

    @FXML
    private Button addTransportBtn;

    @FXML
    private DatePicker dateField;


    @FXML
    private TextField prixField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox categorieComboBox;


    private final TransportService transportService = new TransportService();

    @FXML
    private Button imagePathField;
    @FXML
    private ImageView imageView;
    private boolean isImageSelected = false;

    private CategorieService categorieService = new CategorieService();
    @FXML
    public void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            imagePathField.setText(imagePath);

            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            isImageSelected = true;
        } else {
            showAlert("Veuillez sélectionner une image !");

        }

    }
    @FXML
    public void initialize() {
        List<CategorieTransport> categories = categorieService.readAll();
        ObservableList<CategorieTransport> categorieObservableList = FXCollections.observableArrayList(categories);
        categorieComboBox.setItems(categorieObservableList);


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

        // Define how selected items are shown in the ComboBox dropdown
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





    @FXML
    public void addTransport(ActionEvent event) {
        if (!isImageSelected) {
            showAlert("Veuillez sélectionner une image !");
            return;
        }

        // Fetch categories from the service
        List<CategorieTransport> categories = categorieService.readAll();

        if (categories.isEmpty()) {
            showAlert("Veuillez sélectionner une catégorie !");
            return;
        }

        // Get the selected category from the ComboBox
        CategorieTransport selectedCategory = (CategorieTransport) categorieComboBox.getSelectionModel().getSelectedItem();

        if (selectedCategory == null) {
            showAlert("Veuillez sélectionner une catégorie !");
            return;
        }

        LocalDate selectedDate = dateField.getValue();
        LocalDate currentDate = LocalDate.now();

        if (selectedDate == null || selectedDate.isBefore(currentDate)) {
            showAlert("La date doit être ultérieure à la date actuelle !");
            return;
        }

        Date date = java.sql.Date.valueOf(selectedDate);

        String prixText = prixField.getText().trim();
        if (!isValidNumericInput(prixText)) {
            showAlert("Prix doit être positif!");
            return;
        }
        double prix = Double.parseDouble(prixText);

        String description = descriptionField.getText().trim();

        if (description.isEmpty()) {
            showAlert("Veuillez remplir tous les champs !");
            return;
        }

        Transport newTransport = new Transport();
        newTransport.setTypeTransport(selectedCategory);
        newTransport.setDateTransport(date);
        newTransport.setPrixTransport(prix);
        newTransport.setDescription(description);
        newTransport.setImageTransport(imagePathField.getText());

        try {
            transportService.create(newTransport);
            showAlert("Transport added successfully!");

            Stage stage = (Stage) addTransportBtn.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            showAlert("Failed to add transport: " + e.getMessage());
        }
    }



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
