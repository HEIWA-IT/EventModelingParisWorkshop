package com.github.newlight77.uc.availability;

import com.github.newlight77.events.EventListener;
import com.github.newlight77.events.EventStore;
import com.github.newlight77.model.Room;
import com.github.newlight77.repository.RoomReadRepository;
import com.github.newlight77.repository.RoomWriteRepository;
import org.json.simple.JSONObject;

import java.util.List;

public class RoomAvailabilityProcessor implements EventListener {

    private RoomWriteRepository roomWriteRepository;
    private RoomReadRepository roomReadRepository;

    public RoomAvailabilityProcessor(RoomReadRepository roomReadRepository, RoomWriteRepository roomWriteRepository, EventStore eventStore) {
        this.roomReadRepository = roomReadRepository;
        this.roomWriteRepository = roomWriteRepository;
        eventStore.register(this);
    }

    public List<Room> availableRooms() {
        return roomReadRepository.getAvailableRooms();
    }

    @Override
    public void onEvent(JSONObject event) {
        if (event.containsKey("checkinTime")) {
            this.roomWriteRepository.updateRoomAvailability(event.get("roomNumber").toString(), false);
        }
    }
}
