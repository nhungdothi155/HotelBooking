package com.hotelbooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


@Entity
@Indexed
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "room_id")
    @DocumentId
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "hotel_id",nullable = false)
    private Hotel hotel;

    @FullTextField
    @Column(name = "facility")
    private String facility;

    @ScaledNumberField
    @Column(name = "price")
    private BigDecimal price;

    @FullTextField
    @Column(name = "description")
    private String description;

    @GenericField
    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "create_at")
    private java.sql.Timestamp createAt;

    @Column(name = "update_at")
    private java.sql.Timestamp updateAt;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_by")
    private String updateBy;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private Set<Booking> bookingList;

    @Version
    @Column(name = "total_rooms")
    public int totalRooms;

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int count) {
        this.totalRooms = count;
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Set<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(Set<Booking> reservationList) {
        this.bookingList = reservationList;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }


    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public java.sql.Timestamp getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(java.sql.Timestamp createAt) {
        this.createAt = createAt;
    }

    public java.sql.Timestamp getUpdateAt() {
        return this.updateAt;
    }

    public void setUpdateAt(java.sql.Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }
}
