package com.itravel.app.repository;

import com.itravel.app.domain.ModelCar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ModelCar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelCarRepository extends JpaRepository<ModelCar, Long> {

}
