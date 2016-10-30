package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Encadrant.
 */
@Entity
@Table(name = "encadrant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Encadrant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom_enc")
    private String nomEnc;

    @Column(name = "prenom_enc")
    private String prenomEnc;

    @Column(name = "tel_enc")
    private String telEnc;

    @Column(name = "mail_enc")
    private String mailEnc;

    @ManyToOne
    private Entreprise entreprise;

    @OneToMany(mappedBy = "encadrant")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stage> stages = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEnc() {
        return nomEnc;
    }

    public Encadrant nomEnc(String nomEnc) {
        this.nomEnc = nomEnc;
        return this;
    }

    public void setNomEnc(String nomEnc) {
        this.nomEnc = nomEnc;
    }

    public String getPrenomEnc() {
        return prenomEnc;
    }

    public Encadrant prenomEnc(String prenomEnc) {
        this.prenomEnc = prenomEnc;
        return this;
    }

    public void setPrenomEnc(String prenomEnc) {
        this.prenomEnc = prenomEnc;
    }

    public String getTelEnc() {
        return telEnc;
    }

    public Encadrant telEnc(String telEnc) {
        this.telEnc = telEnc;
        return this;
    }

    public void setTelEnc(String telEnc) {
        this.telEnc = telEnc;
    }

    public String getMailEnc() {
        return mailEnc;
    }

    public Encadrant mailEnc(String mailEnc) {
        this.mailEnc = mailEnc;
        return this;
    }

    public void setMailEnc(String mailEnc) {
        this.mailEnc = mailEnc;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public Encadrant entreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
        return this;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Set<Stage> getStages() {
        return stages;
    }

    public Encadrant stages(Set<Stage> stages) {
        this.stages = stages;
        return this;
    }

    public Encadrant addStage(Stage stage) {
        stages.add(stage);
        stage.setEncadrant(this);
        return this;
    }

    public Encadrant removeStage(Stage stage) {
        stages.remove(stage);
        stage.setEncadrant(null);
        return this;
    }

    public void setStages(Set<Stage> stages) {
        this.stages = stages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Encadrant encadrant = (Encadrant) o;
        if(encadrant.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, encadrant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Encadrant{" +
            "id=" + id +
            ", nomEnc='" + nomEnc + "'" +
            ", prenomEnc='" + prenomEnc + "'" +
            ", telEnc='" + telEnc + "'" +
            ", mailEnc='" + mailEnc + "'" +
            '}';
    }
}
