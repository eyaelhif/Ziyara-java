package services;

import entities.Guide;
import utils.MyDB;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceGuide implements IService<Guide> {
    private Connection con;

    public ServiceGuide() {
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Guide guide) throws SQLException {
        String req = "INSERT INTO guide (nom_guide, prenom_guide, langue, disponibilite, image_guide) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, guide.getNomGuide());
        pre.setString(2, guide.getPrenomGuide());
        pre.setString(3, guide.getLangue());
        pre.setString(4, guide.getDisponibilite()); // Now expecting a string
        pre.setString(5, guide.getImageGuide());
        pre.executeUpdate();
    }


    @Override
    public void modifier(Guide guide) throws SQLException {
        String req = "UPDATE guide SET nom_guide=?, prenom_guide=?, langue=?, disponibilite=?, image_guide=? WHERE id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, guide.getNomGuide());
        pre.setString(2, guide.getPrenomGuide());
        pre.setString(3, guide.getLangue());
        pre.setString(4, guide.getDisponibilite());
        pre.setString(5, guide.getImageGuide());
        pre.setInt(6, guide.getId());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(Guide guide) throws SQLException {
        PreparedStatement pre = con.prepareStatement("DELETE FROM guide WHERE id=?");
        pre.setInt(1, guide.getId());
        pre.executeUpdate();
    }




    @Override
    public List<Guide> afficher() throws SQLException {
        List <Guide> listGuides = new ArrayList<>();
        String req = "SELECT * FROM guide";
        Statement ste = con.createStatement();
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            Guide guide = new Guide();
            guide.setId(res.getInt("id"));
            guide.setNomGuide(res.getString("nom_guide"));
            guide.setPrenomGuide(res.getString("prenom_guide"));
            guide.setLangue(res.getString("langue"));
            guide.setDisponibilite(res.getString("disponibilite"));
            guide.setImageGuide(res.getString("image_guide"));
            listGuides.add(guide);
        }
        return listGuides;
    }
}
