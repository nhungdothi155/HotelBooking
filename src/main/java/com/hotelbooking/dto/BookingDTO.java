package com.hotelbooking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    private Long bookingId;
    private Long roomId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private BigDecimal totalPrice;
    private String userName;
    private String firstName;
    private String lastName;
    private String dob;
    private String sex;
    private String phone;
    private String email;
    private String address;
    private Integer numChildren;
    private Integer numAdults;
    private String note;
    private Integer numOfBookingRoom;

}
