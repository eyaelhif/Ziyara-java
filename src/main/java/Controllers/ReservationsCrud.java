package Controllers;

import entities.Guide;
import entities.Pack;
import entities.ReservationPack;
import entities.Transport;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import services.PackService;
import services.ReservationPackService;

import java.util.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ReservationsCrud {
    @FXML
    private TableView<ReservationPack> tableView;

    private final ReservationPackService serviceReservationPack = new ReservationPackService();
    @FXML
    public void initialize() {
        initializeTableColumns();
        updateReservationsList();
    }

    private void updateReservationsList() {
        List<ReservationPack> ReservationPacks = serviceReservationPack.getAll();

        tableView.getItems().clear();

        tableView.getItems().addAll(ReservationPacks);

    }

    private void initializeTableColumns() {
        tableView.getColumns().clear();

        TableColumn<ReservationPack, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ReservationPack, String> titreColumn = new TableColumn<>("titre");
        titreColumn.setCellValueFactory(cellData -> {
            ReservationPack reservationPack = cellData.getValue();
            Pack pack = reservationPack.getPack();
            String titre = pack.getTitrePack();
            return new SimpleStringProperty(titre);
        });
        TableColumn<ReservationPack, String> prixColumn = new TableColumn<>("prixPack");
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixPack"));

        TableColumn<ReservationPack, String> descriptioncolumn = new TableColumn<>("descriptionPack");
        descriptioncolumn.setCellValueFactory(new PropertyValueFactory<>("descriptionPack"));

        TableColumn<ReservationPack, Date> dateReservationPack = new TableColumn<>("date_reservation_pack");
        dateReservationPack.setCellValueFactory(new PropertyValueFactory<>("dateReservationPack"));



        TableColumn<ReservationPack, String> nbrParticipantPack = new TableColumn<>("nbr_participant_pack");
        nbrParticipantPack.setCellValueFactory(cellData -> {
            ReservationPack reservationPack = cellData.getValue();
            int nbr = reservationPack.getNbrParticipantPack();
            return new SimpleStringProperty(String.valueOf(nbr));
        });
        TableColumn<ReservationPack, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());
       // imageColumn.setCellFactory(getImageCellFactory());
        System.out.println("Columns added");
        tableView.getColumns().addAll(idColumn, titreColumn,nbrParticipantPack,dateReservationPack,actionColumn);
    }

    public void goToNavigate(ActionEvent event) {
        RouterController.navigate("/fxml/AdminDashboard.fxml");
    }

    public void searchquery(KeyEvent keyEvent) {
    }

    public void gotoAjouter(ActionEvent event) {
        RouterController.navigate("/fxml/AddReservation.fxml");
    }
    private Callback<TableColumn<ReservationPack, Void>, TableCell<ReservationPack, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<ReservationPack, Void>, TableCell<ReservationPack, Void>>() {
            @Override
            public TableCell<ReservationPack, Void> call(final TableColumn<ReservationPack, Void> param) {
                final TableCell<ReservationPack, Void> cell = new TableCell<ReservationPack, Void>() {
                    private final Button modifyButton = new Button();
                    private final Button deleteButton = new Button();

                    {
                        Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
                        ImageView modifyIcon = new ImageView(modifyImage);
                        modifyIcon.setFitWidth(20);
                        modifyIcon.setFitHeight(20);
                        modifyButton.setGraphic(modifyIcon);

                        modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
                        deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                        ImageView deleteIcon = new ImageView(deleteImage);
                        deleteIcon.setFitWidth(16);
                        deleteIcon.setFitHeight(16);
                        deleteButton.setGraphic(deleteIcon);

                        modifyButton.setOnAction((ActionEvent event) -> {
                            ReservationPack ReservationPack = getTableView().getItems().get(getIndex());
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            ReservationPack ReservationPack = getTableView().getItems().get(getIndex());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(5);
                            buttons.getChildren().addAll(modifyButton, deleteButton);

                            modifyButton.setFocusTraversable(false);
                            deleteButton.setFocusTraversable(false);

                            modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
                            deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                            Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
                            ImageView modifyIcon = new ImageView(modifyImage);
                            modifyIcon.setFitWidth(20);
                            modifyIcon.setFitHeight(20);
                            modifyButton.setGraphic(modifyIcon);
                            Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                            ImageView deleteIcon = new ImageView(deleteImage);
                            deleteIcon.setFitWidth(20);
                            deleteIcon.setFitHeight(20);
                            deleteButton.setGraphic(deleteIcon);
                            modifyButton.setOnAction((ActionEvent event) -> {
                                ReservationPack ReservationPack = getTableView().getItems().get(getIndex());
                                if (ReservationPack != null) {
                                    System.out.println("SETTING ID");
                                    System.out.println(ReservationPack.getId());

                                    EditReservationController.reservationPack= ReservationPack;
                                    RouterController.navigate("/fxml/EditReservation.fxml");

                                }

                            });


                            deleteButton.setOnAction((ActionEvent event) -> {
                                ReservationPack ReservationPack = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Supprimer Pack");
                                alert.setContentText("Vous etes sur tu veux supprimer cette Pack?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    serviceReservationPack.delete(ReservationPack.getId());
                                    initialize();

                                }
                            });

                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
    }

}
