package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Entreprise.
 */
@Entity
@Table(name = "entreprise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "siret", nullable = false)
    private Integer siret;

    @NotNull
    @Column(name = "service", nullable = false)
    private String service;

    @Column(name = "u_rl")
    private String uRl;

    @Column(name = "tel")
    private Integer tel;

    @OneToMany(mappedBy = "entreprise")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stage> stages = new HashSet<>();

    @OneToMany(mappedBy = "entreprise")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Adresse> adresses = new HashSet<>();

    @OneToMany(mappedBy = "entreprise")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Encadrant> encadrants = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Entreprise nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getSiret() {
        return siret;
    }

    public Entreprise siret(Integer siret) {
        this.siret = siret;
        return this;
    }

    public void setSiret(Integer siret) {
        this.siret = siret;
    }

    public String getService() {
        return service;
    }

    public Entreprise service(String service) {
        this.service = service;
        return this;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getuRl() {
        return uRl;
    }

    public Entreprise uRl(String uRl) {
        this.uRl = uRl;
        return this;
    }

    public void setuRl(String uRl) {
        this.uRl = uRl;
    }

    public Integer getTel() {
        return tel;
    }

    public Entreprise tel(Integer tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public Set<Stage> getStages() {
        return stages;
    }

    public Entreprise stages(Set<Stage> stages) {
        this.stages = stages;
        return this;
    }

    public Entreprise addStage(Stage stage) {
        stages.add(stage);
        stage.setEntreprise(this);
        return this;
    }

    public Entreprise removeStage(Stage stage) {
        stages.remove(stage);
        stage.setEntreprise(null);
        return this;
    }

    public void setStages(Set<Stage> stages) {
        this.stages = stages;
    }

    public Set<Adresse> getAdresses() {
        return adresses;
    }

    public Entreprise adresses(Set<Adresse> adresses) {
        this.adresses = adresses;
        return this;
    }

    public Entreprise addAdresse(Adresse adresse) {
        adresses.add(adresse);
        adresse.setEntreprise(this);
        return this;
    }

    public Entreprise removeAdresse(Adresse adresse) {
        adresses.remove(adresse);
        adresse.setEntreprise(null);
        return this;
    }

    public void setAdresses(Set<Adresse> adresses) {
        this.adresses = adresses;
    }

    public Set<Encadrant> getEncadrants() {
        return encadrants;
    }

    public Entreprise encadrants(Set<Encadrant> encadrants) {
        this.encadrants = encadrants;
        return this;
    }

    public Entreprise addEncadrant(Encadrant encadrant) {
        encadrants.add(encadrant);
        encadrant.setEntreprise(this);
        return this;
    }

    public Entreprise removeEncadrant(Encadrant encadrant) {
        encadrants.remove(encadrant);
        encadrant.setEntreprise(null);
        return this;
    }

    public void setEncadrants(Set<Encadrant> encadrants) {
        this.encadrants = encadrants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entreprise entreprise = (Entreprise) o;
        if(entreprise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entreprise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", siret='" + siret + "'" +
            ", service='" + service + "'" +
            ", uRl='" + uRl + "'" +
            ", tel='" + tel + "'" +
            '}';
    }
}
