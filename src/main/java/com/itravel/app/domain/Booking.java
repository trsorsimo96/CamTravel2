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
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "pnr", nullable = false)
    private String pnr;

    @NotNull
    @Column(name = "booking_date", nullable = false)
    private ZonedDateTime bookingDate;

    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Passenger> passengers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "booking_booking",
               joinColumns = @JoinColumn(name="bookings_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="bookings_id", referencedColumnName="id"))
    private Set<Voyage> bookings = new HashSet<>();

    @ManyToOne
    private Agency agency;

    @ManyToOne
    private Company company;

    @ManyToOne
    private ModePayment payment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPnr() {
        return pnr;
    }

    public Booking pnr(String pnr) {
        this.pnr = pnr;
        return this;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public ZonedDateTime getBookingDate() {
        return bookingDate;
    }

    public Booking bookingDate(ZonedDateTime bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public void setBookingDate(ZonedDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public Booking passengers(Set<Passenger> passengers) {
        this.passengers = passengers;
        return this;
    }

    public Booking addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
        passenger.setBooking(this);
        return this;
    }

    public Booking removePassenger(Passenger passenger) {
        this.passengers.remove(passenger);
        passenger.setBooking(null);
        return this;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Set<Voyage> getBookings() {
        return bookings;
    }

    public Booking bookings(Set<Voyage> voyages) {
        this.bookings = voyages;
        return this;
    }

    public Booking addBooking(Voyage voyage) {
        this.bookings.add(voyage);
        voyage.getVoyages().add(this);
        return this;
    }

    public Booking removeBooking(Voyage voyage) {
        this.bookings.remove(voyage);
        voyage.getVoyages().remove(this);
        return this;
    }

    public void setBookings(Set<Voyage> voyages) {
        this.bookings = voyages;
    }

    public Agency getAgency() {
        return agency;
    }

    public Booking agency(Agency agency) {
        this.agency = agency;
        return this;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Company getCompany() {
        return company;
    }

    public Booking company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ModePayment getPayment() {
        return payment;
    }

    public Booking payment(ModePayment modePayment) {
        this.payment = modePayment;
        return this;
    }

    public void setPayment(ModePayment modePayment) {
        this.payment = modePayment;
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
        Booking booking = (Booking) o;
        if (booking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), booking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", pnr='" + getPnr() + "'" +
            ", bookingDate='" + getBookingDate() + "'" +
            "}";
    }
}
