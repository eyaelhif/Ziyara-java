package models;

public class CategorieTransport {
    private int idCategorieTransport;
    private String nomCategorieTransport;

    @Override
    public String toString() {
        return "CategorieTransport{" +
                "idCategorieTransport=" + idCategorieTransport +
                ", nomCategorieTransport='" + nomCategorieTransport + '\'' +
                '}';
    }

    // Constructor
    public CategorieTransport(int idCategorieTransport, String nomCategorieTransport) {
        this.idCategorieTransport = idCategorieTransport;
        this.nomCategorieTransport = nomCategorieTransport;
    }

    // Getters and Setters
    public int getIdCategorieTransport() {
        return idCategorieTransport;
    }

    public void setIdCategorieTransport(int idCategorieTransport) {
        this.idCategorieTransport = idCategorieTransport;
    }

    public String getNomCategorieTransport() {
        return nomCategorieTransport;
    }

    public void setNomCategorieTransport(String nomCategorieTransport) {
        this.nomCategorieTransport = nomCategorieTransport;
    }
}
