package com.xtraCoder.devbrat.ApexStay.services;

import com.xtraCoder.devbrat.ApexStay.dto.HotelDto;
import com.xtraCoder.devbrat.ApexStay.entity.Hotel;
import com.xtraCoder.devbrat.ApexStay.entity.Room;
import com.xtraCoder.devbrat.ApexStay.exception.ResourceNotFoundException;
import com.xtraCoder.devbrat.ApexStay.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating new hotel with name: {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Fetching hotel with id: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: " + id));
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Fetching hotel with id: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: " + id));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));

        hotelRepository.deleteById(id);
        for(Room room: hotel.getRooms()) {
            inventoryService.deleteFutureInventories(room);
        }
    }

    @Override
    @Transactional
    public void activateHotel(Long id) {
        log.info("Activating hotel with id: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: " + id));
        hotel.setActive(true);

        //assuming only do it ones
        // Below code initializes inventory for all rooms in the hotel for a year when the hotel is activated
        // This ensures that the inventory is only created when the hotel is active, and avoids unnecessary inventory creation for inactive hotels
        for (Room room: hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
        }
    }
}
