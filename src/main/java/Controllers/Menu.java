package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu {
    @FXML
    private Button btnproduit;

    public void gotoproduit(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ziyarae/mainproduit.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);


            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();


            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
