package Entities;

public class User {
    private int id;
    private String email;
    private String roles;

    public User() {
    }

    public User(String email, String roles) {
        this.email = email;
        this.roles = roles;
    }

    public User(int id, String email, String roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
