package Controllers;

import Entities.categorie;
import Entities.categorie;
import Services.ServiceCategorie;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategorieController {

    @FXML
    private Button btnajouterc;

    @FXML
    private Button btnmodifierc;

    @FXML
    private Button btnsupprimerc;

    @FXML
    private TextField fdescription;

    @FXML
    private TextField ftype;

    @FXML
    private TableColumn<categorie, String> tabdescription;

    @FXML
    private TableColumn<categorie, String> tabtype;

    @FXML
    private TableView<categorie> tabviewc;


    private final ServiceCategorie sc = new ServiceCategorie();


    public void initialize() {

        tabtype.setCellValueFactory(new PropertyValueFactory<>("type"));
        tabdescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            List<categorie> categories = sc.read();
            tabviewc.getItems().addAll(categories);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
    public void ajoutercategorie() {
        try {
            String type = ftype.getText();
            String description = fdescription.getText();


            categorie c = new categorie();
            c.setType(type);
            c.setDescription(description);


            sc.create(c);
            ObservableList<categorie> products = tabviewc.getItems();
            products.add(c);

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
        ftype.clear();
        fdescription.clear();
    }

    @FXML
    public void modifiercategorie() {
        try {
            int selectedIndex = tabviewc.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un categorie à mettre à jour.");
                return;
            }

            categorie selectedCategory = tabviewc.getSelectionModel().getSelectedItem();


            selectedCategory.setType(ftype.getText());
            selectedCategory.setDescription(fdescription.getText());



            sc.update(selectedCategory);

            tabviewc.getItems().clear();
            tabviewc.getItems().addAll(sc.read());

            clearFields();


            showAlert(Alert.AlertType.INFORMATION, "categorie mis à jour", "Le categorie a été mis à jour avec succès.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez entrer des valeurs numériques valides pour la quantité et le prix.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de base de données", "Erreur lors de la mise à jour du categorie: " + e.getMessage());
        }
    }

    public void supprimercategorie() {
        categorie selectedProduct = tabviewc.getSelectionModel().getSelectedItem();
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
                sc.delete(selectedProduct);
                tabviewc.getItems().clear();
                tabviewc.getItems().addAll(sc.read());
                showAlert(Alert.AlertType.INFORMATION, "Product Deleted", "The selected product has been deleted.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Error deleting product: " + e.getMessage());
            }
        }
    }

}
