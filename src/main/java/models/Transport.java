package models;

import java.util.Date;

public class Transport {
    private int idTransport;
    private String imageTransport;

    private int typeTransport;

    @Override
    public String toString() {
        return "Transport{" +
                "idTransport=" + idTransport +
                ", imageTransport='" + imageTransport + '\'' +
                ", typeTransport=" + typeTransport +
                ", dateTransport=" + dateTransport +
                ", prixTransport=" + prixTransport +
                ", description='" + description + '\'' +
                '}';
    }

    private Date dateTransport;

    private double prixTransport;
    private String description;

    public int getTypeTransport() {
        return typeTransport;
    }

    public void setTypeTransport(int typeTransport) {
        this.typeTransport = typeTransport;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageTransport() {
        return imageTransport;
    }

    public void setImageTransport(String imageTransport) {
        this.imageTransport = imageTransport;
    }

    // Constructor
    public Transport(int idTransport,int typeTransport, Date dateTransport, double prixTransport,String description, String imageTransport) {
        this.idTransport = idTransport;
        this.typeTransport = typeTransport;
        this.dateTransport = dateTransport;
        this.prixTransport = prixTransport;
        this.description   = description;
        this.imageTransport = imageTransport;
    }
    public Transport(int typeTransport, Date dateTransport, double prixTransport,String description) {

        //this.imageTransport = imageTransport;
        this.typeTransport = typeTransport;
        this.dateTransport = dateTransport;
        this.prixTransport = prixTransport;
        this.description   = description;
    }
     public Transport(){};
    // Getters and Setters
    public int getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(int idTransport) {
        this.idTransport = idTransport;
    }

   /* public String getImageTransport() {
        return imageTransport;
    }

    public void setImageTransport(String imageTransport) {
        this.imageTransport = imageTransport;
    }*/

    /*public int getTypeTransport() {
        return typeTransport;
    }

    public void setTypeTransport(int typeTransport) {
        this.typeTransport = typeTransport;
    }*/

    public Date getDateTransport() {
        return dateTransport;
    }

    public void setDateTransport(Date dateTransport) {
        this.dateTransport = dateTransport;
    }

    public double getPrixTransport() {
        return prixTransport;
    }

    public void setPrixTransport(double prixTransport) {
        this.prixTransport = prixTransport;
    }
}
