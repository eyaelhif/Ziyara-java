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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class AjoutGuide {

    // Constants
    private static final String IMAGES_DIR = "src/main/resources/images/";

    @FXML
    private TextField TfLangue;
    @FXML
    private Button tfValider;
    @FXML
    private Button btnChoisirImageGuide;
    @FXML
    private ImageView imageGPreview;
    @FXML
    private CheckBox tfDispo;
    @FXML
    private CheckBox tfIndispo;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfNom;

    private File imageFile;
    @FXML
    void AjouterGuide(ActionEvent event) {
        try {
            String nom = tfNom.getText();
            String prenom = tfPrenom.getText();
            String langue = TfLangue.getText();
            boolean disponibiliteBool = tfDispo.isSelected(); // This is boolean
            String disponibilite = disponibiliteBool ? "oui" : "non"; // Convert boolean to string

            if (nom.isEmpty() || prenom.isEmpty() || langue.isEmpty() || imageFile == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs et l'image doivent être remplis.");
                return;
            }

            // Create the directory if it doesn't exist
            Files.createDirectories(Paths.get(IMAGES_DIR));

            // Copy the image to the images directory and get the filename
            Path sourcePath = imageFile.toPath();
            Path destinationPath = Paths.get(IMAGES_DIR + imageFile.getName());
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            String imageName = destinationPath.getFileName().toString();

            Guide guide = new Guide(0, nom, prenom, langue, disponibilite, imageName); // Pass string disponibilite
            ServiceGuide serviceGuide = new ServiceGuide();
            serviceGuide.ajouter(guide);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le guide a été ajouté avec succès.");
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace to the console
            showAlert(Alert.AlertType.ERROR, "Erreur de fichier", "Un problème est survenu lors de la copie de l'image.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Un problème est survenu lors de l'ajout du guide: " + e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur inattendue", "Une erreur inattendue est survenue: " + e.getMessage());
        }
    }


    @FXML
    void choisirImageGuide(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image pour le guide");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imageFile = file;
            imageGPreview.setImage(new Image(file.toURI().toString()));
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
