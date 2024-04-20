package models;

import java.util.Date;

public class ReservationTransport {
    private int idReservationTransport;
    private int idUser;
    private int idTransport;
    private Date dateReservationTransport;

    @Override
    public String toString() {
        return "ReservationTransport{" +
                "idReservationTransport=" + idReservationTransport +
                ", idUser=" + idUser +
                ", idTransport=" + idTransport +
                ", dateReservationTransport=" + dateReservationTransport +
                ", pointDepart='" + pointDepart + '\'' +
                ", pointArrive='" + pointArrive + '\'' +
                '}';
    }

    private String pointDepart;

    public ReservationTransport() {
    }

    private String pointArrive;

    // Constructor
    public ReservationTransport(int idReservationTransport, int idUser, int idTransport, Date dateReservationTransport, String pointDepart, String pointArrive) {
        this.idReservationTransport = idReservationTransport;
        this.idUser = idUser;
        this.idTransport = idTransport;
        this.dateReservationTransport = dateReservationTransport;
        this.pointDepart = pointDepart;
        this.pointArrive = pointArrive;
    }

    // Getters and Setters
    public int getIdReservationTransport() {
        return idReservationTransport;
    }

    public void setIdReservationTransport(int idReservationTransport) {
        this.idReservationTransport = idReservationTransport;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(int idTransport) {
        this.idTransport = idTransport;
    }

    public Date getDateReservationTransport() {
        return dateReservationTransport;
    }

    public void setDateReservationTransport(Date dateReservationTransport) {
        this.dateReservationTransport = dateReservationTransport;
    }

    public String getPointDepart() {
        return pointDepart;
    }

    public void setPointDepart(String pointDepart) {
        this.pointDepart = pointDepart;
    }

    public String getPointArrive() {
        return pointArrive;
    }

    public void setPointArrive(String pointArrive) {
        this.pointArrive = pointArrive;
    }
}
