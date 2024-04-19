package Services;

import Entities.produit;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ServiceProduit implements IService<produit> {
   private Connection connection;

    public ServiceProduit(Connection connection) {
            this.connection= MyDB.getInstance().getConnection();
            System.out.println("Connection succesfull!!");
    }

    public ServiceProduit() {
        this.connection= MyDB.getInstance().getConnection();
        System.out.println("Connection succesfull!!");
    }

    @Override
    public void create(produit produit) throws SQLException {
        String query="INSERT INTO produit (nom, image, quantite, prix) VALUES (?,?,?,?)";
        try (PreparedStatement statement=connection.prepareStatement(query)){
            statement.setString(1,produit.getNom());
            statement.setString(2,produit.getImage());
            statement.setInt(3,produit.getQuantite());
            statement.setFloat(4,produit.getPrix());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.err.println("Error while creating product:" + e.getMessage());
        }
    }


    @Override
    public void delete(produit produit) throws SQLException {
        String query = "DELETE FROM produit WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, produit.getId()); // Assuming id is the primary key column
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while deleting product: " + e.getMessage());
        }
    }


        @Override
    public List<produit> read() throws SQLException {
        List<produit> produits = new ArrayList<>();
        String query = "SELECT * FROM produit";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                produit p = new produit();
                p.setId(resultSet.getInt("id")); // Assuming id is the primary key column
                p.setNom(resultSet.getString("nom"));
                p.setImage(resultSet.getString("image"));
                p.setQuantite(resultSet.getInt("quantite"));
                p.setPrix(resultSet.getFloat("prix"));
                p.setCatt(resultSet.getInt("catt"));
                produits.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error while reading products: " + e.getMessage());
        }
        return produits;
    }


    @Override
    public void update(produit produit) throws SQLException {
        String query="UPDATE produit (nom, image, quantite, prix, catt) VALUES (?,?,?,?,?)";
        try (PreparedStatement statement=connection.prepareStatement(query)){
            statement.setString(1,produit.getNom());
            statement.setString(2,produit.getImage());
            statement.setInt(3,produit.getQuantite());
            statement.setFloat(4,produit.getPrix());
            statement.setInt(5,produit.getCatt());
        }
        catch (SQLException e){
            System.err.println("Error while updating product:" + e.getMessage());
        }

    }

}
