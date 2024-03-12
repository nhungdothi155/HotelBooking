package com.hotelbooking.service.impl;

import com.hotelbooking.dao.HotelDao;
import com.hotelbooking.dto.BookingDTO;
import com.hotelbooking.entity.Booking;
import com.hotelbooking.entity.BookingStatus;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.entity.Room;
import com.hotelbooking.enums.BookingStatusCode;
import com.hotelbooking.dao.BookingDao;
import com.hotelbooking.dao.BookingStatusDao;
import com.hotelbooking.dao.RoomDao;
import com.hotelbooking.exception.BusinessException;
import com.hotelbooking.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final RoomDao roomDao;
    private final BookingStatusDao bookingStatusDao;
    private final HotelDao hotelDAO;

    public BookingServiceImpl(BookingDao bookingDao, RoomDao roomDao, BookingStatusDao bookingStatusDao, HotelDao hotelDAO) {
        this.bookingDao = bookingDao;
        this.roomDao = roomDao;
        this.bookingStatusDao = bookingStatusDao;
        this.hotelDAO = hotelDAO;
    }

    @Transactional
    @Override
    public void createBooking(Long hotelId, Long roomId, BookingDTO reservation) throws BusinessException {

        //check hotel is existed with provide hotel id
        Hotel hotel = hotelDAO.findHotelByHotelId(hotelId).orElseThrow(() -> new RuntimeException("No such hotel"));
        //check room is existed with provide room id
        Room room = roomDao.findRoomByRoomIdAndHotelId(roomId, hotelId).orElseThrow(() -> new RuntimeException("No such room"));

        //check is there any same reservation at the same time or overlap time
        List<Booking> bookings = bookingDao.findOverlapBookingByStartTimeAndEndTime(roomId, reservation.getCheckInTime(), reservation.getCheckOutTime());

        //check valid reservation
        isValidReservation(reservation, room, bookings);


        Booking booking = convertToBooking(reservation, room);
        bookingDao.save(booking);

    }

    @Transactional
    @Override
    public void updateBooking(Long hotelId, Long roomId, BookingDTO reservation) throws BusinessException {

        //check hotel is existed with provide hotel id
        Hotel hotel = hotelDAO.findHotelByHotelId(hotelId).orElseThrow(() -> new BusinessException("No such hotel"));
        //check room is existed with provide room id
        Room room = roomDao.findRoomByRoomIdAndHotelId(roomId, hotelId).orElseThrow(() -> new BusinessException("No such room"));

        Booking originalBooking = bookingDao.findBookingByRoomIdAndBookingId(room.getRoomId(), reservation.getBookingId()).orElseThrow(() -> new BusinessException("No such booking"));

        //check is there any same reservation at the same time or overlap time
        List<Booking> bookings = bookingDao.findOverlapBookingByStartTimeAndEndTimeAndNotEqualCurrentBooking(roomId, reservation.getCheckInTime(), reservation.getCheckOutTime(), originalBooking.getBookingId());

        //check valid reservation
        isValidReservation(reservation, room, bookings);


        Booking updatedBooking = convertToBooking(reservation, room);
        bookingDao.save(updatedBooking);

    }

    public  void isValidReservation(BookingDTO reservation, Room room, List<Booking> bookings) throws BusinessException {
        if (!CollectionUtils.isEmpty(bookings)) {
            //check total rooms is reversed at that time + current total booking room > number of available rooms
            int numberOfReservedRoom = bookings.stream().mapToInt(Booking::getNumReservedRooms).sum();
            int numberOfBookingRoom = reservation.getNumOfBookingRoom();
            if (numberOfReservedRoom + numberOfBookingRoom > room.getTotalRooms()) {
               throw new BusinessException("There is a reservation at the same time or overlap time");
            }
        } else {
            int totalRoom = room.getTotalRooms();
            int numberOfBookingRoom = reservation.getNumOfBookingRoom();
            if (numberOfBookingRoom > totalRoom) {
                throw new BusinessException("Number of bookings is larger than number of total rooms");
            }
        }
        int totalClients = reservation.getNumChildren() + reservation.getNumAdults();
        if (totalClients / reservation.getNumOfBookingRoom() > room.getCapacity()) {
            log.info("Number of client is over capacity of booking rooms");
            throw new BusinessException("Number of client is over capacity of booking rooms");

        }

    }


    private Booking convertToBooking(BookingDTO reservation, Room room) {
        BookingStatus bookingStatus = bookingStatusDao.findByCode(BookingStatusCode.REQUESTED);
        Booking booking = new Booking(reservation.getBookingId(), room, bookingStatus, reservation.getCheckInTime(), reservation.getCheckOutTime(),
                reservation.getTotalPrice(), reservation.getUserName(), reservation.getFirstName(),
                reservation.getLastName(), reservation.getDob(), reservation.getSex(), reservation.getPhone(),
                reservation.getEmail(), reservation.getAddress(), reservation.getNumAdults(), reservation.getNumChildren(),
                reservation.getNumOfBookingRoom());
        return booking;
    }


    @Transactional
    @Override
    public void cancelReservation(Long id) throws BusinessException {

        //is existed
        Booking booking = bookingDao.findById(id).orElseThrow(() -> new BusinessException("No such reservation"));

        //update booking status to cancelled
        BookingStatus cancelledStatus = bookingStatusDao.findByCode(BookingStatusCode.CANCELED);
        booking.setBookingStatus(cancelledStatus);
        bookingDao.save(booking);

    }

    @Override
    public BookingDTO viewReservation(Long bookingId) throws BusinessException {
        Booking booking = bookingDao.findById(bookingId).orElse(null);
        if (booking == null) {
            throw new BusinessException("Not found booking");
        }
        BookingDTO bookingDTO = new BookingDTO(bookingId, booking.getRoom().getRoomId(),
                booking.getCheckInTime(), booking.getCheckOutTime(),
                booking.getTotalPrice(), booking.getUserName(), booking.getFirstName(),
                booking.getLastName(), booking.getDob(), booking.getSex(), booking.getPhone(),
                booking.getEmail(), booking.getAddress(), booking.getNumAdults(), booking.getNumChildren(), null,
                booking.getNumReservedRooms());

        return bookingDTO;
    }


}
