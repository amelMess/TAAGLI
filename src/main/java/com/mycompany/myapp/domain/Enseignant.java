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
 * A Enseignant.
 */
@Entity
@Table(name = "enseignant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Enseignant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom_ens")
    private String nomEns;

    @Column(name = "prenom_ens")
    private String prenomEns;

    @Column(name = "tel_ens")
    private String telEns;

    @Column(name = "mail_ens")
    private String mailEns;

    @OneToMany(mappedBy = "enseignant")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stage> stages = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEns() {
        return nomEns;
    }

    public Enseignant nomEns(String nomEns) {
        this.nomEns = nomEns;
        return this;
    }

    public void setNomEns(String nomEns) {
        this.nomEns = nomEns;
    }

    public String getPrenomEns() {
        return prenomEns;
    }

    public Enseignant prenomEns(String prenomEns) {
        this.prenomEns = prenomEns;
        return this;
    }

    public void setPrenomEns(String prenomEns) {
        this.prenomEns = prenomEns;
    }

    public String getTelEns() {
        return telEns;
    }

    public Enseignant telEns(String telEns) {
        this.telEns = telEns;
        return this;
    }

    public void setTelEns(String telEns) {
        this.telEns = telEns;
    }

    public String getMailEns() {
        return mailEns;
    }

    public Enseignant mailEns(String mailEns) {
        this.mailEns = mailEns;
        return this;
    }

    public void setMailEns(String mailEns) {
        this.mailEns = mailEns;
    }

    public Set<Stage> getStages() {
        return stages;
    }

    public Enseignant stages(Set<Stage> stages) {
        this.stages = stages;
        return this;
    }

    public Enseignant addStage(Stage stage) {
        stages.add(stage);
        stage.setEnseignant(this);
        return this;
    }

    public Enseignant removeStage(Stage stage) {
        stages.remove(stage);
        stage.setEnseignant(null);
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
        Enseignant enseignant = (Enseignant) o;
        if(enseignant.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, enseignant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Enseignant{" +
            "id=" + id +
            ", nomEns='" + nomEns + "'" +
            ", prenomEns='" + prenomEns + "'" +
            ", telEns='" + telEns + "'" +
            ", mailEns='" + mailEns + "'" +
            '}';
    }
}
