package com.itravel.app.repository;

import com.itravel.app.domain.ModePayment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ModePayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModePaymentRepository extends JpaRepository<ModePayment, Long> {

}
