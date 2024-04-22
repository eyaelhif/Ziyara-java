/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;

public class User {
    private Long id;
    private String email;
    private List<ReservationPack> reservationPacks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ReservationPack> getReservationPacks() {
        return reservationPacks;
    }

    public void setReservationPacks(List<ReservationPack> reservationPacks) {
        this.reservationPacks = reservationPacks;
    }

    public void addReservationPack(ReservationPack reservationPack) {
        this.reservationPacks.add(reservationPack);
        reservationPack.setUser(this);
    }

    public void removeReservationPack(ReservationPack reservationPack) {
        this.reservationPacks.remove(reservationPack);
        reservationPack.setUser(null);
    }

    @Override
    public String toString() {
        return email;
    }


}

