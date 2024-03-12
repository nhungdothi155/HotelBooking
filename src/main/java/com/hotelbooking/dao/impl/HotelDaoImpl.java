package com.hotelbooking.dao.impl;


import com.hotelbooking.dto.HotelSearchDTO;
import com.hotelbooking.entity.Hotel;
import org.apache.logging.log4j.util.Strings;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class HotelRepositoryImpl {
    public static final int MAX_HOTEL_STAR = 5;
    @PersistenceContext
    private EntityManager entityManager;

    public HotelRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }


    public List<Hotel> searchProductNameAndDescriptionByCombinedQuery(String manufactorer, int memoryLow, int memoryTop, String extraFeature, String exclude) {

        Query combinedQuery = getQueryBuilder()
                .bool()
                .must(getQueryBuilder().keyword()
                        .onField("productName")
                        .matching(manufactorer)
                        .createQuery())
                .must(getQueryBuilder()
                        .range()
                        .onField("memory")
                        .from(memoryLow)
                        .to(memoryTop)
                        .createQuery())
                .should(getQueryBuilder()
                        .phrase()
                        .onField("description")
                        .sentence(extraFeature)
                        .createQuery())
                .must(getQueryBuilder()
                        .keyword()
                        .onField("productName")
                        .matching(exclude)
                        .createQuery())
                .not()
                .createQuery();

        List<Hotel> results = getJpaQuery(combinedQuery).getResultList();

        return results;
    }

    private FullTextQuery getJpaQuery(org.apache.lucene.search.Query luceneQuery) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        return fullTextEntityManager.createFullTextQuery(luceneQuery, Hotel.class);
    }

    private QueryBuilder getQueryBuilder() {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Hotel.class)
                .get();
    }


    public List<Hotel> searchHotel(int pageNumber, int pageSize, HotelSearchDTO hotelSearchDTO) {

        BooleanJunction combinedQuery = getQueryBuilder().bool();
        if (Strings.isNotEmpty(hotelSearchDTO.getName()))

            combinedQuery.should(getQueryBuilder()
                    .phrase()
                    .onField("name")
                    .sentence(hotelSearchDTO.getName())
                    .createQuery());
        if (Strings.isNotEmpty(hotelSearchDTO.getAddress())) {
            combinedQuery.should(getQueryBuilder()
                    .keyword().fuzzy()
                    .onField("address")
                    .matching(hotelSearchDTO.getAddress())
                    .createQuery());
        }
        if (Strings.isNotEmpty(hotelSearchDTO.getCity())) {
            combinedQuery.must(getQueryBuilder()
                    .keyword()
                    .onField("city")
                    .matching(hotelSearchDTO.getCity())
                    .createQuery());

        }
        if (hotelSearchDTO.getStar() != null){
            combinedQuery.must(getQueryBuilder()
                    .range()
                    .onField("star")
                    .from(hotelSearchDTO.getStar())
                    .to(MAX_HOTEL_STAR)
                    .createQuery());
        }
        if (Strings.isNotEmpty(hotelSearchDTO.getDescription())) {
            combinedQuery.should(getQueryBuilder()
                    .keyword()
                    .fuzzy()
                    .onField("description")
                    .matching(hotelSearchDTO.getDescription())
                    .createQuery());
        }
        if (Strings.isNotEmpty(hotelSearchDTO.getFacility())) {
            combinedQuery.must(getQueryBuilder()
                    .keyword().fuzzy()
                    .onField("facility")
                    .matching(hotelSearchDTO.getFacility())
                    .createQuery());
        }
        if (hotelSearchDTO.getPrice() != null) {
            combinedQuery.must(getQueryBuilder()
                    .range()
                    .onField("price")
                    .from(hotelSearchDTO.getPrice())
                    .to(BigDecimal.valueOf(Double.MAX_VALUE))
                    .createQuery());
        }
        if (hotelSearchDTO.getCapacity() != null) {
            combinedQuery.must(getQueryBuilder()
                    .range()
                    .onField("capacity")
                    .from(hotelSearchDTO.getCapacity())
                    .to(Integer.MAX_VALUE)
                    .createQuery()).createQuery();
        }

        List<Hotel> results = getJpaQuery(combinedQuery.createQuery()).setFirstResult((pageNumber-1)*pageSize).setMaxResults(pageSize).getResultList();
        return results;
    }
}
