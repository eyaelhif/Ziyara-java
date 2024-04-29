package entities;

public class Guide {
    private int id;
    private String nomGuide;
    private String prenomGuide;
    private String langue;
    private String disponibilite;  // Changed from boolean to String
    private String imageGuide;

    // Constructor
    public Guide(int id, String nomGuide, String prenomGuide, String langue, String disponibilite, String imageGuide) {
        this.id = id;
        this.nomGuide = nomGuide;
        this.prenomGuide = prenomGuide;
        this.langue = langue;
        this.disponibilite = disponibilite;
        this.imageGuide = imageGuide;
    }

    public Guide() {
        // Default constructor
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomGuide() {
        return nomGuide;
    }

    public void setNomGuide(String nomGuide) {
        this.nomGuide = nomGuide;
    }

    public String getPrenomGuide() {
        return prenomGuide;
    }

    public void setPrenomGuide(String prenomGuide) {
        this.prenomGuide = prenomGuide;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getImageGuide() {
        return imageGuide;
    }

    public void setImageGuide(String imageGuide) {
        this.imageGuide = imageGuide;
    }
}
