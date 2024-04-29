package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import services.UserService;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AddUserController {


    @FXML
    private TextField addemailuser;

    @FXML
    private ChoiceBox<String> addisuser;

    @FXML
    private TextField addpassworduser;

    @FXML
    private ChoiceBox<String> addroleuser;

    @FXML
    private Button addUserBtn;

    private final UserService userService = new UserService();


    public void initialize() {
        // Initialize the choice box with options
        ObservableList<String> options = FXCollections.observableArrayList("true", "false");
        addisuser.setItems(options);
        ObservableList<String> roles = FXCollections.observableArrayList("admin", "client");
        addroleuser.setItems(roles);
    }

    // Helper method to show alerts
    private void showAlert(String message, String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        // Implement your email validation logic here
        // You can use regular expressions or other methods to validate email format
        return email != null && !email.isEmpty() && email.contains("@");
    }

    private boolean isValidPassword(String password) {
        // Implement your password validation logic here
        return password != null && password.length() >= 6;
    }


    public void addUser(javafx.event.ActionEvent actionEvent) {

        // Input validation
        String email = addemailuser.getText().trim();
        String pswd = addpassworduser.getText().trim();
        String selectedRole = addroleuser.getValue();
        String isVerified = addisuser.getValue();

        // Check if email is valid
        if (!isValidEmail(email)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }

        // Check if password is valid
        /*if (!isValidPassword(password)) {
            showAlert("Invalid Password", "Password must be at least 6 characters long.");
            return;
        }*/

        String roles;
        switch (selectedRole) {
            case "admin":
                roles = "{\"role\": \"admin\"}";
                break;
            case "client":
                roles = "{\"role\": \"client\"}";
                break;
            default:
                showAlert("Invalid Role cycy", "Please select a valid role.");
                return;
        }

        int iv;
        switch (isVerified) {
            case "true":
                iv = 1;
                break;
            case "false":
                iv = 0;
                break;
            default:
                showAlert("Invalid is_verified", "Please select true or false.");
                return;
        }

        // Create a new Cours object
        User user = new User(email, roles, pswd, 0, 0);

        // Use CoursService to add the new cours to the database
        try {
            userService.create(user);
            showAlert("User added successfully!", "Please enter a valid email address.");

            // Get the stage associated with the addCoursBtn button and close it
            Stage stage = (Stage) addUserBtn.getScene().getWindow();
            stage.close();

            // You may implement further actions here, like refreshing the view
        } catch (SQLException e) {
            showAlert("Failed to add cours: " + e.getMessage(), "Please enter a valid email address.");
        }



    }
}
