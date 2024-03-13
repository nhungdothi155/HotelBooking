package com.hotelbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public interface HotelBookingIndexService {

    /**
     * Used for initiate all indices to elasticsearch at first app start
     * @throws InterruptedException
     */
    public void initiateIndexing() throws InterruptedException;

}
