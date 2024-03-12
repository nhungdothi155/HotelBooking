package com.hotelbooking.service.impl;

import com.hotelbooking.dao.HotelDao;
import com.hotelbooking.dto.HotelSearchDTO;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelDao hotelDao;

    @Override
    public List<Hotel> searchHotel(int pageNumber, int pageSize, HotelSearchDTO hotelSearchDTO) {
        List<Hotel> hotelList = hotelDao.searchHotel(pageNumber, pageSize, hotelSearchDTO);
        return hotelList;
    }

}
