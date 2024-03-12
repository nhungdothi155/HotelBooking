package com.hotelbooking.dao;

import com.hotelbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingDao extends JpaRepository<Booking, Long> {

     @Query("select r from Booking r " +
             "where r.room.roomId=:roomId " +
             "and ((r.checkInTime<= :startTime and r.checkOutTime>= :startTime)" +
             " or (r.checkInTime<= :endTime and r.checkOutTime>= :endTime)" +
             " or (r.checkInTime between :startTime and :endTime) " +
             " or (r.checkOutTime between :startTime and :endTime) )")
     public List<Booking> findOverlapBookingByStartTimeAndEndTime(@Param("roomId")Long roomId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

     @Query("select r from Booking r " +
             "where r.room.roomId=:roomId and r.bookingId <> :bookingId " +
             "and ((r.checkInTime<= :startTime and r.checkOutTime>= :startTime)" +
             " or (r.checkInTime<= :endTime and r.checkOutTime>= :endTime)" +
             " or (r.checkInTime between :startTime and :endTime) " +
             " or (r.checkOutTime between :startTime and :endTime) )")
     public List<Booking> findOverlapBookingByStartTimeAndEndTimeAndNotEqualCurrentBooking(@Param("roomId")Long roomId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime,
                                                                                           @Param("bookingId") Long bookingId);

     @Query("select r from Booking r WHERE r.bookingId=:bookingId and r.room.roomId=:roomId ")
     public Optional<Booking> findBookingByRoomIdAndBookingId( @Param("roomId") Long roomId,@Param("bookingId") Long bookingId);


}
