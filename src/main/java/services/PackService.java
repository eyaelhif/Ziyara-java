package services;

import Controllers.RouterController;
import entities.Pack;
import entities.Transport;
import entities.Guide;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import utilities.Myconnection;

public class PackService implements CrudInterface<Pack> {

    private Connection cnx;

    public PackService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
    public void create(Pack pack) {
        String query = "INSERT INTO Pack (transports_id, guide_id, titre_pack, description_pack, prix_pack, image_pack, date_depart_pack, date_arrive_pack, nbvue) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, pack.getTransports().getId()); // Set the id of Transport
            pst.setLong(2, pack.getGuide().getId()); // Set the id of Guide
            pst.setString(3, pack.getTitrePack());
            pst.setString(4, pack.getDescriptionPack());
            pst.setInt(5, pack.getPrixPack());
            pst.setString(6, pack.getImagePack());
            pst.setObject(7, pack.getDateDepartPack());
            pst.setObject(8, pack.getDateArrivePack());
            pst.setInt(9, pack.getNbvue());

            pst.executeUpdate();
            System.out.println("Pack ajouté avec succès");

            showSuccessMessage("Success");
            RouterController.navigate("/fxml/PacksCrud.fxml");
        } catch (SQLException ex) {
            showAlert("Erreur lors de l'ajout du pack : "+ex.getMessage());
            System.err.println("Erreur lors de l'ajout du pack : " + ex.getMessage());
        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void update(Pack pack) {
        if (pack != null) {
            if (pack.getTitrePack() == null || pack.getDescriptionPack() == null || pack.getImagePack() == null || pack.getDateDepartPack() == null || pack.getDateArrivePack() == null) {
                System.err.println("Some essential attributes of the pack are null. Cannot update.");
                return;
            }

            String query = "UPDATE Pack SET titre_pack=?, description_pack=?, prix_pack=?, image_pack=?, date_depart_pack=?, date_arrive_pack=?, nbvue=? WHERE id=?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setString(1, pack.getTitrePack());
                pst.setString(2, pack.getDescriptionPack());
                pst.setInt(3, pack.getPrixPack());
                pst.setString(4, pack.getImagePack());
                pst.setObject(5, pack.getDateDepartPack());
                pst.setObject(6, pack.getDateArrivePack());
                pst.setInt(7, pack.getNbvue());
                pst.setLong(8, pack.getId());

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Pack mis à jour avec succès");
                } else {
                    System.out.println("Aucun pack trouvé avec l'ID : " + pack.getId());
                }
            } catch (SQLException ex) {
                System.err.println("Erreur lors de la mise à jour du pack : " + ex.getMessage());
            }
        } else {
            System.err.println("L'objet pack est null. Impossible de mettre à jour.");
        }
    }




    @Override
    public void delete(int id) {
        String query = "DELETE FROM Pack WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pack supprimé avec succès");
            } else {
                System.out.println("Aucun pack trouvé avec l'ID : " + id);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du pack : " + ex.getMessage());
            showAlert("Pack déja reservé");
        }
    }

    @Override
    public Pack getById(int id) {
        String query = "SELECT * FROM Pack WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Pack pack = new Pack();
                    pack.setId(rs.getInt("id"));
                    pack.setTitrePack(rs.getString("titre_pack"));
                    pack.setDescriptionPack(rs.getString("description_pack"));
                    pack.setPrixPack(rs.getInt("prix_pack"));
                    pack.setImagePack(rs.getString("image_pack"));
                    pack.setDateDepartPack(new java.util.Date(rs.getDate("date_depart_pack").getTime())); // Convert java.sql.Date to java.util.Date
                    pack.setDateArrivePack(new java.util.Date(rs.getDate("date_arrive_pack").getTime())); // Convert java.sql.Date to java.util.Date
                    pack.setNbvue(rs.getInt("nbvue"));

                    return pack;
                } else {
                    System.out.println("Aucun pack trouvé avec l'ID : " + id);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération du pack : " + ex.getMessage());
        }
        return null;
    }

    public List<Guide> getAllGuides() {
        List<Guide> guideList = new ArrayList<>();
        String query = "SELECT * FROM guide";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Guide guide = new Guide();
                    guide.setId(rs.getLong("id"));
                    guide.setNomGuide(rs.getString("nom_guide"));
                    guide.setPrenomGuide(rs.getString("prenom_guide"));

                    guideList.add(guide);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des guides : " + ex.getMessage());
        }
        return guideList;
    }

    public List<Transport> getAllTransports() {
        List<Transport> TransportList = new ArrayList<>();
        String query = "SELECT * FROM transport";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Transport transport = new Transport();
                    transport.setId(rs.getLong("id"));
                    TransportList.add(transport);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des guides : " + ex.getMessage());
        }
        return TransportList;
    }
    @Override
    public List<Pack> getAll() {
        List<Pack> packList = new ArrayList<>();
        String query = "SELECT * FROM Pack";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Pack pack = new Pack();
                    pack.setId(rs.getInt("id"));
                    pack.setTitrePack(rs.getString("titre_pack"));
                    pack.setDescriptionPack(rs.getString("description_pack"));
                    pack.setPrixPack(rs.getInt("prix_pack"));
                    pack.setImagePack(rs.getString("image_pack"));
                    pack.setDateDepartPack(new java.util.Date(rs.getDate("date_depart_pack").getTime())); // Convert java.sql.Date to java.util.Date
                    pack.setDateArrivePack(new java.util.Date(rs.getDate("date_arrive_pack").getTime())); // Convert java.sql.Date to java.util.Date
                    pack.setNbvue(rs.getInt("nbvue"));
                    pack.setGuide(findGuideById(rs.getInt("guide_id")));

                    pack.setTransports(findTransportsById(rs.getInt("transports_id")));

                    packList.add(pack);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des packs : " + ex.getMessage());
        }
        return packList;


    }

    private Transport findTransportsById(int transportsId) {
         String query = "SELECT * FROM transport WHERE id = ?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setInt(1, transportsId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        Transport transport = new Transport();
                        transport.setId(rs.getLong("id"));
                        transport.setPrixTransport(rs.getInt("prix_transport"));
                        return transport;
                    } else {
                        System.out.println("Aucun guide trouvé avec l'ID : " + transportsId);
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Erreur lors de la récupération du guide : " + ex.getMessage());
            }
            return null;
    }

    public List<Pack> getMostLikedPacks() {
        List<Pack> mostLikedPacks = new ArrayList<>();
        String query = "SELECT p.id, p.titre_pack, p.description_pack, p.prix_pack, p.image_pack, p.date_depart_pack, p.date_arrive_pack, p.nbvue " +
                "FROM Pack p " +
                "JOIN reservation_pack rp ON p.id = rp.pack_id " +
                "GROUP BY p.id " +
                "ORDER BY COUNT(rp.id) DESC";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Pack pack = new Pack();
                    pack.setId(rs.getInt("id"));
                    pack.setTitrePack(rs.getString("titre_pack"));
                    pack.setDescriptionPack(rs.getString("description_pack"));
                    pack.setPrixPack(rs.getInt("prix_pack"));
                    pack.setImagePack(rs.getString("image_pack"));
                    pack.setDateDepartPack(rs.getDate("date_depart_pack"));
                    pack.setDateArrivePack(rs.getDate("date_arrive_pack"));
                    pack.setNbvue(rs.getInt("nbvue"));
                    pack.setGuide(findGuideById(rs.getInt("guide")));
                    mostLikedPacks.add(pack);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des packs les plus aimés : " + ex.getMessage());
        }

        return mostLikedPacks;
    }

    private Guide findGuideById(int guideId) {
        String query = "SELECT * FROM guide WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, guideId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Guide guide = new Guide();
                    guide.setId(rs.getLong("id"));
                    guide.setNomGuide(rs.getString("nom_guide"));
                    guide.setPrenomGuide(rs.getString("prenom_guide"));
                    return guide;
                } else {
                    System.out.println("Aucun guide trouvé avec l'ID : " + guideId);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération du guide : " + ex.getMessage());
        }
        return null;
    }

}