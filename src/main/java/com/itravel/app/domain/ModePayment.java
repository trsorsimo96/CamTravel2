package com.itravel.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ModePayment.
 */
@Entity
@Table(name = "mode_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "modepayment")
public class ModePayment implements Serializable {

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

    @OneToMany(mappedBy = "payment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "payment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Deposit> deposits = new HashSet<>();

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

    public ModePayment code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public ModePayment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public ModePayment bookings(Set<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    public ModePayment addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setPayment(this);
        return this;
    }

    public ModePayment removeBooking(Booking booking) {
        this.bookings.remove(booking);
        booking.setPayment(null);
        return this;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public ModePayment deposits(Set<Deposit> deposits) {
        this.deposits = deposits;
        return this;
    }

    public ModePayment addDeposit(Deposit deposit) {
        this.deposits.add(deposit);
        deposit.setPayment(this);
        return this;
    }

    public ModePayment removeDeposit(Deposit deposit) {
        this.deposits.remove(deposit);
        deposit.setPayment(null);
        return this;
    }

    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
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
        ModePayment modePayment = (ModePayment) o;
        if (modePayment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modePayment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModePayment{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
