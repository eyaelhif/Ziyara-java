package Controllers.Guide;

import entities.Guide;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import services.ServiceGuide;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

public class EditGuide {

    @FXML
    private TextField TfLangueedit;

    @FXML
    private Button btnEditImageGuide;

    @FXML
    private ImageView imageGPreviewedit;

    @FXML
    private CheckBox tfDispoedit;

    @FXML
    private CheckBox tfIndispoedit;

    @FXML
    private TextField tfNomedit;

    @FXML
    private TextField tfPrenomedit;

    @FXML
    private Button tfValideredit;
    private File imageFile; // For storing the selected image file
    private Guide currentGuide; // The publication being edited
    private ServiceGuide serviceGuide = new ServiceGuide();

    public void setGuide(Guide guide) {
        currentGuide = guide;
        tfNomedit.setText(guide.getNomGuide());
        tfPrenomedit.setText(guide.getPrenomGuide());
        tfDispoedit.setText(guide.getDisponibilite());
        TfLangueedit.setText(guide.getLangue());



        // Set image with a unique timestamp to prevent caching
        String imagePath = "/images/" + guide.getImageGuide()+ "?time=" + System.currentTimeMillis();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            imageGPreviewedit.setImage(image);
        } else {
            System.out.println("Image not found: " + imagePath);
            // You can set a default image here if necessary
        }
    }


    @FXML
    void editImageGuide(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image for the guide");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg", "*.bmp", "*.gif"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            imageFile = file;
            Image image = new Image(file.toURI().toString());
            imageGPreviewedit.setImage(image);
        }
    }


    @FXML
    void modifierGuide(ActionEvent event) {
        try {
            String nom = tfNomedit.getText();
            String prenom = tfPrenomedit.getText();

            String disponibilite = tfDispoedit.getText();
            String langue = TfLangueedit.getText();

            // Update the publication object
            currentGuide.setNomGuide(nom);
            currentGuide.setPrenomGuide(prenom);
            currentGuide.setDisponibilite(disponibilite);
            currentGuide.setLangue(langue);


            // Vérification que tous les champs sont remplis
            if (nom.isEmpty() || prenom.isEmpty() ||  langue.isEmpty() ) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs avant de soumettre.");
                return;
            }
            // Update the image if a new one was selected
            if (imageFile != null) {
                String imagesDir = "src/main/resources/images/";
                Files.createDirectories(Paths.get(imagesDir));
                Path sourcePath = imageFile.toPath();
                Path destinationPath = Paths.get(imagesDir + imageFile.getName());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                String imageName = destinationPath.getFileName().toString();
                currentGuide.setImageGuide(imageName);
            }

            serviceGuide.modifier(currentGuide);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Publication has been updated successfully.");
            alert.showAndWait();

        } catch (IOException | NumberFormatException | SQLException e) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.setContentText("An error occurred: " + e.getMessage());

            alert.showAndWait();
        }

    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
