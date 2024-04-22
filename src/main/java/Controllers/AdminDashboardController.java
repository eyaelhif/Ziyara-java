package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AdminDashboardController {
    @FXML
    private Label adminNameLabel;

    public void initialize() {
        // Set the admin name in the label
     //   GuiLoginController guilogin = new GuiLoginController();
        String name="Bienvenue ";
        adminNameLabel.setText(name);
    }

    public void goToNavigate(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/AdminDashboard.fxml");
    }

    public void goToUsers(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/PacksCrud.fxml");
    }

    public void goToActivities(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/ReservationsCrud.fxml");
    }


    public void goToLogn(MouseEvent mouseEvent) {
    }
}
