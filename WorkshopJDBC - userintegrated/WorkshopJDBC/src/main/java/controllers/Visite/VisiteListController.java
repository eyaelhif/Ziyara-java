package controllers.Visite;

import models.Visite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import services.VisiteService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class VisiteListController {

    private VisiteService visiteService = new VisiteService();

    @FXML
    private TableView<Visite> visiteTableView;

    @FXML
    private TableColumn<Visite, String> titreColumn;

    @FXML
    private TableColumn<Visite, String> descriptionColumn;

    @FXML
    private TableColumn<Visite, String> dateVisiteColumn;

    @FXML
    private TableColumn<Visite, Double> prixColumn;

    @FXML
    private TableColumn<Visite, String> imageColumn;

    @FXML
    private Label titleLabel;

    @FXML
    private Button modifierButton;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button addVisiteButton;

    @FXML
    void initialize() {
        try {
            ObservableList<Visite> observableList = FXCollections.observableList(visiteService.readAll());
            visiteTableView.setItems(observableList);
            titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description_visite"));
            dateVisiteColumn.setCellValueFactory(new PropertyValueFactory<>("date_visite"));
            prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
            imageColumn.setCellValueFactory(new PropertyValueFactory<>("imagev"));
// Set a custom cell factory to display images in the imageColumn
            imageColumn.setCellFactory(column -> {
                return new TableCell<>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String imagePath, boolean empty) {
                        super.updateItem(imagePath, empty);
                        if (empty || imagePath == null) {
                            setGraphic(null);
                        } else {
                            // Load the image from the file path
                            Image image = new Image("file:" + imagePath);
                            imageView.setImage(image);
                            imageView.setFitWidth(100); // Adjust the width of the image as needed
                            imageView.setPreserveRatio(true);
                            setGraphic(imageView);
                        }
                    }
                };
            });

            // Load data into the TableView
            loadData();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void loadData() {
        try {

            List<Visite> Visites = visiteService.getAllVisite();
            ObservableList<Visite> VisitestList = FXCollections.observableArrayList(Visites);
            visiteTableView.setItems(VisitestList);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void addVisite(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/AjoutVisiteFXML.fxml"));
            Parent root = loader.load();
            AddVisiteController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierVisite(ActionEvent event) {
        Visite selectedVisite = visiteTableView.getSelectionModel().getSelectedItem();
        if (selectedVisite != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ModifVisiteFXML.fxml"));
                Parent root = loader.load();

                // Pass the selected visit details to the controller of the modifVisiteFXML scene
                ModifVisiteController modifVisiteController = loader.getController();
                modifVisiteController.initData(selectedVisite);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        } else {
            showAlert(AlertType.WARNING, "Aucune visite sélectionnée", "Veuillez sélectionner une visite à modifier.");
        }
    }


    @FXML
    void supprimerVisite(ActionEvent event) {
        Visite selectedVisite = visiteTableView.getSelectionModel().getSelectedItem();
        if (selectedVisite != null) {
            visiteService.delete(selectedVisite);
            refreshTableView();
        } else {
            showAlert(AlertType.WARNING, "Aucune visite sélectionnée", "Veuillez sélectionner une visite à supprimer.");
        }
    }

    private void refreshTableView() {
        ObservableList<Visite> observableList = FXCollections.observableList(visiteService.readAll());
        visiteTableView.setItems(observableList);
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleVisitesButtonClicked(ActionEvent event) {
        try {
            // Load the Visite list FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ListeVisiteFXML.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ListeReservationVisiteFXML.fxml"));
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

    public void gotofront(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the reservation list view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Visite/ListeVisiteUserFXML.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            //ListeReservationUserController controller = loader.getController();

            // Create a new scene with the loaded FXML root
            Scene scene = new Scene(root);

            // Get the stage from the action event
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene to the stage and show it
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToNavigate(ActionEvent actionEvent) {
    }

    public void searchquery(KeyEvent keyEvent) {
    }
}

