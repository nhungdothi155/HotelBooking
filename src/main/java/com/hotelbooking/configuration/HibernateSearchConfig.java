package com.hotelbooking.configuration;

import com.hotelbooking.service.HotelBookingIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HibernateSearchConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final HotelBookingIndexService hotelBookingIndexService;

    public HibernateSearchConfig(HotelBookingIndexService hotelBookingIndexService) {
        this.hotelBookingIndexService = hotelBookingIndexService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            hotelBookingIndexService.initiateIndexing();
        } catch (InterruptedException e) {
            log.error("Failed to reindex entities");
        }
    }


}
