package com.hotelbooking.dao.impl;


import com.hotelbooking.dao.HotelDao;
import com.hotelbooking.dto.HotelSearchDTO;
import com.hotelbooking.entity.Hotel;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class HotelDaoImpl implements HotelDao {

    @PersistenceContext
    private EntityManager entityManager;


    public List<Hotel> searchHotel(int pageNumber, int pageSize, HotelSearchDTO hotelSearchDTO) {
        return Search.session(entityManager).search(Hotel.class).where(h -> {
            BooleanPredicateClausesStep boolQuery = h.bool();
            if (Strings.isNotEmpty(hotelSearchDTO.getName())) {
                boolQuery.should(h.simpleQueryString().field("name").matching(hotelSearchDTO.getName()));
            }
            if (Strings.isNotEmpty(hotelSearchDTO.getAddress())) {
                boolQuery.should(h.simpleQueryString().field("address").matching(hotelSearchDTO.getAddress()));
            }
            if (Strings.isNotEmpty(hotelSearchDTO.getCity())) {
                boolQuery.must(h.match().field("city").matching(hotelSearchDTO.getCity()));
            }
            if (Strings.isNotEmpty(hotelSearchDTO.getDescription())) {
                boolQuery.should(h.simpleQueryString().field("description").matching(hotelSearchDTO.getDescription()));
            }
            if (Strings.isNotEmpty(hotelSearchDTO.getFacility())) {
                boolQuery.should(h.simpleQueryString().field("rooms.facility").matching(hotelSearchDTO.getFacility()));
            }
            if (hotelSearchDTO.getPrice() != null) {
                boolQuery.must(h.range().field("rooms.price").greaterThan(hotelSearchDTO.getPrice()));
            }
            if (hotelSearchDTO.getCapacity() != null) {
                boolQuery.must(h.range().field("rooms.capacity").greaterThan(hotelSearchDTO.getCapacity()));
            }
            return boolQuery;
        }).fetchHits((pageNumber - 1) * pageSize, pageSize);

    }

    @Override
    public Optional<Hotel> findHotelByHotelId(Long hotelId) {
        return Optional.of(entityManager.find(Hotel.class, hotelId));
    }
}
