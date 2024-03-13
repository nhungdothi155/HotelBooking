package com.hotelbooking.service.impl;

import com.hotelbooking.dto.HotelSearchDTO;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class HotelServiceImplTest {

    @Autowired
    private HotelService hotelService;


    @Test
    public void should_not_throw_exception(){
        int pageNumber =1;
        int pageSize =5;

        HotelSearchDTO hotelSearchDTO = new HotelSearchDTO()
                .setAddress("vietnam");

        List<Hotel> hotelList = hotelService.searchHotel(pageNumber,pageSize,hotelSearchDTO);
        assertNotNull(hotelList !=null && hotelList.size()>0);

    }

}