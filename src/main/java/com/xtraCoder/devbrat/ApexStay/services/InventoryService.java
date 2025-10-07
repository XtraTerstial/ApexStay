package com.xtraCoder.devbrat.ApexStay.services;

import com.xtraCoder.devbrat.ApexStay.entity.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);
}
