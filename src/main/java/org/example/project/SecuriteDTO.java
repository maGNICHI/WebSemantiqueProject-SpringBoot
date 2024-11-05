package org.example.project;
import java.time.LocalDate;

public class SecuriteDTO {
    private String sujet;
    private String status;
    private String description;
    private LocalDate dateReclamation;
    private LocalDate dateTraitement;

    // Getter pour sujet
    public String getSujet() {
        return sujet;
    }

    // Setter pour sujet
    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    // Getter pour status
    public String getStatus() {
        return status;
    }

    // Setter pour status
    public void setStatus(String status) {
        this.status = status;
    }

    // Getter pour description
    public String getDescription() {
        return description;
    }

    // Setter pour description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter pour dateReclamation
    public LocalDate getDateReclamation() {
        return dateReclamation;
    }

    // Setter pour dateReclamation
    public void setDateReclamation(LocalDate dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    // Getter pour dateTraitement
    public LocalDate getDateTraitement() {
        return dateTraitement;
    }

    // Setter pour dateTraitement
    public void setDateTraitement(LocalDate dateTraitement) {
        this.dateTraitement = dateTraitement;
    }
}
