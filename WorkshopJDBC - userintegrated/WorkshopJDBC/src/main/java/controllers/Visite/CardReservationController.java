package controllers.Visite;


import models.ReservationVisite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.ReservationVisiteService;

import java.io.IOException;

public class CardReservationController {

    @FXML
    public Label resdat;

    @FXML
    public Label participantCountLabel;

    @FXML
    public Label nomLabel;

    @FXML
    public Label prenomLabel;

    @FXML
    private Button cancelButton;

    private int reservationId;

    private ReservationVisiteService reservationService = new ReservationVisiteService();
    private ReservationVisite reservation;

    public void setReservationService(ReservationVisiteService reservationService) {
        this.reservationService = reservationService;
    }


    public void displayReservationData(ReservationVisite reservation) {
        if (resdat != null && participantCountLabel != null && nomLabel != null && prenomLabel != null) {
            resdat.setText(reservation.getDate_reservation_visite());
            participantCountLabel.setText(String.valueOf(reservation.getNbrparticipant_visite()));
            nomLabel.setText(reservation.getNom());
            prenomLabel.setText(reservation.getPrenom());
            this.reservationId = reservation.getId();
        } else {
            System.out.println("Labels are null. Make sure they are properly injected.");
        }
    }


    @FXML
    public void cancelReservation(ActionEvent actionEvent) {
        try {
            // Check if the reservationService is null before using it
            if (reservationService != null) {
                // Delete the reservation using the service
                reservationService.deleteById(reservationId);

                try {
                    // Load the FXML file for the reservation list view
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Visite/ListeReservationVisiteUserFXML.fxml"));
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
            } else {
                System.out.println("Reservation service is null. Make sure it's properly injected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
