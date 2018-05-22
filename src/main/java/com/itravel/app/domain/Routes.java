package com.itravel.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Routes.
 */
@Entity
@Table(name = "routes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "routes")
public class Routes implements Serializable {

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

    @OneToOne(mappedBy = "voyage")
    @JsonIgnore
    private Voyage itinerary;

    @ManyToOne(optional = false)
    @NotNull
    private City origin;

    @ManyToOne(optional = false)
    @NotNull
    private City destination;

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

    public Routes code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Routes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Voyage getItinerary() {
        return itinerary;
    }

    public Routes itinerary(Voyage voyage) {
        this.itinerary = voyage;
        return this;
    }

    public void setItinerary(Voyage voyage) {
        this.itinerary = voyage;
    }

    public City getOrigin() {
        return origin;
    }

    public Routes origin(City city) {
        this.origin = city;
        return this;
    }

    public void setOrigin(City city) {
        this.origin = city;
    }

    public City getDestination() {
        return destination;
    }

    public Routes destination(City city) {
        this.destination = city;
        return this;
    }

    public void setDestination(City city) {
        this.destination = city;
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
        Routes routes = (Routes) o;
        if (routes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), routes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Routes{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
