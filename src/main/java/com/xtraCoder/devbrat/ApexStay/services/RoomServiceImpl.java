package com.xtraCoder.devbrat.ApexStay.services;

import com.xtraCoder.devbrat.ApexStay.dto.RoomDto;
import com.xtraCoder.devbrat.ApexStay.entity.Hotel;
import com.xtraCoder.devbrat.ApexStay.entity.Room;
import com.xtraCoder.devbrat.ApexStay.exception.ResourceNotFoundException;
import com.xtraCoder.devbrat.ApexStay.repositories.HotelRepository;
import com.xtraCoder.devbrat.ApexStay.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    public final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    public final ModelMapper modelMapper;

    //First we check if the hotel exists then we create a new room and associate it with the hotel
    @Override
    public RoomService createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating new room: {}", roomDto);
        // Check if the hotel exists
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        // if hotel exists, create a new room and associate it with the hotel
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);
        return modelMapper.map(room, RoomService.class);
    }

    //First we check if the hotel exists then we fetch all rooms associated with the hotel
    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Fetching all rooms in hotel with ID: {}", hotelId);
        // Check if the hotel exists
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + hotelId));
        // If hotel exists, fetch all rooms associated with the hotel by mapping each Room entity to RoomDto
        return hotel.getRooms()
                .stream().map((element)-> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    //This API is to get room details by room id
    //No need to check if the hotel exists as we are fetching room by room id
    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting room with ID: {}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));
        return modelMapper.map(room, RoomDto.class);
    }

    //This API is to delete room by room id
    @Override
    public void deleteRoomById(Long roomId) {
        log.info("Deleting room with ID: {}", roomId);
        boolean exists = roomRepository.existsById(roomId);
        if (!exists) {
            throw new ResourceNotFoundException("Room not found with id: " + roomId);
        }
        roomRepository.deleteById(roomId);

        //TODO: delete the future inventories and bookings of this room
    }
}
