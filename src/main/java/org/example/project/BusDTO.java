package org.example.project;

public class BusDTO {
    private String bus;
    private Integer prix;
    private String description;

    // Getter pour bus
    public String getBus() {
        return bus;
    }

    // Setter pour bus
    public void setBus(String bus) {
        this.bus = bus;
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