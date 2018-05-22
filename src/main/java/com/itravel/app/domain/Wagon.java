package com.itravel.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Wagon.
 */
@Entity
@Table(name = "wagon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "wagon")
public class Wagon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "place")
    private Integer place;

    @ManyToOne(optional = false)
    @NotNull
    private ModelCar model;

    @ManyToOne(optional = false)
    @NotNull
    private Train train;

    @ManyToOne(optional = false)
    @NotNull
    private Classe classe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Wagon code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Wagon name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlace() {
        return place;
    }

    public Wagon place(Integer place) {
        this.place = place;
        return this;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public ModelCar getModel() {
        return model;
    }

    public Wagon model(ModelCar modelCar) {
        this.model = modelCar;
        return this;
    }

    public void setModel(ModelCar modelCar) {
        this.model = modelCar;
    }

    public Train getTrain() {
        return train;
    }

    public Wagon train(Train train) {
        this.train = train;
        return this;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Classe getClasse() {
        return classe;
    }

    public Wagon classe(Classe classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wagon wagon = (Wagon) o;
        if (wagon.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wagon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Wagon{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", place=" + getPlace() +
            "}";
    }
}
