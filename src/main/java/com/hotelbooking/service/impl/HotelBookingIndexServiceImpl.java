package com.hotelbooking.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@Slf4j
public class HotelBookingIndexServiceImpl implements HotelBookingIndexService {
    private final EntityManager entityManager;

    public HotelBookingIndexServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void initiateIndexing() throws InterruptedException {
        log.info("Initiating indexing...");
        SearchSession searchSession = Search.session(entityManager);
        MassIndexer indexer = searchSession.massIndexer()
                .threadsToLoadObjects(7);
        indexer.startAndWait();
        log.info("All entities indexed");
    }
}
