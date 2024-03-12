package com.hotelbooking.service;

import com.hotelbooking.dto.BookingDTO;
import com.hotelbooking.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;

public interface BookingService {

    /**
     * Create booking base on hotelId and roomId
     * @param hotelId
     * @param roomId
     * @param reservation
     */
    public void createBooking(Long hotelId, Long roomId, BookingDTO reservation) throws BusinessException;

    /**
     * update booking by hotel , room
     * @param hotelId
     * @param roomId
     * @param reservation
     * @throws BusinessException
     */
    @Transactional
    void updateBooking(Long hotelId, Long roomId, BookingDTO reservation) throws BusinessException;

    /**
     * Cancel existing booking
     * @param id
     * @throws BusinessException
     */
    @Transactional
    void cancelReservation(Long id) throws BusinessException;

    /**
     * View current booking
     * @param id
     * @return
     * @throws BusinessException
     */
    public BookingDTO viewReservation(Long id) throws BusinessException;
}
