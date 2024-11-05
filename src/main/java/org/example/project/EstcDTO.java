package org.example.project;

public class EstcDTO {
    private String impactEnvironnemental;  // Corresponds to ?impact_environnemental
    private String lieu;                     // Corresponds to ?lieu
    private String nom;                      // Corresponds to ?nom
    private String description;              // Corresponds to ?description

    // Getter pour impactEnvironnemental
    public String getImpactEnvironnemental() {
        return impactEnvironnemental;
    }

    // Setter pour impactEnvironnemental
    public void setImpactEnvironnemental(String impactEnvironnemental) {
        this.impactEnvironnemental = impactEnvironnemental;
    }

    // Getter pour lieu
    public String getLieu() {
        return lieu;
    }

    // Setter pour lieu
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    // Getter pour nom
    public String getNom() {
        return nom;
    }

    // Setter pour nom
    public void setNom(String nom) {
        this.nom = nom;
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
