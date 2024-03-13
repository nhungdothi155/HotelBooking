package com.hotelbooking.controller;

import com.hotelbooking.dao.BookingDao;
import com.hotelbooking.dto.BookingDTO;
import com.hotelbooking.entity.Booking;
import com.hotelbooking.enums.BookingStatusCode;
import com.hotelbooking.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingDao bookingDao;

    @SpyBean
    private BookingService bookingService;

    @Test
    void upsertBooking() throws Exception {
        doNothing().when(bookingService).createBooking(anyLong(), anyLong(), any(BookingDTO.class));
        this.mockMvc.perform(post("/booking").param("hotelId", "1").param("roomId", "1")
             .contentType("application/json")
             .content("{\"hotelId\": 1, \"roomId\": 1}")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("200")));

    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    @Rollback(true)
    void should_update_status_to_cancelled_if_booking_is_existed(long bookingId) throws Exception {
        Booking booking = bookingDao.findById(bookingId).get();
        log.info("Before update : " + booking.getBookingStatus().getCode());
        assertNotEquals(booking.getBookingStatus().getCode(), BookingStatusCode.CANCELED);
        this.mockMvc.perform(delete("/booking/" + bookingId)).andDo(print()).andExpect(content().string(containsString("200")));
        log.info("After update : " + booking.getBookingStatus().getCode());
        assert (booking.getBookingStatus().getCode().equals(BookingStatusCode.CANCELED));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1999999})
    void should_return_success_if_booking_is_not_exist(int bookingId) throws Exception {
        this.mockMvc.perform(get("/booking/" + bookingId)).andDo(print()).andExpect(content().string(containsString("500")));
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void should_return_success_if_booking_is_exist(int bookingId) throws Exception {
        this.mockMvc.perform(get("/booking/" + bookingId)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("200")));
    }
}