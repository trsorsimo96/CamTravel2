package com.itravel.app.repository;

import com.itravel.app.domain.TypePassenger;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypePassenger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePassengerRepository extends JpaRepository<TypePassenger, Long> {

}
