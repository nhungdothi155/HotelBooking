package com.hotelbooking.entity;

import com.hotelbooking.enums.BookingStatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "booking_status")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_status_id")
    private Long bookingStatusId;
    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    private BookingStatusCode code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "create_at")
    private java.sql.Timestamp createAt;

    @Column(name = "update_at")
    private java.sql.Timestamp updateAt;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_by")
    private String updateBy;


}
