package com.xtraCoder.devbrat.ApexStay.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(nullable = false)
    private String type; // e.g., Single, Double, Suite

    @Column(nullable = false, precision = 10, scale = 2) // e.g., 99999999.99
    private BigDecimal basePrice;

    @Column(columnDefinition = "TEXT[]")
    private String[] photos;

    @Column(columnDefinition = "TEXT[]")
    private String[] amenities;

    @Column(nullable = false)
    private Integer totalCount; // Total number of such rooms available in the hotel

    @Column(nullable = false)
    private Integer capacity; // Number of guests the room can accommodate

    @CreationTimestamp
    @Column(updatable = false) // createdAt should not be updated
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
