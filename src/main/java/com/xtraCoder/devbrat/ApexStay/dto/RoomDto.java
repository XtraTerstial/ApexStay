package com.xtraCoder.devbrat.ApexStay.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomDto {

    private Long id;
    private String type; // e.g., Single, Double, Suite
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private Integer totalCount; // Total number of such rooms available in the hotel
    private Integer capacity; // Number of guests the room can accommodate
}
