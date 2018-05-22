package com.itravel.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CompanyAgency.
 */
@Entity
@Table(name = "company_agency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "companyagency")
public class CompanyAgency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "company", nullable = false)
    private String company;

    @NotNull
    @Column(name = "agency", nullable = false)
    private String agency;

    @Column(name = "commision")
    private Integer commision;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public CompanyAgency company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAgency() {
        return agency;
    }

    public CompanyAgency agency(String agency) {
        this.agency = agency;
        return this;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public Integer getCommision() {
        return commision;
    }

    public CompanyAgency commision(Integer commision) {
        this.commision = commision;
        return this;
    }

    public void setCommision(Integer commision) {
        this.commision = commision;
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
        CompanyAgency companyAgency = (CompanyAgency) o;
        if (companyAgency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyAgency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyAgency{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", agency='" + getAgency() + "'" +
            ", commision=" + getCommision() +
            "}";
    }
}
