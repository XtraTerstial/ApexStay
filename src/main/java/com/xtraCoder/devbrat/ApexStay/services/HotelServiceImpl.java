package com.xtraCoder.devbrat.ApexStay.services;

import com.xtraCoder.devbrat.ApexStay.dto.HotelDto;
import com.xtraCoder.devbrat.ApexStay.entity.Hotel;
import com.xtraCoder.devbrat.ApexStay.exception.ResourceNotFoundException;
import com.xtraCoder.devbrat.ApexStay.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
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
}
