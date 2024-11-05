package org.example.project;

public class AvionDTO {
    private String avion;
    private Integer prix;
    private String description;

    // Getter pour avion
    public String getAvion() {
        return avion;
    }

    // Setter pour avion
    public void setAvion(String avion) {
        this.avion = avion;
    }

    // Getter pour prix
    public Integer getPrix() {
        return prix;
    }

    // Setter pour prix
    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    // Getter pour description
    public String getDescription() {
        return description;
    }

    // Setter pour description
    public void setDescription(String description) {
        this.description = description;
    }
}