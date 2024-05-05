package controllers.Visite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {

    @FXML
    public void goToVisites() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ListeVisiteFXML.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void goToReservationVisites() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ListeReservationVisiteFXML.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goToLogn(MouseEvent mouseEvent) {
    }

    public void goToNavigate(ActionEvent actionEvent) {
    }

    // Other methods and variables...
}
