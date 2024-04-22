package Entities;

import java.time.LocalDate;

public class Visite {
    private int id;
    private String titre;
    private String description_visite;
    private String date_visite;
    private double prix;
    private String imagev;

    public Visite() {}

    public Visite(String titre, String description_visite, String date_visite, double prix, String imagev) {
        this.titre = titre;
        this.description_visite = description_visite;
        this.date_visite = String.valueOf(date_visite);
        this.prix = prix;
        this.imagev = imagev;
    }

    public Visite(int id, String titre, String description_visite, String date_visite, double prix, String imagev) {
        this.id = id;
        this.titre = titre;
        this.description_visite = description_visite;
        this.date_visite = String.valueOf(date_visite);
        this.prix = prix;
        this.imagev = imagev;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription_visite() {
        return description_visite;
    }

    public void setDescription_visite(String description_visite) {
        this.description_visite = description_visite;
    }

    public LocalDate getDate_visite() {
        return LocalDate.parse(date_visite);
    }

    public void setDate_visite(String date_visite) {
        this.date_visite = date_visite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImagev() {
        return imagev;
    }

    public void setImagev(String imagev) {
        this.imagev = imagev;
    }

    @Override
    public String toString() {
        return "Visite{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description_visite='" + description_visite + '\'' +
                ", date_visite='" + date_visite + '\'' +
                ", prix=" + prix +
                ", imagev='" + imagev + '\'' +
                '}';
    }
}
