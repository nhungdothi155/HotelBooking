package com.hotelbooking.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "hotel")
@Indexed
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    @DocumentId
    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "name")
    @FullTextField
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @FullTextField
    @Column(name = "address")
    private String address;

    @FullTextField
    @Column(name = "city")
    private String city;

    @FullTextField
    @Column(name = "country")
    private String country;


    @Column(name = "star")
    private Integer star;

    @Column(name = "create_at", updatable = false,nullable = false)
    @CreatedDate
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateAt;
    @Column(name = "create_by")
    private String createBy;


    @Column(name = "update_by")
    private String updateBy;

    @FullTextField
    @Column(name = "description")
    private String description;


    @IndexedEmbedded
    @JsonIgnore
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms;


}
