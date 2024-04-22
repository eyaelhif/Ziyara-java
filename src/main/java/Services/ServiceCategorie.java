package Services;

import Entities.categorie;

import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategorie implements IService<categorie>{
    private Connection connection;

    public ServiceCategorie(Connection connection) {
        this.connection= MyDB.getInstance().getConnection();
        System.out.println("Connection succesfull!!");
    }

    public ServiceCategorie() {
        this.connection= MyDB.getInstance().getConnection();
        System.out.println("Connection succesfull!!");
    }

    @Override
    public void create(categorie categorie) throws SQLException {
        String query="INSERT INTO categorie (type, description) VALUES (?,?)";
        try (PreparedStatement statement=connection.prepareStatement(query)){
            statement.setString(1,categorie.getType());
            statement.setString(2,categorie.getDescription());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.err.println("Error while creating product:" + e.getMessage());
        }
    }


    @Override
    public void delete(categorie categorie) throws SQLException {
        String query = "DELETE FROM categorie WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categorie.getId()); // Assuming id is the primary key column
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while deleting product: " + e.getMessage());
        }
    }


    @Override
    public List<categorie> read() throws SQLException {
        List<categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM categorie";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                categorie c = new categorie();
                c.setId(resultSet.getInt("id")); // Assuming id is the primary key column
                c.setType(resultSet.getString("type"));
                c.setDescription(resultSet.getString("description"));
                categories.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error while reading products: " + e.getMessage());
        }
        return categories;
    }


    @Override
    public void update(categorie categorie) throws SQLException {
        String query="UPDATE categorie SET type = ?, description = ? WHERE id = ?";
        try (PreparedStatement statement=connection.prepareStatement(query)){
            statement.setString(1,categorie.getType());
            statement.setString(2,categorie.getDescription());
            statement.setInt(3,categorie.getId());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.err.println("Error while updating product:" + e.getMessage());
        }



    }

}
