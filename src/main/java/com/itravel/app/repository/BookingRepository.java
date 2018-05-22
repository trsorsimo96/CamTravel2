package com.itravel.app.repository;

import com.itravel.app.domain.Booking;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Booking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select distinct booking from Booking booking left join fetch booking.bookings")
    List<Booking> findAllWithEagerRelationships();

    @Query("select booking from Booking booking left join fetch booking.bookings where booking.id =:id")
    Booking findOneWithEagerRelationships(@Param("id") Long id);

}
