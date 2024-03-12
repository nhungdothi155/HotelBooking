package com.hotelbooking.controller;


import com.hotelbooking.dto.BookingDTO;
import com.hotelbooking.exception.BusinessException;
import com.hotelbooking.utils.ResponseObject;
import com.hotelbooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
@Slf4j
public class BookingController {
    final private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Allow update and insert a booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return status 200 and message is success",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseObject.class))
                    })

    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject upsertBooking(@RequestBody BookingDTO booking, @RequestParam("hotelId") Long hotelId, @RequestParam("roomId") Long roomId) throws BusinessException {
        if (booking.getBookingId() == null) {
            log.info("Create booking");
            bookingService.createBooking(hotelId, roomId, booking);
        } else {
            log.info("Update booking");
            bookingService.updateBooking(hotelId, roomId, booking);
        }
        return ResponseObject.ok();
    }

    @Operation(summary = "cancel existing reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return status 200 and message is success if existed",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseObject.class))
                    })

    })
    @DeleteMapping(path = "/{bookingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject cancelReservation(@PathVariable("bookingId") Long bookingId) throws BusinessException {
        log.info("Cancel reservation");
        bookingService.cancelReservation(bookingId);
        return ResponseObject.ok();
    }

    @Operation(summary = "Get booking by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return status 200 if provided booking id is existed",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseObject.class))
                    })

    })
    @GetMapping(path = "/{bookingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<BookingDTO> getBookingById(@PathVariable("bookingId") Long bookingId) throws BusinessException {
        log.info("Get booking");
        BookingDTO booking = bookingService.viewReservation(bookingId);
        return ResponseObject.ok(booking);
    }


}
