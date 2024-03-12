package com.hotelbooking.service;

import com.hotelbooking.dto.HotelSearchDTO;
import com.hotelbooking.entity.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {

      /**
       * Search for a hotel by several filter(name,description,city,country ....)
       * @param pageNumber
       * @param pageSize
       * @param hotelSearchDTO
       * @return
       */
      public List<Hotel> searchHotel(int pageNumber, int pageSize, HotelSearchDTO hotelSearchDTO);

}
