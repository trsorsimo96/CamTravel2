package com.itravel.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CompanyClasse.
 */
@Entity
@Table(name = "company_classe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "companyclasse")
public class CompanyClasse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "company", nullable = false)
    private String company;

    @NotNull
    @Column(name = "classe", nullable = false)
    private String classe;

    @Column(name = "price")
    private Integer price;

    @Column(name = "name")
    private String name;

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

    public CompanyClasse company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getClasse() {
        return classe;
    }

    public CompanyClasse classe(String classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getPrice() {
        return price;
    }

    public CompanyClasse price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public CompanyClasse name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
        CompanyClasse companyClasse = (CompanyClasse) o;
        if (companyClasse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyClasse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyClasse{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", classe='" + getClasse() + "'" +
            ", price=" + getPrice() +
            ", name='" + getName() + "'" +
            "}";
    }
}
