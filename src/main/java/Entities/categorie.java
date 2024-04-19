package Entities;

public class categorie {

    private int id;
    private String description;
    private String type;

    public categorie() {
    }

    public categorie(int id, String description, String type) {
        this.id = id;
        this.description = description;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "categorie{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
