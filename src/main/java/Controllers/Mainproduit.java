package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import Entities.produit;
import Services.ServiceProduit;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;


public class Mainproduit {
    @FXML
    private AnchorPane mainform;

    @FXML
    private Button menu;

    @FXML
    private Button produit;

    @FXML
    private Tab tabc;

    @FXML
    private Tab tabp;
    public void initialize(){

        FXMLLoader loader = new FXMLLoader();
        try
        {
            AnchorPane anch1 = loader.load(getClass().getResource("/com/example/ziyarae/Produit1.fxml"));
            tabp.setContent(anch1);
        }
        catch(IOException iex)
        {
            System.out.println("File not found");
        }
        loader = new FXMLLoader();
        try
        {
            AnchorPane anch2 = loader.load(getClass().getResource("/com/example/ziyarae/categorie.fxml"));
            tabc.setContent(anch2);
        }
        catch(IOException iex)
        {
            System.out.println("File not found");
        }
    }
}
