package Controllers.Guide;

import Controllers.Guide.CardGuide;
import entities.Guide;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import services.ServiceGuide;

import java.util.List;
import java.util.stream.Collectors;

public class AffichageGuide {

    @FXML
    private FlowPane guidesContainer;

    @FXML
    private TextField searchField;

    private final ServiceGuide serviceGuide = new ServiceGuide();

    @FXML
    private void initialize() {
        loadGuides(null); // Load all guides initially

        // Add a listener to the search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            loadGuides(newValue); // Load guides with the search term
        });
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText();
        loadGuides(searchTerm);
    }

    private void loadGuides(String searchTerm) {
        try {
            List<Guide> Guide = serviceGuide.afficher();
            if (searchTerm != null && !searchTerm.isEmpty()) {
                Guide = Guide.stream()
                        .filter(guide -> guide.getNomGuide().toLowerCase().contains(searchTerm.toLowerCase()))
                        .collect(Collectors.toList());
            }

            guidesContainer.getChildren().clear();
            for (Guide gui : Guide) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Guide/cardGuide.fxml"));
                Node card = loader.load(); // This line can throw IOException
                CardGuide controller = loader.getController();
                controller.setGuide(gui);
                controller.setAffichageGuideController(this); // Pass reference to this controller
                guidesContainer.getChildren().add(card);
            }
        } catch (Exception e) { // Catch any exception here
            e.printStackTrace();
        }
    }


    public void refreshGuidesView() {
        Platform.runLater(() -> {
            loadGuides(null); // Reload all guides
        });
    }
}

