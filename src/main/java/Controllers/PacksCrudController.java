package Controllers;

import entities.Guide;
import entities.Pack;
import entities.ReservationPack;
import entities.Transport;
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

public class PacksCrudController {
    @FXML
    private TableView<Pack> tableView;

    private final PackService servicePack = new PackService();
    @FXML
    public void initialize() {
        initializeTableColumns();
        updatePacksList();
    }

    private void updatePacksList() {
        List<Pack> Packs = servicePack.getAll();

        tableView.getItems().clear();

        tableView.getItems().addAll(Packs);

    }

    private void initializeTableColumns() {
        tableView.getColumns().clear();

        TableColumn<Pack, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Pack, String> titreColumn = new TableColumn<>("titrePack");
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titrePack"));

        TableColumn<Pack, String> prixColumn = new TableColumn<>("prixPack");
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixPack"));

        TableColumn<Pack, String> descriptioncolumn = new TableColumn<>("descriptionPack");
        descriptioncolumn.setCellValueFactory(new PropertyValueFactory<>("descriptionPack"));

        TableColumn<Pack, Date> dateDepartPackColumn = new TableColumn<>("dateDepartPack");
        dateDepartPackColumn.setCellValueFactory(new PropertyValueFactory<>("dateDepartPack"));
        dateDepartPackColumn.setCellFactory(column -> {
            return new TableCell<Pack, Date>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        String formattedDate = item.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
                        setText(formattedDate);
                    }
                }
            };
        });

        TableColumn<Pack, Date> dateArrivePackColumn = new TableColumn<>("dateArrivePack");
        dateArrivePackColumn.setCellValueFactory(new PropertyValueFactory<>("dateArrivePack"));
        dateArrivePackColumn.setCellFactory(column -> {
            return new TableCell<Pack, Date>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        String formattedDate = item.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
                        setText(formattedDate);
                    }
                }
            };
        });

        TableColumn<Pack, Transport> transportsColumn = new TableColumn<>("transports");
        transportsColumn.setCellValueFactory(new PropertyValueFactory<>("transports"));
        transportsColumn.setCellFactory(column -> {
            return new TableCell<Pack, Transport>() {
                @Override
                protected void updateItem(Transport item, boolean empty) {
                    super.updateItem(item, empty);
                    System.out.println(item);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(item.getId()));
                    }
                }
            };
        });

        TableColumn<Pack, Guide> guideColumn = new TableColumn<>("guide");
        guideColumn.setCellValueFactory(new PropertyValueFactory<>("guide"));
        guideColumn.setCellFactory(column -> {
            return new TableCell<Pack, Guide>() {
                @Override
                protected void updateItem(Guide item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(item.getId()));
                    }
                }
            };
        });
        TableColumn<Pack, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());
        TableColumn<Pack, Void> imageColumn = new TableColumn<>("imagePack");
        imageColumn.setCellFactory(column -> {
            return new TableCell<Pack, Void>() {
                private final ImageView imageView = new ImageView();
                private final double imageSize = 50; // Adjust the size of the image as needed

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Pack pack = getTableRow().getItem();
                        if (pack != null && pack.getImagePack() != null) {
                            try {
                                System.out.println("https://127.0.0.1:8000/img/"+pack.getImagePack());
                                String imageUrl = "http://127.0.0.1:8000/img/"+pack.getImagePack();
                                System.out.println(imageUrl);
                                Image image = new Image(imageUrl);
                                imageView.setImage(image);
                                imageView.setFitWidth(imageSize);
                                imageView.setFitHeight(imageSize);
                                setGraphic(imageView);
                            } catch (Exception e) {
                                // Handle any exceptions that may occur while loading the image
                                e.printStackTrace();
                            }
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            };
        });
       // imageColumn.setCellFactory(getImageCellFactory());
        System.out.println("Columns added");
        tableView.getColumns().addAll(idColumn, titreColumn, prixColumn,descriptioncolumn, dateDepartPackColumn, dateArrivePackColumn,guideColumn, transportsColumn,actionColumn,imageColumn);
    }

    public void goToNavigate(ActionEvent event) {
        RouterController.navigate("/fxml/AdminDashboard.fxml");
    }

    public void searchquery(KeyEvent keyEvent) {
    }

    public void gotoAjouter(ActionEvent event) {
        RouterController.navigate("/fxml/AddPack.fxml");
    }
    private Callback<TableColumn<Pack, Void>, TableCell<Pack, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<Pack, Void>, TableCell<Pack, Void>>() {
            @Override
            public TableCell<Pack, Void> call(final TableColumn<Pack, Void> param) {
                final TableCell<Pack, Void> cell = new TableCell<Pack, Void>() {
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
                            Pack hotel = getTableView().getItems().get(getIndex());
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Pack hotel = getTableView().getItems().get(getIndex());
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
                                Pack pack = getTableView().getItems().get(getIndex());
                                if (pack != null) {
                                    System.out.println("SETTING ID");
                                    System.out.println(pack.getId());

                                    EditPackController.pack= pack;
                                    RouterController.navigate("/fxml/EditPack.fxml");

                                }

                            });


                            deleteButton.setOnAction((ActionEvent event) -> {
                                Pack pack = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Supprimer Pack");
                                alert.setContentText("Vous etes sur tu veux supprimer cette Pack?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    servicePack.delete(pack.getId());
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
