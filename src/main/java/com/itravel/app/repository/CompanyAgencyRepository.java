package com.itravel.app.repository;

import com.itravel.app.domain.CompanyAgency;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyAgency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyAgencyRepository extends JpaRepository<CompanyAgency, Long> {

}
