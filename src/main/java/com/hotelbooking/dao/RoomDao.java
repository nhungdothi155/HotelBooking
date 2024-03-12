package com.hotelbooking.dao;

import com.hotelbooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomDao extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r where r.roomId =:roomId and r.hotel.hotelId =:hotelId ")
    public Optional<Room> findRoomByRoomIdAndHotelId(Long roomId, Long hotelId);

}
