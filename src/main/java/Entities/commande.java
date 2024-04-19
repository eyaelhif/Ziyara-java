package Entities;

import java.time.LocalDateTime;

public class commande {
    private int id;
    private LocalDateTime date;
    private String statut;
    private double total;
    private String type_paiement;
    private String iduser;

    public commande() {
    }

    public commande(int id, LocalDateTime date, String statut, double total, String type_paiement, String iduser) {
        this.id = id;
        this.date = LocalDateTime.now();
        this.statut = statut;
        this.total = total;
        this.type_paiement = type_paiement;
        this.iduser = iduser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = LocalDateTime.now();
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getType_paiement() {
        return type_paiement;
    }

    public void setType_paiement(String type_paiement) {
        this.type_paiement = type_paiement;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    @Override
    public String toString() {
        return "commande{" +
                "id=" + id +
                ", date=" + date +
                ", statut='" + statut + '\'' +
                ", total=" + total +
                ", type_paiement='" + type_paiement + '\'' +
                ", iduser='" + iduser + '\'' +
                '}';
    }
}