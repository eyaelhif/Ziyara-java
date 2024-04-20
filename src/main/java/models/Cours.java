package models;

public class Cours {
    private int id;
    private int user_id;
    private String titre;
    private String description;
    private String duree;
    private String categorie;
    private int prix;
    private int nbplace;

    // Constructor
    public Cours(int id, int user_id, String titre, String description, String duree, String categorie, int prix, int nbplace) {
        this.id = id;
        this.user_id = user_id;
        this.titre = titre;
        this.description = description;
        this.duree = duree;
        this.categorie = categorie;
        this.prix = prix;
        this.nbplace = nbplace;
    }

    public Cours() {
        // Initialize any default values if needed
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getNbplace() {
        return nbplace;
    }

    public void setNbplace(int nbplace) {
        this.nbplace = nbplace;
    }
}

