package com.itravel.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Voyage.
 */
@Entity
@Table(name = "voyage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "voyage")
public class Voyage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotNull
    @Column(name = "datedepart", nullable = false)
    private ZonedDateTime datedepart;

    @NotNull
    @Column(name = "datearrive", nullable = false)
    private ZonedDateTime datearrive;

    @OneToOne
    @JoinColumn(unique = true)
    private Routes voyage;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Train train;

    @ManyToOne
    private TypeVoyage type;

    @ManyToOne
    private StateVoyage state;

    @ManyToMany(mappedBy = "bookings")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Booking> voyages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Voyage numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ZonedDateTime getDatedepart() {
        return datedepart;
    }

    public Voyage datedepart(ZonedDateTime datedepart) {
        this.datedepart = datedepart;
        return this;
    }

    public void setDatedepart(ZonedDateTime datedepart) {
        this.datedepart = datedepart;
    }

    public ZonedDateTime getDatearrive() {
        return datearrive;
    }

    public Voyage datearrive(ZonedDateTime datearrive) {
        this.datearrive = datearrive;
        return this;
    }

    public void setDatearrive(ZonedDateTime datearrive) {
        this.datearrive = datearrive;
    }

    public Routes getVoyage() {
        return voyage;
    }

    public Voyage voyage(Routes routes) {
        this.voyage = routes;
        return this;
    }

    public void setVoyage(Routes routes) {
        this.voyage = routes;
    }

    public Car getCar() {
        return car;
    }

    public Voyage car(Car car) {
        this.car = car;
        return this;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Train getTrain() {
        return train;
    }

    public Voyage train(Train train) {
        this.train = train;
        return this;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public TypeVoyage getType() {
        return type;
    }

    public Voyage type(TypeVoyage typeVoyage) {
        this.type = typeVoyage;
        return this;
    }

    public void setType(TypeVoyage typeVoyage) {
        this.type = typeVoyage;
    }

    public StateVoyage getState() {
        return state;
    }

    public Voyage state(StateVoyage stateVoyage) {
        this.state = stateVoyage;
        return this;
    }

    public void setState(StateVoyage stateVoyage) {
        this.state = stateVoyage;
    }

    public Set<Booking> getVoyages() {
        return voyages;
    }

    public Voyage voyages(Set<Booking> bookings) {
        this.voyages = bookings;
        return this;
    }

    public Voyage addVoyage(Booking booking) {
        this.voyages.add(booking);
        booking.getBookings().add(this);
        return this;
    }

    public Voyage removeVoyage(Booking booking) {
        this.voyages.remove(booking);
        booking.getBookings().remove(this);
        return this;
    }

    public void setVoyages(Set<Booking> bookings) {
        this.voyages = bookings;
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
        Voyage voyage = (Voyage) o;
        if (voyage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voyage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Voyage{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", datedepart='" + getDatedepart() + "'" +
            ", datearrive='" + getDatearrive() + "'" +
            "}";
    }
}
