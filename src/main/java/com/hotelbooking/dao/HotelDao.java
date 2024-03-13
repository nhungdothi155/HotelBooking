package com.hotelbooking.dao;

import com.hotelbooking.dto.HotelSearchDTO;
import com.hotelbooking.entity.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelDao {
    public List<Hotel> searchHotel(int pageNumber, int pageSize, HotelSearchDTO hotelSearchDTO);

    Optional<Hotel> findHotelByHotelId(Long hotel_id);
}
