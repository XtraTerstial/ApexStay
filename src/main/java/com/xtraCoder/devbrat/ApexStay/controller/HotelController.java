package com.xtraCoder.devbrat.ApexStay.controller;

import com.xtraCoder.devbrat.ApexStay.dto.HotelDto;
import com.xtraCoder.devbrat.ApexStay.services.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

    //This API is for admin to create new hotel. (This API is not for public use)
    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto) {
        log.info("Attempting to create new hotel with name: {}", hotelDto.getName());
        HotelDto hotel = hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    //This API is for admin to get any hotel details by id.(This API is not for public use)
    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId) {
        log.info("Fetching hotel details for id: {}", hotelId);
        HotelDto hotelDto = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotelDto);
    }

    //This API is for admin to update any hotel details by id.(This API is not for public use)
    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable Long hotelId, @RequestBody HotelDto hotelDto) {
        log.info("Updating hotel with id: {}", hotelId);
        // Assuming there's an updateHotel method in the service layer
        HotelDto updatedHotel = hotelService.updateHotelById(hotelId, hotelDto);
        return ResponseEntity.ok(updatedHotel);
    }

    //This API is for admin to delete any hotel by id.(This API is not for public use)
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId) {
        log.info("Deleting hotel with id: {}", hotelId);
        Boolean isDeleted = hotelService.deleteHotelById(hotelId);
        return ResponseEntity.noContent().build();
        }
}
