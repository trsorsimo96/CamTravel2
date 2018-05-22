package com.itravel.app.repository;

import com.itravel.app.domain.StateVoyage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StateVoyage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateVoyageRepository extends JpaRepository<StateVoyage, Long> {

}
