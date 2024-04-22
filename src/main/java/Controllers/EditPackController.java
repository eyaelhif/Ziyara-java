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
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import services.PackService;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class EditPackController {
    public static Pack pack;
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
        newPack.setId(pack.getId());
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
        servicePack.update(newPack);
    }
    public void uploadImage(File imageFile) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        // Create HTTP client

        // Create HTTP POST request
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8000/upload-image");

        // Build the multipart request entity
        HttpEntity requestEntity = MultipartEntityBuilder.create()
                .addBinaryBody("image", imageFile, ContentType.APPLICATION_OCTET_STREAM, imageFile.getName())
                .build();

        // Set the request entity
        httpPost.setEntity(requestEntity);
// Create a custom SSL context with a TrustManager that trusts all certificates
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();

// Create an HttpClient with the custom SSL context
        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();
        // Execute the request
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(response);

        // Handle the response as needed
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            // Get the filename from the Content-Disposition header
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
        loadData();
        btnReturn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Returning to Pack CRUD");
                RouterController.navigate("/fxml/PacksCrud.fxml");
            }
        });
    }

    private void loadData() {
        this.titrePack.setText(pack.getTitrePack());
       this.Prix.setText(String.valueOf(pack.getPrixPack()));
        this.dateArrivePack.setValue(pack.getDateArrivePack().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.dateDepartPack.setValue(pack.getDateDepartPack().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.descriptionPack.setText(pack.getDescriptionPack());
        System.out.println(pack.getGuide());
        GuideCombobox.getSelectionModel().select(pack.getGuide());
        Transportcombobox.getSelectionModel().select(pack.getTransports());
        String imageUrl = "http://127.0.0.1:8000/img/"+pack.getImagePack();
        System.out.println(imageUrl);
        Image image = new Image(imageUrl);
        imageView.setImage(image);
       // Guide guide = GuideCombobox.getValue();
       // Transport transport = Transportcombobox.getValue();

    }

    private void initializeComboBoxes() {
        List<Guide> Guides = servicePack.getAllGuides();
        List<Transport> transports = servicePack.getAllTransports();
        this.GuideCombobox.getItems().addAll(Guides);
        this.Transportcombobox.getItems().addAll(transports);

    }


    public boolean validateInputsAndProceed() {
      /*  if (selectedImageFile == null) {
            showAlert("Sélectionner une image s'il vous plaît.");
            return false;
        }*/
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
        if (GuideCombobox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Selectionner un Guide");
            return false;
        }
        if (Transportcombobox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Selectionner un Transport");
            return false;
        }

        if (descriptionPack.getText().isEmpty()) {
            showAlert("La description ne peut pas être vide.");
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
    public void returnTo(ActionEvent actionEvent) {
        System.out.println("Returning to Hotels CRUD");
        RouterController.navigate("/fxml/Activities/ActivitiesCRUD.fxml");
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
