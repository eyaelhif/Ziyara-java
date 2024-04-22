/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;
import java.util.List;

public class Pack {
    private int id;
    private String titrePack;
    private String descriptionPack;
    private int prixPack;
    private String imagePack;
    private Date dateDepartPack;
    private Date dateArrivePack;
    private int nbvue;
    private List<ReservationPack> reservations;
    private Transport transports;
    private Guide guide;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitrePack() {
        return titrePack;
    }

    public void setTitrePack(String titrePack) {
        this.titrePack = titrePack;
    }

    public String getDescriptionPack() {
        return descriptionPack;
    }

    public void setDescriptionPack(String descriptionPack) {
        this.descriptionPack = descriptionPack;
    }

    public int getPrixPack() {
        return prixPack;
    }

    public void setPrixPack(int prixPack) {
        this.prixPack = prixPack;
    }

    public String getImagePack() {
        return imagePack;
    }

    public void setImagePack(String imagePack) {
        this.imagePack = imagePack;
    }

    public Date getDateDepartPack() {
        return dateDepartPack;
    }

    public void setDateDepartPack(Date dateDepartPack) {
        this.dateDepartPack = dateDepartPack;
    }

    public Date getDateArrivePack() {
        return dateArrivePack;
    }

    public void setDateArrivePack(Date dateArrivePack) {
        this.dateArrivePack = dateArrivePack;
    }

    public int getNbvue() {
        return nbvue;
    }

    public void setNbvue(int nbvue) {
        this.nbvue = nbvue;
    }

    public List<ReservationPack> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationPack> reservations) {
        this.reservations = reservations;
    }

    public Transport getTransports() {
        return transports;
    }

    public void setTransports(Transport transports) {
        this.transports = transports;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    @Override
    public String toString() {
        return titrePack;

    }


}

