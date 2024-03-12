package com.hotelbooking.controller;

import com.hotelbooking.dto.HotelSearchDTO;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.utils.ResponseObject;
import com.hotelbooking.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    final private HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }


    @Operation(summary = "Search hotels by various criteria ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return list of hotels if matching criteria is provided",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseObject.class))
                    })

    })
    @PostMapping(path = "/search", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject searchHotels(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber, @RequestParam(value = "pageSize", required = false, defaultValue = "25") Integer pageSize, @RequestBody HotelSearchDTO hotelSearchDTO) {
        List<Hotel> list = hotelService.searchHotel(pageNumber, pageSize, hotelSearchDTO);
        return ResponseObject.ok(list);
    }

}
