package com.hotelbooking.dao;

import com.hotelbooking.entity.BookingStatus;
import com.hotelbooking.enums.BookingStatusCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingStatusDao extends JpaRepository< BookingStatus,Long> {
     BookingStatus findByCode(BookingStatusCode code);

}
