package com.xtraCoder.devbrat.ApexStay.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(
        name = "unique_hotel_room_date",
        columnNames = {"hotel_id", "room_id", "date"}
        // Ensure unique combination of hotel, room, and date because a room type can have multiple rooms
))
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0") // Default to 0 if not specified
    private Integer bookedCount; // Number of rooms booked for this date

    @Column(nullable = false)
    private Integer totalCount; // Total number of such rooms available in the hotel

    @Column(nullable = false, precision = 5, scale = 2) // e.g., 99999.99
    private BigDecimal surgeFactor; // Multiplier for dynamic pricing based on demand

    @Column(nullable = false, precision = 10, scale = 2) // e.g., 99999999.99
    private BigDecimal price; // Final price after applying surge factor (basePrice * surgeFactor)

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Boolean closed; // If true, no bookings allowed for this date

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
