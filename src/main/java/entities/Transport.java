/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Objects;

public class Transport {
    private Long id;

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    private int prixTransport;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transport transport = (Transport) o;
        return Objects.equals(getId(), transport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrixTransport() {
        return prixTransport;
    }

    public void setPrixTransport(int prixTransport) {
        this.prixTransport = prixTransport;
    }
}
