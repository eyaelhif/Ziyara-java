package services;

import entities.Pack;
import entities.ReservationPack;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.User;
import utilities.Myconnection;

public class ReservationPackService implements CrudInterface<ReservationPack> {

    private Connection cnx;

    public ReservationPackService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
    public void create(ReservationPack reservationPack) {
        String query = "INSERT INTO reservation_pack (date_reservation_pack, nbr_participant_pack, pack_id, user_id, likes, dis) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setDate(1, new java.sql.Date(reservationPack.getDateReservationPack().getTime()));
            pst.setInt(2, reservationPack.getNbrParticipantPack());
            pst.setLong(3, reservationPack.getPack().getId());
            pst.setLong(4, reservationPack.getUser().getId());
            pst.setInt(5, reservationPack.getLikes());
            pst.setInt(6, reservationPack.getDislikes());

            pst.executeUpdate();
            System.out.println("Réservation pack ajoutée avec succès");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout de la réservation pack : " + ex.getMessage());
        }
    }

    @Override
    public void update(ReservationPack reservationPack) {
        if (reservationPack != null) {
            if (reservationPack.getDateReservationPack() == null) {
                System.err.println("Date reservation pack is null. Cannot update.");
                return;
            }

            String query = "UPDATE reservation_pack SET date_reservation_pack=?, nbr_participant_pack=?, likes=?, dis=? WHERE id=?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setDate(1, new java.sql.Date(reservationPack.getDateReservationPack().getTime()));
                pst.setInt(2, reservationPack.getNbrParticipantPack());
                pst.setInt(3, reservationPack.getLikes());
                pst.setInt(4, reservationPack.getDislikes());
                pst.setLong(5, reservationPack.getId());

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Réservation pack mise à jour avec succès");
                } else {
                    System.out.println("Aucune réservation pack trouvée avec l'ID : " + reservationPack.getId());
                }
            } catch (SQLException ex) {
                System.err.println("Erreur lors de la mise à jour de la réservation pack : " + ex.getMessage());
            }
        } else {
            System.err.println("L'objet reservationPack est null. Impossible de mettre à jour.");
        }
    }


    @Override
    public void delete(int id) {
        String query = "DELETE FROM reservation_pack WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Réservation pack supprimée avec succès");
            } else {
                System.out.println("Aucune réservation pack trouvée avec l'ID : " + id);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de la réservation pack : " + ex.getMessage());
        }
    }

    @Override
    public ReservationPack getById(int id) {
        String query = "SELECT * FROM reservation_pack WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    ReservationPack reservationPack = new ReservationPack();
                    reservationPack.setId(rs.getInt("id"));
                    reservationPack.setDateReservationPack(rs.getDate("date_reservation_pack")); // Get Date directly
                    reservationPack.setNbrParticipantPack(rs.getInt("nbr_participant_pack"));
                    // Assuming you have the appropriate methods to get Pack and User objects
                    // reservationPack.setPack(getPackById(rs.getLong("pack_id")));
                    // reservationPack.setUser(getUserById(rs.getLong("user_id")));
                    reservationPack.setLikes(rs.getInt("likes"));
                    reservationPack.setDislikes(rs.getInt("dis"));

                    return reservationPack;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de la réservation pack : " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<ReservationPack> getAll() {
        List<ReservationPack> reservationPackList = new ArrayList<>();
        String query = "SELECT * FROM reservation_pack";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    ReservationPack reservationPack = new ReservationPack();
                    reservationPack.setId(rs.getInt("id"));
                    java.sql.Date sqlDate = rs.getDate("date_reservation_pack");
                    java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                    reservationPack.setDateReservationPack(utilDate);
                    reservationPack.setNbrParticipantPack(rs.getInt("nbr_participant_pack"));
                    // Assuming you have the appropriate methods to get Pack and User objects
                     reservationPack.setPack(getPackById(rs.getLong("pack_id")));
                    // reservationPack.setUser(getUserById(rs.getLong("user_id")));
                    reservationPack.setLikes(rs.getInt("likes"));
                    reservationPack.setDislikes(rs.getInt("dis"));

                    reservationPackList.add(reservationPack);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des réservations pack : " + ex.getMessage());
        }
        return reservationPackList;
    }

    private Pack getPackById(long packId) {
        String query = "SELECT * FROM pack WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, packId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Pack pack = new Pack();
                    pack.setId(rs.getInt("id"));
                    pack.setTitrePack(rs.getString("titre_pack"));
                    // Set other properties of the Pack object
                    return pack;
                } else {
                    System.out.println("No pack found with ID: " + packId);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving pack: " + ex.getMessage());
        }
        return null;
    }

    public List<Pack> getTopReservedPacksWithLikes(int limit) {
        List<Pack> topReservedPacks = new ArrayList<>();
        String query = "SELECT p.titre_pack, COUNT(rp.likes) AS total_likes " +
                "FROM Pack p " +
                "JOIN reservation_pack rp ON p.id = rp.pack_id " +
                "GROUP BY p.id " +
                "ORDER BY total_likes DESC " +
                "LIMIT ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, limit);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Pack pack = new Pack();
                    pack.setTitrePack(rs.getString("titre_pack"));
                    int totalLikes = rs.getInt("total_likes");
                    System.out.println("Titre du pack: " + pack.getTitrePack());
                    System.out.println("Nombre de likes: " + totalLikes);
                    System.out.println(); // Add a newline for separation

                    topReservedPacks.add(pack);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des packs les plus réservés : " + ex.getMessage());
        }

        return topReservedPacks;
    }
    public List<Pack> getTopReservedPacksWithParticipants(int limit) {
        List<Pack> topReservedPacks = new ArrayList<>();
        String query = "SELECT p.id, p.titre_pack, p.description_pack, p.prix_pack, p.image_pack, p.date_depart_pack, p.date_arrive_pack, p.nbvue " +
                "FROM Pack p " +
                "JOIN reservation_pack r ON p.id = r.pack_id " +
                "GROUP BY p.id " +
                "ORDER BY COUNT(r.id) DESC " +
                "LIMIT ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, limit);
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

                    topReservedPacks.add(pack);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des packs avec le plus grand nombre de participants : " + ex.getMessage());
        }

        return topReservedPacks;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (PreparedStatement pst = cnx.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                userList.add(user);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des utilisateurs : " + ex.getMessage());
        }
        return userList;
    }




}


