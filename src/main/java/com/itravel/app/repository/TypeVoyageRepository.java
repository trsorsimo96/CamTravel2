package com.itravel.app.repository;

import com.itravel.app.domain.TypeVoyage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeVoyage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeVoyageRepository extends JpaRepository<TypeVoyage, Long> {

}
