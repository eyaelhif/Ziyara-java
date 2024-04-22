package Controllers;

import Entities.produit;
import Services.ServiceProduit;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProduitController {

    @FXML
    private Button btnajouterp;

    @FXML
    private Button btnimporterp;

    @FXML
    private Button btnmodifierp;

    @FXML
    private Button btnsupprimerp;

    @FXML
    private ImageView fimage;

    @FXML
    private TextField fnom;

    @FXML
    private TextField fprix;

    @FXML
    private TextField fquantite;

    @FXML
    private AnchorPane mainform;

    @FXML
    private Button menu;

    @FXML
    private Button produit;

    @FXML
    private Tab tabc;

    @FXML
    private TableColumn<produit, String> tabcategorie;

    @FXML
    private TableColumn<produit, String> tabimage;

    @FXML
    private TableColumn<produit, String> tabnom;

    @FXML
    private Tab tabp;

    @FXML
    private TableColumn<produit, String> tabprix;

    @FXML
    private TableColumn<produit, String> tabquantite;

    @FXML
    private TableView<produit> tabviewp;

    private Alert alert;
    private final ServiceProduit sp = new ServiceProduit();
    String imageUrl;

    public void initialize() {

        tabnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tabimage.setCellValueFactory(new PropertyValueFactory<>("image"));
        tabquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        tabprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tabcategorie.setCellValueFactory(new PropertyValueFactory<>("catt_id"));


        try {
            List<produit> produits = sp.read();
            tabviewp.getItems().addAll(produits);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
        


    public void importImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            fimage.setImage(image);
        }
    }

    public void ajouterProduit() {
        try {

            String nom = fnom.getText();
            if(nom.isEmpty()){
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter the name of the product .");
            }
            Image image = fimage.getImage();

            // Save the image URL or image data as per your requirement
         if (image != null) { imageUrl=image.getUrl();}
         else{ imageUrl="";}

            int quantite = Integer.parseInt(fquantite.getText());
            float prix = Float.parseFloat(fprix.getText());

            produit p = new produit();
            p.setNom(nom);
            p.setImage(imageUrl);
            p.setQuantite(quantite);
            p.setPrix(prix);

            sp.create(p);
            ObservableList<produit> products = tabviewp.getItems();
            products.add(p);

            clearFields();


            showAlert(Alert.AlertType.INFORMATION, "Product Created", "New product created successfully.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numeric values for quantity and price.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error creating product: " + e.getMessage());
        }
    }



    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void clearFields() {
        fnom.clear();
        fimage.setImage(null);
        fquantite.clear();
        fprix.clear();
    }

    @FXML
    public void modifierProduit() {
        try {
            int selectedIndex = tabviewp.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un produit à mettre à jour.");
                return;
            }

            produit selectedProduct = tabviewp.getSelectionModel().getSelectedItem();

            Image image = fimage.getImage();
            if (image != null) { imageUrl=image.getUrl();}
            else{ imageUrl="";}

            selectedProduct.setNom(fnom.getText());
            selectedProduct.setImage(imageUrl);
            selectedProduct.setQuantite(Integer.parseInt(fquantite.getText()));
            selectedProduct.setPrix(Float.parseFloat(fprix.getText()));


            sp.update(selectedProduct);

            tabviewp.getItems().clear();
            tabviewp.getItems().addAll(sp.read());

            clearFields();


            showAlert(Alert.AlertType.INFORMATION, "Produit mis à jour", "Le produit a été mis à jour avec succès.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez entrer des valeurs numériques valides pour la quantité et le prix.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de base de données", "Erreur lors de la mise à jour du produit: " + e.getMessage());
        }
    }

    public void supprimerProduit() {
        produit selectedProduct = tabviewp.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a product to delete.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure you want to delete the selected product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                sp.delete(selectedProduct);
                tabviewp.getItems().clear();
                tabviewp.getItems().addAll(sp.read());
                showAlert(Alert.AlertType.INFORMATION, "Product Deleted", "The selected product has been deleted.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Error deleting product: " + e.getMessage());
            }
        }
    }
}
