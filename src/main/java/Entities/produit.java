package Entities;

public class produit {

    private int id;
    private String nom;
    private String image;
    private int quantite;
    private float prix;

    private int catt_id;

    public produit() {
    }

    public produit( String nom, String image, int quantite, float prix, int catt_id) {
        this.nom = nom;
        this.image = image;
        this.quantite = quantite;
        this.prix = prix;
        this.catt_id = catt_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getCatt() {
        return catt_id;
    }

    public void setCatt(int catt_id) {
        this.catt_id = catt_id;
    }

    public String toString() {
        return "produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", image='" + image + '\'' +
                ", quantite=" + quantite +
                ", prix=" + prix +
                ", catt=" + catt_id +
                '}';
    }

}