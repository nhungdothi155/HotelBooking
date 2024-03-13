package com.hotelbooking.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.hotelbooking.dao.BookingDao;
import com.hotelbooking.dto.BookingDTO;
import com.hotelbooking.entity.Booking;
import com.hotelbooking.entity.Room;
import com.hotelbooking.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Slf4j
class BookingServiceImplTest {

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();
    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void do_nothing_if_booking_is_not_overlap_and_enough_room_to_reserve_and_serve_capacity() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException, BusinessException {

        //init resource
        BookingDTO book = convertToObject("bookinghoteltest/case1/booking.json",BookingDTO.class);
        Room room = convertToObject("bookinghoteltest/case1/room.json", Room.class);

        bookingService.isValidReservation(book,room,null);

    }


    @Test
    public void do_nothing_if_booking_is_overlap_but_enough_room_to_reserve_and_serve_capacity() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException, BusinessException {

        //init resource
        BookingDTO book = convertToObject("bookinghoteltest/case2/booking.json",BookingDTO.class);
        List<Booking> bookingList = convertToList("bookinghoteltest/case2/list_booking.json",Booking.class);
        Room room = convertToObject("bookinghoteltest/case2/room.json", Room.class);

        bookingService.isValidReservation(book,room,bookingList);

    }

    @Test
    public void do_nothing_if_booking_is_overlap_but_enough_room_to_reserve_but_not_serve_capacity() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException, BusinessException {

        //init resource
        BookingDTO book = convertToObject("bookinghoteltest/case3/booking.json",BookingDTO.class);
        List<Booking> bookingList = convertToList("bookinghoteltest/case3/list_booking.json",Booking.class);
        Room room = convertToObject("bookinghoteltest/case3/room.json", Room.class);

        Exception exception =assertThrows(BusinessException.class, () -> bookingService.isValidReservation(book,room,bookingList));

        String expectedMessage = "Number of client is over capacity of booking rooms";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage,actualMessage);
    }


    private <T> T convertToObject(String fileName, Class<T> clazz) throws IOException {
        File file = new File(classLoader.getResource(fileName).getFile());
        T  object = mapper.readValue(file,clazz);
        return object;
    }
    public  <T> List<T> convertToList(String fileName, Class<T> elementClass) throws IOException {

        File file2 = new File(classLoader.getResource(fileName).getFile());
        CollectionType listType =
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
        return mapper.readValue(file2, listType);
    }
}