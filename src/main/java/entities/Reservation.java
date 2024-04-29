package entities;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int guideId;
    private LocalDate dateReservationGuide;
    private int duree; // Assuming 'duree' is the duration in hours

    // Constructor without id, for creating new instances
    public Reservation(int guideId, LocalDate dateReservationGuide, int duree) {
        this.guideId = guideId;
        this.dateReservationGuide = dateReservationGuide;
        this.duree = duree;
    }

    // Constructor with id, for retrieving instances from the database
    public Reservation(int id, int guideId, LocalDate dateReservationGuide, int duree) {
        this.id = id;
        this.guideId = guideId;
        this.dateReservationGuide = dateReservationGuide;
        this.duree = duree;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public LocalDate getDateReservationGuide() {
        return dateReservationGuide;
    }

    public void setDateReservationGuide(LocalDate dateReservationGuide) {
        this.dateReservationGuide = dateReservationGuide;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", guideId=" + guideId +
                ", dateReservationGuide=" + dateReservationGuide +
                ", duree=" + duree +
                '}';
    }
}
