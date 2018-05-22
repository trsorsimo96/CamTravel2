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
 * A Agency.
 */
@Entity
@Table(name = "agency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "agency")
public class Agency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "pcc", nullable = false)
    private String pcc;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tel")
    private String tel;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "om_number")
    private String omNumber;

    @Column(name = "momo_number")
    private String momoNumber;

    @Column(name = "solde")
    private Integer solde;

    @OneToMany(mappedBy = "agency")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "agency")
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

    public String getPcc() {
        return pcc;
    }

    public Agency pcc(String pcc) {
        this.pcc = pcc;
        return this;
    }

    public void setPcc(String pcc) {
        this.pcc = pcc;
    }

    public String getName() {
        return name;
    }

    public Agency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public Agency tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public Agency email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOmNumber() {
        return omNumber;
    }

    public Agency omNumber(String omNumber) {
        this.omNumber = omNumber;
        return this;
    }

    public void setOmNumber(String omNumber) {
        this.omNumber = omNumber;
    }

    public String getMomoNumber() {
        return momoNumber;
    }

    public Agency momoNumber(String momoNumber) {
        this.momoNumber = momoNumber;
        return this;
    }

    public void setMomoNumber(String momoNumber) {
        this.momoNumber = momoNumber;
    }

    public Integer getSolde() {
        return solde;
    }

    public Agency solde(Integer solde) {
        this.solde = solde;
        return this;
    }

    public void setSolde(Integer solde) {
        this.solde = solde;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public Agency bookings(Set<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    public Agency addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setAgency(this);
        return this;
    }

    public Agency removeBooking(Booking booking) {
        this.bookings.remove(booking);
        booking.setAgency(null);
        return this;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public Agency deposits(Set<Deposit> deposits) {
        this.deposits = deposits;
        return this;
    }

    public Agency addDeposit(Deposit deposit) {
        this.deposits.add(deposit);
        deposit.setAgency(this);
        return this;
    }

    public Agency removeDeposit(Deposit deposit) {
        this.deposits.remove(deposit);
        deposit.setAgency(null);
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
        Agency agency = (Agency) o;
        if (agency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agency{" +
            "id=" + getId() +
            ", pcc='" + getPcc() + "'" +
            ", name='" + getName() + "'" +
            ", tel='" + getTel() + "'" +
            ", email='" + getEmail() + "'" +
            ", omNumber='" + getOmNumber() + "'" +
            ", momoNumber='" + getMomoNumber() + "'" +
            ", solde=" + getSolde() +
            "}";
    }
}
