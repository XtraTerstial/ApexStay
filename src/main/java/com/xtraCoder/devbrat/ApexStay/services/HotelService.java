package com.xtraCoder.devbrat.ApexStay.services;

import com.xtraCoder.devbrat.ApexStay.dto.HotelDto;
import com.xtraCoder.devbrat.ApexStay.entity.Hotel;

public interface HotelService {

    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);
}
