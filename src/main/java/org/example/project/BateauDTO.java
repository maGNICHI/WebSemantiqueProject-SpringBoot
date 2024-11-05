package org.example.project;

public class BateauDTO {
    private String bateau;
    private Integer prix;
    private String description;

    // Getter pour bateau
    public String getBateau() {
        return bateau;
    }

    // Setter pour bateau
    public void setBateau(String bateau) {
        this.bateau = bateau;
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
