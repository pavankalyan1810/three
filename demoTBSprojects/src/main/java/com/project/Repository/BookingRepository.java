package com.project.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
