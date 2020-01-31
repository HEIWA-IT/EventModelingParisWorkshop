package com.github.newlight77.repository;

import com.github.newlight77.model.Room;
import com.github.newlight77.repository.database.HotelDatabase;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class RoomWriteRepository {

    private HotelDatabase database;
    private RoomPublisher publisher;

    public RoomWriteRepository(RoomPublisher publisher, HotelDatabase database) {
        this.publisher = publisher;
        this.database = database;
    }

    public void updateRoomAvailability(String roomNumber, boolean available) {
        database.getRooms().stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber))
                .forEach(r -> r.setAvailable(available));

        //publisher.publish();
    }
}
