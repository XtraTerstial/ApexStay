package com.xtraCoder.devbrat.ApexStay.services;

import com.xtraCoder.devbrat.ApexStay.entity.Inventory;
import com.xtraCoder.devbrat.ApexStay.entity.Room;
import com.xtraCoder.devbrat.ApexStay.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    // This method initializes the inventory for a given room for the next year
    // It creates an inventory record for each day from today to one year later
    @Override
    public void initializeRoomForAYear(Room room) {
        // Get today's date
        LocalDate today = LocalDate.now();
        // Calculate the end date which is one year from today
        LocalDate endDate = today.plusYears(1);
        // Loop through each date from today to end date
        for (;!today.isAfter(endDate); today=today.plusDays(1)) {
            // Logic to initialize inventory for the room on 'today'
            log.info("Initializing inventory for room ID: {} on date: {}", room.getId(), today);
            // You can add actual inventory initialization logic here
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }

    }

    @Override
    public void deleteFutureInventories(Room room) {
        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByDateAfterAndRoom(today, room);
    }
}
