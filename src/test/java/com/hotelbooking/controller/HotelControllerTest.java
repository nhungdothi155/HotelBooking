package com.hotelbooking.controller;

import com.hotelbooking.dao.HotelDao;
import com.hotelbooking.dao.RoomDao;
import com.hotelbooking.dto.HotelSearchDTO;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.Nullable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
class HotelControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private HotelService hotelService;

    @ParameterizedTest
    @CsvSource("hello1,hello")
    void searchHotels( String name) throws Exception {
        //add hotel list
        List<Hotel> list = new ArrayList<>();
        Hotel hotel1 = new Hotel();
        hotel1.setName(name);
        list.add(hotel1);

        when(hotelService.searchHotel(anyInt(), anyInt(), any(HotelSearchDTO.class))).thenReturn(list);
        this.mockMvc.perform(post("/hotel/search?pageNumber=1&pageSize=2")
                .contentType("application/json")
                .content("{\"hotelId\": 1, \"roomId\": 1}"))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(name)));

    }
}