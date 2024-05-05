package controllers.Visite;

import models.Visite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.sql.*;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import services.UserService;

import static java.sql.DriverManager.getConnection;

public class CardVisiteController {

    @FXML
    private ImageView imageColumn;

    @FXML
    private Button addres;

    @FXML
    private Button Share;

    @FXML
    private Label titre;

    @FXML
    private Label description_visite;

    @FXML
    private Label date_visite;

    @FXML
    private Label prix;
    @FXML
    private Button btnfavoris;
    @FXML
    private ImageView btnGraphic;

    @FXML
    private Label imeagv;
    private int vid;

    public void displayVisiteData(Visite visite) {
        titre.setText(visite.getTitre());
        description_visite.setText(visite.getDescription_visite());
        date_visite.setText(visite.getDate_visite().toString()); // Assuming getDate_visite() returns a LocalDate
        prix.setText(String.valueOf(visite.getPrix()));
        this.vid=visite.getId();
        if (checkFavorisExists()) {
            setButtonImage(true);
        } else {
            setButtonImage(false);
        }


        String imagePath = visite.getImagev();

        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageColumn.setImage(image);
            } else {
                System.out.println("L'image n'existe pas : " + imagePath);
            }
        } else {
            System.out.println("Le chemin de l'image est vide.");
        }
    }

    public void setButtonImage(boolean favorisExists) {
        String imageUrl = favorisExists ? "/images/heart.png" : "/images/heartb.png"; // Change "favorisImage.png" to the path of your favoris image
        Image image = new Image(getClass().getResourceAsStream(imageUrl));
        btnGraphic.setImage(image);
    }

    public void addres(ActionEvent actionEvent) {
        // Assuming you have the visiteid available, replace "yourVisiteId" with the actual visiteid value


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Visite/AjoutReservationUserFXML.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            AddReservationUserController controller = loader.getController();

            // Pass the visiteId to the controller
            controller.setVisiteId(vid);

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkFavorisExists() {
        // SQL query to check if the favoris instance exists
        String query = "SELECT * FROM favoris WHERE id_visite_id = ? AND id_user_id = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            UserService userService = new UserService();
            int userId = userService.getCurrentUserId(); // Get the current user's ID

            statement.setInt(1, vid);
            statement.setInt(2, userId);


            // Execute query
            ResultSet resultSet = statement.executeQuery();

            // If resultSet.next() returns true, it means favoris exists
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void deleteFavoris() {
        // SQL query to delete favoris instance
        String query = "DELETE FROM favoris WHERE id_visite_id = ? AND id_user_id = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            UserService userService = new UserService();
            int userId = userService.getCurrentUserId(); // Get the current user's ID

            statement.setInt(1, vid);
            statement.setInt(2, userId);


            // Execute update
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addfavoris(ActionEvent actionEvent) {
        // Check if the favoris instance already exists
        boolean favorisExists = checkFavorisExists();

        if (favorisExists) {
            // If favoris exists, delete it
            deleteFavoris();
        } else {
            // If favoris doesn't exist, create it
            createFavoris();
        }

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        // Load the same FXML file to refresh the content
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Visite/ListeVisiteUserFXML.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Set the scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integration", "root", "");

            return connection;
    }

    private void createFavoris() {
        // SQL query to insert favoris instance
        String query = "INSERT INTO favoris (id_visite_id,id_user_id) VALUES (?,?)";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            UserService userService = new UserService();
            int userId = userService.getCurrentUserId(); // Get the current user's ID
            statement.setInt(1, vid);
            statement.setInt(2, userId);

            // Execute update
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
