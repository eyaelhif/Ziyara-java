/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Objects;

public class Guide {
    private Long id;

    @Override
    public String toString() {
        return nomGuide;
    }

    private String nomGuide;
    private String prenomGuide;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomGuide() {
        return nomGuide;
    }

    public void setNomGuide(String nomGuide) {
        this.nomGuide = nomGuide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guide guide = (Guide) o;
        return Objects.equals(getId(), guide.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String getPrenomGuide() {
        return prenomGuide;
    }
    public void setPrenomGuide(String prenomGuide)
    {
        this.prenomGuide=prenomGuide;
    }
}

