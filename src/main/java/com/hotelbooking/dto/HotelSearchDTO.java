package com.hotelbooking.controller.dto;

import joptsimple.internal.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HotelSearchDTO {
    //field base on hotel entity
    private String name ;
    private String address;
    private String city;
    private String country;
    private String description;

    //field base on room entity
    private String facility;
    private BigDecimal price;
    private Integer capacity;


}
