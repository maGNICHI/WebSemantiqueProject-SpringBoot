package org.example.project;

public class RandoLoupDTO {
    private String impactEnvironnemental; // Impact environnemental
    private String lieu; // Lieu de l'événement
    private String description; // Description de l'événement
    private String nom; // Nom de l'événement

    // Getters et Setters
    public String getImpactEnvironnemental() {
        return impactEnvironnemental;
    }

    public void setImpactEnvironnemental(String impactEnvironnemental) {
        this.impactEnvironnemental = impactEnvironnemental;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
