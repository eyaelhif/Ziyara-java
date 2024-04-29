package models;

public class Rememberme {

    int id;
    int iduser;

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Rememberme(int id, int iduser) {
        this.id = id;
        this.iduser = iduser;
    }

    public Rememberme(int iduser) {
        this.iduser = iduser;
    }


    public Rememberme() {}


}
