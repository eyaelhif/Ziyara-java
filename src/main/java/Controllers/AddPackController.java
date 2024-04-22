package Controllers;

import entities.Guide;
import entities.Pack;
import entities.Transport;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.http.Header;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import services.PackService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

public class AddPackController {
    public Button btnUploadImage;
    public DatePicker dateDepartPack;
    public ImageView imageView;
    public TextField descriptionPack;
    public DatePicker dateArrivePack;
    public ImageView btnReturn;
    public ComboBox<Transport> Transportcombobox;
    public ComboBox<Guide> GuideCombobox;
    public TextField titrePack;
    public TextField Prix;

    public void returnTo(MouseEvent mouseEvent) {
    }

    public void AddPack(ActionEvent event) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        if (!validateInputsAndProceed()) {
            return;
        }
        String titre=this.titrePack.getText();
        int prix=Integer.parseInt(this.Prix.getText());
        java.util.Date datearrivee = java.sql.Date.valueOf(this.dateArrivePack.getValue());
        java.util.Date datedepart = java.sql.Date.valueOf(this.dateDepartPack.getValue());
        String description = descriptionPack.getText();
        Guide guide = GuideCombobox.getValue();
        Transport transport = Transportcombobox.getValue();
        Pack newPack=new Pack();
        newPack.setTitrePack(titre);
        newPack.setPrixPack(prix);
        newPack.setDateArrivePack(datearrivee);
        newPack.setDateDepartPack(datedepart);
        newPack.setDescriptionPack(description);
        newPack.setGuide(guide);
        newPack.setTransports(transport);
        System.out.println(newPack);
        uploadImage(selectedImageFile);
        newPack.setImagePack(selectedImageFile.getName());
        servicePack.create(newPack);
    }
    public void uploadImage(File imageFile) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8000/upload-image");

        HttpEntity requestEntity = MultipartEntityBuilder.create()
                .addBinaryBody("image", imageFile, ContentType.APPLICATION_OCTET_STREAM, imageFile.getName())
                .build();

        httpPost.setEntity(requestEntity);
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(response);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            Header contentDispositionHeader = response.getFirstHeader("Content-Disposition");
            if (contentDispositionHeader != null) {
                String contentDisposition = contentDispositionHeader.getValue();
                String filename = extractFilenameFromContentDisposition(contentDisposition);
                System.out.println("Success upload. Filename: " + filename);
            } else {
                System.out.println("Success upload, but filename not found in the response");
            }
        } else {
            System.out.println("Failed upload");
        }
    }
    private String extractFilenameFromContentDisposition(String contentDisposition) {
        String filename = null;
        if (contentDisposition != null && contentDisposition.contains("filename=")) {
            String[] parts = contentDisposition.split(";");
            for (String part : parts) {
                if (part.trim().startsWith("filename=")) {
                    filename = part.substring(part.indexOf('=') + 1).trim().replace("\"", "");
                    break;
                }
            }
        }
        return filename;
    }
    private File selectedImageFile;


    private byte[] imageData;

    private PackService servicePack = new PackService();


    @FXML
    public void initialize() {
        initializeComboBoxes();
        btnReturn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Returning to Pack CRUD");
                RouterController.navigate("/fxml/PacksCrud.fxml");
            }
        });
    }

    private void initializeComboBoxes() {
        List<Guide> Guides = servicePack.getAllGuides();
        List<Transport> transports = servicePack.getAllTransports();
        this.GuideCombobox.getItems().addAll(Guides);
        this.Transportcombobox.getItems().addAll(transports);

    }


    public boolean validateInputsAndProceed() {
        if (selectedImageFile == null) {
            showAlert("Sélectionner une image s'il vous plaît.");
            return false;
        }
        String price = Prix.getText().trim();
        if (!price.matches("\\d+")) {
            showAlert("Le prix doit être composé uniquement de chiffres.");
            return false;
        }
        if (titrePack.getText().isEmpty()) {
            showAlert("Le titre ne peut pas être vide.");
            return false;
        }
        if (Prix.getText().isEmpty()) {
            showAlert("Le prix ne peut pas être vide.");
            return false;
        }

        try {
            Double.parseDouble(Prix.getText());
        } catch (NumberFormatException e) {
            showAlert("Le prix doit être un nombre valide.");
            return false;
        }

        if (dateDepartPack.getValue() == null || dateArrivePack.getValue() == null) {
            showAlert("Veuillez sélectionner une date de départ et une date d'arrivée.");
            return false;
        }

        LocalDate departureDate = dateDepartPack.getValue();
        LocalDate arrivalDate = dateArrivePack.getValue();

        if (arrivalDate.isBefore(departureDate)) {
            showAlert("La date de départ doit être avant la date d'arrivée.");
            return false;
        }

        if (descriptionPack.getText().isEmpty()) {
            showAlert("La description ne peut pas être vide.");
            return false;
        }
        System.out.println(GuideCombobox.getSelectionModel().getSelectedItem());
        if (GuideCombobox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Selectionner un Guide");
            return false;
        }
        if (Transportcombobox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Selectionner un Transport");
            return false;
        }

        return true;
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private byte[] loadImage(File file) throws FileNotFoundException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] imageData = new byte[(int) file.length()];
            fis.read(imageData);
            return imageData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileNotFoundException("Error loading image file");
        }
    }


    @FXML
    public void uploadImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        selectedImageFile = fileChooser.showOpenDialog(stage);
        if (selectedImageFile != null) {
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream(selectedImageFile));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
