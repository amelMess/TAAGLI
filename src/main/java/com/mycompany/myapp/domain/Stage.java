package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Stage.
 */
@Entity
@Table(name = "stage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "sujet", nullable = false)
    private String sujet;

    @NotNull
    @Column(name = "duree_en_semaine", nullable = false)
    private Integer dureeEnSemaine;

    @Column(name = "service")
    private String service;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private ZonedDateTime dateDebut;

    @Column(name = "date_fin")
    private ZonedDateTime dateFin;

    @Column(name = "lang")
    private String lang;

    @Column(name = "materiel")
    private String materiel;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Etudiant etudiant;

    @ManyToOne
    private Entreprise entreprise;

    @ManyToOne
    private Encadrant encadrant;

    @ManyToOne
    private Enseignant enseignant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public Stage sujet(String sujet) {
        this.sujet = sujet;
        return this;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Integer getDureeEnSemaine() {
        return dureeEnSemaine;
    }

    public Stage dureeEnSemaine(Integer dureeEnSemaine) {
        this.dureeEnSemaine = dureeEnSemaine;
        return this;
    }

    public void setDureeEnSemaine(Integer dureeEnSemaine) {
        this.dureeEnSemaine = dureeEnSemaine;
    }

    public String getService() {
        return service;
    }

    public Stage service(String service) {
        this.service = service;
        return this;
    }

    public void setService(String service) {
        this.service = service;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public Stage dateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public Stage dateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getLang() {
        return lang;
    }

    public Stage lang(String lang) {
        this.lang = lang;
        return this;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMateriel() {
        return materiel;
    }

    public Stage materiel(String materiel) {
        this.materiel = materiel;
        return this;
    }

    public void setMateriel(String materiel) {
        this.materiel = materiel;
    }

    public String getDescription() {
        return description;
    }

    public Stage description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public Stage etudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        return this;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public Stage entreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
        return this;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Encadrant getEncadrant() {
        return encadrant;
    }

    public Stage encadrant(Encadrant encadrant) {
        this.encadrant = encadrant;
        return this;
    }

    public void setEncadrant(Encadrant encadrant) {
        this.encadrant = encadrant;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public Stage enseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        return this;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stage stage = (Stage) o;
        if(stage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, stage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Stage{" +
            "id=" + id +
            ", sujet='" + sujet + "'" +
            ", dureeEnSemaine='" + dureeEnSemaine + "'" +
            ", service='" + service + "'" +
            ", dateDebut='" + dateDebut + "'" +
            ", dateFin='" + dateFin + "'" +
            ", lang='" + lang + "'" +
            ", materiel='" + materiel + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
